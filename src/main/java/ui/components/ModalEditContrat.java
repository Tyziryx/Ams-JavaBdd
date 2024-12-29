package main.java.ui.components;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.java.data.entities.CommandeAEffectuer;
import main.java.data.entities.Contrat;
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

public class ModalEditContrat extends Modal {
    Table oldTable;

    /**
     * Constructeur de la classe ModalEditContrat
     * Permet de créer et modifier un contrat
     * @param page
     * @param oldPage
     * @param oldTable
     * @param spacing
     * @param title
     * @param items
     * @param isNew
     * @throws SQLException
     */
    public ModalEditContrat(BorderPane page, Page oldPage, Table oldTable, double spacing, String title, ObservableList<String> items, boolean isNew) throws SQLException {
        super(page, oldPage, spacing, title);
        this.oldTable = oldTable;
        VBox form = new VBox();

        Label nomFournisseurLabel = new Label("Nom du fournisseur");
        nomFournisseurLabel.getStyleClass().add("label");
        TextField nomFournisseur = new TextField();
        if(!isNew) nomFournisseur.setText(getNomFournisseur(items.get(5).toString()));
        nomFournisseur.setDisable(true);

        Label fournisseurLabel = new Label("Fournisseur");
        fournisseurLabel.getStyleClass().add("label");
        TextField fournisseur = new TextField();
        if(!isNew) fournisseur.setText(items.get(5).toString());
        fournisseur.setDisable(true);


        Label produitNomLabel = new Label("Nom du produit");
        produitNomLabel.getStyleClass().add("label");
        TextField produitNom = new TextField();
        if(!isNew) produitNom.setText(items.get(9).toString());
        produitNom.setDisable(true);

        Label produitLabel = new Label("Produit");
        produitLabel.getStyleClass().add("label");
        TextField produit = new TextField();
        if(!isNew) produit.setText(items.get(7).toString());
        produit.setDisable(true);

        Label quantiteLabel = new Label("Quantite Minimale");
        quantiteLabel.getStyleClass().add("label");
        TextField quantite = new TextField();
        if(isNew)quantite.setText("1");
        if(!isNew) quantite.setText(items.get(1).toString());

        Label dateDebutLabel = new Label("Date de début");
        dateDebutLabel.getStyleClass().add("label");
        DatePicker dateDebut = new DatePicker();
        if(!isNew) dateDebut.setValue(LocalDate.parse(items.get(2).toString()));

        Label dateFinLabel = new Label("Date de fin");
        dateFinLabel.getStyleClass().add("label");
        DatePicker dateFin = new DatePicker();
        if(!isNew) dateFin.setValue(LocalDate.parse(items.get(3).toString()));

        Label prixLabel = new Label("Prix");
        prixLabel.getStyleClass().add("label");
        TextField prix = new TextField();
        prix.setText("0");
        if(!isNew) prix.setText(items.get(4).toString());

        Label idLabel = new Label("ID");
        idLabel.getStyleClass().add("label");
        TextField id = new TextField();
        if (!isNew) id.setText(items.get(6).toString());
        id.setDisable(true);

        Button commander = new Button("Etablir le contrat");
        if (!isNew) commander= new Button("Changer le contrat");
        commander.getStyleClass().add("buttoncommander");

        final Text actiontarget = new Text();
        actiontarget.getStyleClass().add("erreur-form");

        Button button = new Button("⬅ Revenir en arrière");
        button.getStyleClass().add("top-left-button");
        button.setOnAction(e -> {
            page.setCenter(oldPage);
        });
        VBox contentVBox = new VBox();
        contentVBox.getChildren().add(button);
        HBox contentHbox = new HBox();
        form.getChildren().addAll(nomFournisseurLabel, nomFournisseur, produitNomLabel, produitNom, quantiteLabel, quantite, dateDebutLabel, dateDebut, dateFinLabel, dateFin, prixLabel, prix, commander, actiontarget);
        form.getStyleClass().add("form");
        contentHbox.getChildren().add(form);
        contentHbox.getStyleClass().add("content-box");

        commander.setOnAction(e -> {
            try {
                if (fournisseur.getText().isEmpty() || produit.getText().isEmpty() || quantite.getText().isEmpty() || prix.getText().isEmpty() || dateDebut.getValue() == null || dateFin.getValue() == null) {
                    showError(actiontarget, "Veuillez remplir tous les champs");
                    return;
                }
                try {
                    Integer.parseInt(quantite.getText());
                } catch (NumberFormatException ex) {
                    showError(actiontarget, "La quantité doit être un nombre");
                    return;
                }
                try {
                    Float.parseFloat(prix.getText());
                } catch (NumberFormatException ex) {
                    showError(actiontarget, "Le prix doit être un nombre");
                    return;
                }
                try {
                    Integer.parseInt(fournisseur.getText());
                } catch (NumberFormatException ex) {
                    showError(actiontarget, "La quantité doit être un nombre");
                    return;
                }
                int id_fournisseur = Integer.valueOf(fournisseur.getText());

                int id_produit = Integer.valueOf(produit.getText());
                int quantiteInt = Integer.valueOf(quantite.getText());
                float prixFloat = Float.valueOf(prix.getText());
                Date dateDebutDate = Date.valueOf(dateDebut.getValue());
                Date dateFinDate = Date.valueOf(dateFin.getValue());
                if(!isNew) {
                    UUID idContrat = UUID.fromString(id.getText());
                    Contrat contrat = new Contrat(idContrat, id_fournisseur, id_produit, quantiteInt, dateDebutDate, dateFinDate, prixFloat);
                    Gestion.update(contrat, Tables.CONTRAT);
                    fermer(oldTable);
                } else {
                    Contrat contrat = new Contrat(id_fournisseur, id_produit, quantiteInt, dateDebutDate, dateFinDate, prixFloat);
                    Gestion.insert(contrat, Tables.CONTRAT);
                    fermer(oldTable);
                }

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });


        /*
        ###################
        ##### Tableaux ####
        ###################
         */
        // Le tableau permet de sélectionner le produit/fournisseur facilement
        LinkedList<Colonne> colonnes = new LinkedList<Colonne>(){
            {
                add(new Colonne("siret", "Siret", 100));
                add(new Colonne("nom_societe", "Nom fournisseur", 100));
                add(new Colonne("id_produit", "Produit", 100));
                add(new Colonne("nom", "Nom produit", 100));
                add(new Colonne("prix", "Prix", 100));
            }
        };

        Table table = new Table(page, this, Tables.FOURNISSEUR, "SELECT siret, fournisseur.nom_societe, prix_fournisseur.id_produit, prix_fournisseur.prix, produit.nom FROM prix_fournisseur JOIN fournisseur ON prix_fournisseur.id_fournisseur = fournisseur.siret JOIN produit ON produit.id_produit = prix_fournisseur.id_produit" , "Commander", colonnes, true, false);
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
                    produit.setText(row.getItem().get(2));
                    try {
                        nomFournisseur.setText(getNomFournisseur(row.getItem().get(0)));
                        produitNom.setText(getProduitNom(row.getItem().get(2)));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }
            });
            return row;
        });
    }

    private String getNomFournisseur(String id) throws SQLException {
        ResultSet rs;
        String nom = "";
        try {
            rs = Gestion.execute("SELECT nom_societe FROM fournisseur WHERE siret = " + id);
            while (rs.next()) {
                nom = rs.getString("nom_societe");
            }
        } catch (SQLException e) {
            nom = "Fournisseur inconnu";
        }

        return nom;
    }

    /**
     * Permet de récupérer le nom d'un produit en fonction de son id
     * @param id
     * @return
     * @throws SQLException
     */
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

//    private String getProduitCategorie(String id) throws SQLException {
//        ResultSet rs;
//        String nom = "";
//        try {
//            rs = Gestion.execute("SELECT categorie FROM produit WHERE id_produit = " + id);
//            while (rs.next()) {
//                nom = rs.getString("nom");
//            }
//        } catch (SQLException e) {
//            nom = "Produit inconnu";
//        }
//
//        return nom;
//    }

    /**
     * Affiche une erreur sur le formulaire
     * @param actionTarget
     * @param msg
     */
    private void showError(Text actionTarget, String msg) {
        actionTarget.setText(msg);
    }

    /**
     * Permet de fermer la fenêtre
     * @param table
     * @throws SQLException
     */
    public void fermer(Table table) throws SQLException {
        super.fermer();
        table.refreshDynamicTable();
        page.setCenter(oldPage);
    }
}