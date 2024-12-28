package main.java.ui.components;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.java.data.entities.LotDeProduit;
import main.java.data.sql.Gestion;
import main.java.data.sql.Tables;
import main.java.ui.pages.Page;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static javafx.scene.input.KeyEvent.KEY_RELEASED;

public class ModalCommander extends Modal {
    Table oldTable;
    public ModalCommander(BorderPane page, Page oldPage, Table oldTable, double spacing, String title, ObservableList<String> items) throws SQLException {
        super(page, oldPage, spacing, title);
        this.oldTable = oldTable;
        VBox form = new VBox();

        Label fournisseurLabel = new Label("Fournisseur");
        fournisseurLabel.getStyleClass().add("label");
        TextField fournisseur = new TextField();
        fournisseur.setText(items.get(0).toString());
        fournisseur.setDisable(true);
        Label produitNomLabel = new Label("Nom du produit");
        produitNomLabel.getStyleClass().add("label");
        TextField produitNom = new TextField();
        produitNom.setText(getProduitNom(items.get(1).toString()));
        produitNom.setDisable(true);
        Label produitLabel = new Label("Produit");
        produitLabel.getStyleClass().add("label");
        TextField produit = new TextField();
        produit.setText(items.get(1).toString());
        produit.setDisable(true);
        Label quantiteLabel = new Label("Quantite");
        quantiteLabel.getStyleClass().add("label");
        TextField quantite = new TextField();
        quantite.setText("1");
        Label prixLabel = new Label("Prix a l'unité");
        prixLabel.getStyleClass().add("label");
        TextField prix = new TextField();
        prix.setText(items.get(2).toString());
        prix.setDisable(true);
        Button commander = new Button("Commander");
        commander.getStyleClass().add("buttoncommander");
        final javafx.scene.text.Text actiontarget = new Text();
        actiontarget.getStyleClass().add("erreur-form");
        form.getChildren().addAll(fournisseurLabel, fournisseur, produitNomLabel, produitNom, produitLabel, produit, quantiteLabel, quantite, prixLabel, prix, commander, actiontarget);
        Button button = new Button("⬅ Revenir en arrière");
        button.getStyleClass().add("button-top");
        button.setOnAction(e -> {
            page.setCenter(oldPage);
        });

        VBox Box = new VBox();
        Box.getChildren().add(button);

        Box.getChildren().add(form);
        contentBox.getChildren().add(Box);
        Box.getStyleClass().add("content-box");
        //contentBox.getChildren().add(form);
        form.getStyleClass().add("form");
        commander.setOnAction(e -> {
            try {
                int id_produit = Integer.valueOf(items.get(1));
                if (quantite.getText().isEmpty()) {
                    showError(actiontarget, "La quantité ne peut pas être vide");
                    return;
                }
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
                float prixUnite = Float.valueOf(items.get(2));
                float prixAchat = prixUnite * quantiteInt;
                int id_fournisseur = Integer.valueOf(items.get(0));
                LotDeProduit lot = new LotDeProduit(id_produit, quantiteInt, date_achat, date_peremption, prixAchat, prixUnite, id_fournisseur);
                Gestion.insert(lot, Tables.LOT_DE_PRODUIT);
                fermer(oldTable);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    private String getProduitNom(String id) throws SQLException {
        ResultSet rs;
        String nom = "";
        try {
            rs = Gestion.execute("SELECT nom FROM produit WHERE id_produit = " + id);
            while (rs.next()) {
                nom = rs.getString("nom");
            }
        } catch (SQLException e) {
            nom = "Produit inconnu";
        }

        return nom;
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