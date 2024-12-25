package main.java.ui.pages;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import main.java.data.entities.Fournisseur;
import main.java.data.sql.Tables;
import main.java.ui.components.Modal;
import main.java.ui.components.Table;
import main.java.util.Colonne;

import java.sql.SQLException;
import java.util.ArrayList;

public class PageFournisseurs extends Page {
    public Modal modal;

    public PageFournisseurs(double spacing) throws SQLException {
        super(spacing, "Fournisseurs");
        ArrayList<Colonne> tableContentFournisseurs = new ArrayList<Colonne>() {
            {
                add(new Colonne("nom_societe", "Nom société", 150));
                add(new Colonne("siret", "Siret", 100));
            }
        };
        Table tableFournisseurs = new Table(Tables.FOURNISSEUR, tableContentFournisseurs, true);

        ArrayList<Colonne> tableContentContrats = new ArrayList<Colonne>() {
            {
                add(new Colonne("id_fournisseur", "Fournisseur", 110));
                add(new Colonne("quantite_min", "Quantité min", 115));
                add(new Colonne("date_fin", "Date de fin", 100));
                add(new Colonne("prix_produit", "Prix", 100));
            }
        };
        Table tableContrats = new Table(Tables.CONTRAT, tableContentContrats, true);

        HBox tables = new HBox();
        HBox.setHgrow(tableContrats, Priority.ALWAYS);
        tables.getChildren().addAll(tableFournisseurs, tableContrats);

        ObservableList<Node> components = this.getChildren();
        components.addAll(title, tables);

        modal = new Modal(10, "Informations sur le fournisseur");

        // Ajouter un gestionnaire d'événements pour le tableau des fournisseurs
        tableFournisseurs.getSelectionModel(nom_societe).setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                System.out.println("Double-clic détecté !");

                Fournisseur selectedFournisseur = (Fournisseur) tableFournisseurs.getSelectionModel().getSelectedItem();
                if (selectedFournisseur != null) {
                    try {
                        showModalWithData(selectedFournisseur);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private void showModalWithData(Fournisseur fournisseur) throws SQLException {
        // Extraire les données nécessaires du fournisseur
        String nomSociete = fournisseur.getNom_societe();
        String siret = String.valueOf(fournisseur.getSiret());
        String adresse = fournisseur.getAdresse();
        String email = fournisseur.getEmail();

        // Mettre à jour les données de la modale
        modal.setFournisseurData(nomSociete, siret, adresse, email);

        // Afficher la modale
        modal.open();
    }
}