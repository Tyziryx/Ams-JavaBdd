package main.java.ui.components;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.java.data.entities.CommandeAEffectuer;
import main.java.data.entities.LotDeProduit;
import main.java.data.sql.Gestion;
import main.java.data.sql.Tables;
import main.java.ui.pages.Page;
import main.java.util.Colonne;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.UUID;

import static javafx.scene.input.KeyEvent.KEY_RELEASED;

public class ModalCommandeAEffectuer extends Modal {
    Table oldTable;
    public ModalCommandeAEffectuer(BorderPane page, Page oldPage, Table oldTable, double spacing, String title, ObservableList<String> items) throws SQLException {
        super(page, oldPage, spacing, title);
        this.oldTable = oldTable;
        VBox form = new VBox();

        Label fournisseurLabel = new Label("Fournisseur");
        fournisseurLabel.getStyleClass().add("label");
        TextField fournisseur = new TextField();
        fournisseur.setDisable(true);


        Label produitNomLabel = new Label("Nom du produit");
        produitNomLabel.getStyleClass().add("label");
        TextField produitNom = new TextField();
        produitNom.setText(items.get(2).toString());
        produitNom.setDisable(true);

        Label prixLabel = new Label("Prix a l'unité");
        prixLabel.getStyleClass().add("label");
        TextField prix = new TextField();
        prix.setText("0");
        prix.setDisable(true);

        Label prixTotalLabel = new Label("Prix total");
        prixTotalLabel.getStyleClass().add("label");
        TextField prixTotal = new TextField();
        prixTotal.setDisable(true);

        Label produitLabel = new Label("Produit");
        produitLabel.getStyleClass().add("label");
        TextField produit = new TextField();
        produit.setText(items.get(1).toString());
        produit.setDisable(true);

        Label quantiteLabel = new Label("Quantite");
        quantiteLabel.getStyleClass().add("label");
        TextField quantite = new TextField();
        quantite.setText("1");

        Button commander = new Button("Commander");
        commander.getStyleClass().add("button");

        final Text actiontarget = new Text();
        actiontarget.getStyleClass().add("erreur-form");

        quantite.addEventHandler(KEY_RELEASED, e -> {
            try {
                int quantiteInt = Integer.valueOf(quantite.getText());
                float prixUnite = Float.valueOf(prix.getText());
                prixTotal.setText(String.valueOf(quantiteInt * prixUnite));
            } catch (NumberFormatException ex) {
                prixTotal.setText(null);
            }
        });

        Button button = new Button("⬅ Revenir en arrière");
        button.getStyleClass().add("top-left-button");
       button.setOnAction(e -> {
            page.setCenter(oldPage);
        });
        VBox contentVBox = new VBox();
        contentVBox.getChildren().add(button);
        HBox contentHbox = new HBox();
        form.getChildren().addAll(fournisseurLabel, fournisseur, produitNomLabel, produitNom, produitLabel, produit, quantiteLabel, quantite, prixLabel, prix, prixTotalLabel, prixTotal, commander, actiontarget);
        form.getStyleClass().add("form");
        contentHbox.getChildren().add(form);
        contentHbox.getStyleClass().add("content-box");

        commander.setOnAction(e -> {
            try {
                int id_produit = Integer.valueOf(items.get(1));
                int quantiteInt = Integer.valueOf(quantite.getText());
                if (quantiteInt <= 0) {
                    showError(actiontarget, "La quantité doit être supérieure à 0");
                    return;
                }

                Date date_achat = Date.valueOf(LocalDate.now());
                Date date_peremption;
                switch (getProduitCategorie(items.get(1)).toLowerCase()) {
                    case "nourriture":
                        date_peremption = Date.valueOf(LocalDate.now().plusDays(90));
                        break;
                    case "frais":
                        date_peremption = Date.valueOf(LocalDate.now().plusDays(7));
                        break;
                    default:
                        date_peremption = Date.valueOf(LocalDate.now().plusDays(365));
                }

                float prixUnite = Float.valueOf(prix.getText());
                float prixAchat = prixUnite * quantiteInt;
                int id_fournisseur = Integer.valueOf(fournisseur.getText());

                LotDeProduit lot = new LotDeProduit(id_produit, quantiteInt, date_achat, date_peremption, prixAchat, prixUnite, id_fournisseur);
                Gestion.insert(lot, Tables.LOT_DE_PRODUIT);
                CommandeAEffectuer commandeAEffectuer = new CommandeAEffectuer(UUID.fromString(items.get(0)), id_fournisseur);
                Gestion.delete(commandeAEffectuer, Tables.COMMANDE_A_EFFECTUER);
                fermer(oldTable);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });


        /* Table */

        LinkedList<Colonne> colonnes = new LinkedList<Colonne>(){
            {
                add(new Colonne("siret", "Siret", 100));
                add(new Colonne("nom_societe", "Nom fournisseur", 100));
                add(new Colonne("id_produit", "Produit", 100));
                add(new Colonne("prix", "Prix", 100));
            }
        };

        Table table = new Table(page, this, Tables.FOURNISSEUR, "SELECT siret, fournisseur.nom_societe, prix_fournisseur.id_produit, prix_fournisseur.prix FROM prix_fournisseur JOIN fournisseur ON prix_fournisseur.id_fournisseur = fournisseur.siret WHERE id_produit = " + Integer.valueOf(items.get(1)), "Commander", colonnes, true, false);
        table.getStyleClass().add("hoverable");

        contentHbox.getChildren().add(table);
        contentVBox.getChildren().add(contentHbox);
        contentBox.getChildren().add(contentVBox);
        contentBox.getStyleClass().add("content-box");
        table.getDynamicTable().setRowFactory(tv -> {
            TableRow<ObservableList<String>> row = new TableRow<>();
            row.getStyleClass().add("row");
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    fournisseur.setText(row.getItem().get(0));
                    prix.setText(row.getItem().get(3));
                    prixTotal.setText(String.valueOf(Float.valueOf(quantite.getText()) * Integer.valueOf(row.getItem().get(3))));
                }
            });
            return row;
        });
    }

    private String getNomFournisseur(String id) throws SQLException {
        ResultSet rs;
        String nom = "";
        try {
            rs = Gestion.execute("SELECT nom FROM fournisseur WHERE id_fournisseur = " + id);
            while (rs.next()) {
                nom = rs.getString("nom");
            }
        } catch (SQLException e) {
            nom = "Fournisseur inconnu";
        }

        return nom;
    }

    private int getPrixUnite(String idProduit, String idFournisseur) throws SQLException {
        ResultSet rs;
        int prix = 0;
        try {
            rs = Gestion.execute("SELECT prix_produit FROM produit JOIN prix_fournisseur ON produit.id_produit = prix_fournissuer.id_produit WHERE id_produit = " + idProduit + " AND id_fournisseur = " + idFournisseur);
            while (rs.next()) {
                prix = rs.getInt("prix_produit");
            }
        } catch (SQLException e) {
            prix = 0;
        }

        return prix;
    }

    private String getProduitCategorie(String id) throws SQLException {
        ResultSet rs;
        String nom = "";
        try {
            rs = Gestion.execute("SELECT categorie FROM produit WHERE id_produit = " + id);
            while (rs.next()) {
                nom = rs.getString("nom");
            }
        } catch (SQLException e) {
            nom = "Produit inconnu";
        }

        return nom;
    }

    private void showError(Text actionTarget, String msg) {
        actionTarget.setText(msg);
    }

    public void fermer(Table table) throws SQLException {
        super.fermer();
        table.refreshDynamicTable();
        page.setCenter(oldPage);
    }
}