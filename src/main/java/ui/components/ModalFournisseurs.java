package main.java.ui.components;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main.java.data.entities.Fournisseur;
import main.java.data.sql.Tables;
import main.java.ui.pages.Page;
import main.java.util.Colonne;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class ModalFournisseurs extends Modal {

    private VBox box = new VBox();
    private VBox infoFournisseurs; // Conteneur pour les boutons
    private Table contactAssocies;

    public ModalFournisseurs(BorderPane page, Page oldPage, double spacing, String title, Fournisseur fournisseur) throws SQLException {
        super(page, oldPage, spacing, title);
        this.page = page;
        box.getStyleClass().add("modal-box");
        infoFournisseurs = new VBox();
        Label nomSociete = new Label("Nom société : " + fournisseur.getNom_societe());
        Label siret = new Label("Siret : " + fournisseur.getSiret());
        Label adresse = new Label("Adresse : " + fournisseur.getAdresse());
        Label mail = new Label("Mail : " + fournisseur.getEmail());
        infoFournisseurs.getChildren().addAll(nomSociete, siret, adresse, mail);
        nomSociete.getStyleClass().add("label-title");
        siret.getStyleClass().add("label");
        adresse.getStyleClass().add("label");
        mail.getStyleClass().add("label");
        infoFournisseurs.getStyleClass().add("info-fournisseurs");
        box.getChildren().add(infoFournisseurs);



        // Création du tableau des contacts associés
        contactAssocies = new Table(page, Tables.CONTACT_ASSOCIE, "SELECT * FROM contact_associe WHERE contact_associe.siret  = " + fournisseur.getSiret(), "Contacts associés", new LinkedList<Colonne>() {
            {
                add(new Colonne("nom", "Nom", 100));
                add(new Colonne("prenom", "Prénom", 100));
                add(new Colonne("fonction", "Fonction", 150));
                add(new Colonne("email", "Email", 150));
                add(new Colonne("telephone", "Téléphone", 100));
            }
        }, true, true);


        box.getChildren().add(contactAssocies);
        contentBox.getStyleClass().add("content-box");
        contentBox.getChildren().add(box);
        contactAssocies.prefWidthProperty().bind(contentBox.widthProperty());
        infoFournisseurs.prefWidthProperty().bind(contentBox.widthProperty());

//        this.getChildren().add(contentBox);

    }
}