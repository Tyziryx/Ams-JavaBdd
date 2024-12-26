package main.java.ui.components;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.java.data.entities.Fournisseur;
import main.java.ui.pages.Page;
import main.java.util.Colonne;

import java.sql.SQLException;
import java.util.ArrayList;

public class Modal extends Page {

    HBox contentBox; // Conteneur pour afficher les données dynamiques
    BorderPane page;

    public Modal(BorderPane page, double spacing, String title) {
        super(spacing, title);
        this.page = page;
        this.contentBox = new HBox();
    }


    public void affiche() {
        this.getChildren().add(contentBox);
        page.setCenter(this);
    }

    public void fermer() {
//        this.setVisible(false);
    }
}