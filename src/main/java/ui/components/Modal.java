package main.java.ui.components;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.java.data.entities.Fournisseur;
import main.java.ui.pages.Page;

import java.sql.SQLException;

public class Modal extends Page {

    private VBox contentBox; // Conteneur pour afficher les données dynamiques

    public Modal(double spacing, String title) throws SQLException {
        super(spacing, title);

        // Initialisation d'un VBox pour afficher les informations
        contentBox = new VBox(10);
        contentBox.setStyle("-fx-padding: 20; -fx-alignment: center;");
        this.getChildren().add(contentBox); // Ajouter le conteneur à la page
    }

    /**
     * Met à jour les données affichées dans la modale pour un fournisseur donné.
     */
    public void setFournisseurData(String nomSociete, String siret, String adresse, String email) {
        // Effacer les anciennes données
        contentBox.getChildren().clear();

        // Ajouter les nouvelles informations sous forme de labels
        Label nameLabel = new Label("Nom société : " + nomSociete);
        Label siretLabel = new Label("Siret : " + siret);
        Label adresseLabel = new Label("Adresse : " + adresse);
        Label emailLabel = new Label("Email : " + email);

        contentBox.getChildren().addAll(nameLabel, siretLabel, adresseLabel, emailLabel);
    }

    /**
     * Affiche la modale.
     */
    public void open() {
        this.setVisible(true);
    }

    /**
     * Ferme la modale.
     */
    public void close() {
        this.setVisible(false);
    }
}
