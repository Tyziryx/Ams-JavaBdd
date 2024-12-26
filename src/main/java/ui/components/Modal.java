package main.java.ui.components;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import main.java.data.entities.Fournisseur;
import main.java.ui.pages.Page;
import main.java.util.Colonne;

import java.sql.SQLException;
import java.text.Format;
import java.util.ArrayList;

public class Modal extends Page {

    private HBox contentBox; // Conteneur pour afficher les données dynamiques
    private VBox infoFournisseurs; // Conteneur pour les boutons
    private Table contactAssocies;

    public Modal(double spacing, String title , Fournisseur fournisseur) throws SQLException {

        super(spacing, title);
        infoFournisseurs = new VBox();
        contentBox = new HBox();
        Label nomSociete = new Label("Noms sociétés : " + fournisseur.getNom_societe());
        Label siret = new Label("Siret : " + fournisseur.getSiret());
        Label adresse = new Label("Adresse : " + fournisseur.getAdresse());
        Label mail = new Label("Mail : " + fournisseur.getEmail());
        infoFournisseurs.getChildren().addAll(nomSociete, siret, adresse, mail);
        contentBox.getChildren().add(infoFournisseurs);




        // Création du tableau des contacts associés
        contactAssocies = new Table("SELECT * FROM contact_associe WHERE contact_associe.siret  = " + fournisseur.getSiret(), "Contacts associés", new ArrayList<Colonne>() {
            {
                add(new Colonne("nom", "Nom", 100));
                add(new Colonne("prenom", "Prénom", 100));
                add(new Colonne("fonction", "Fonction", 100));
                add(new Colonne("email", "Email", 100));
                add(new Colonne("telephone", "Téléphone", 100));
            }
        }, true);

contentBox.getChildren().add(contactAssocies);


this.getChildren().add(contentBox);







    }





    /**
     * Affiche la modale.
     */
    public void affiche(BorderPane page) {

        // Afficher la modale
        page.setCenter(this);
    }

    /**
     * Ferme la modale.
     */
    public void close() {
        this.setVisible(false);
    }
}