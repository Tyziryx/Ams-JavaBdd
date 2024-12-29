package main.java.ui.components;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.java.data.entities.Fournisseur;
import main.java.data.entities.IData;
import main.java.ui.pages.Page;
import main.java.util.Colonne;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe abstraite pour les modales
 * permet d'affiche /  fermé une page et si elle est valide

 */


public class Modal extends Page {

    HBox contentBox; // Conteneur pour afficher les données dynamiques
    BorderPane page;
    Page oldPage;

    public Modal(BorderPane page, Page oldPage, double spacing, String title) {
        super(spacing, title);
        this.page = page;
        this.oldPage = oldPage;
        this.contentBox = new HBox();
        contentBox.getStyleClass().add("modal-content-box");
    }


    public void affiche() {
//        this.getChildren().add(contentBox);
        page.setCenter(this);
    }

    public void fermer() {
        this.getChildren().remove(contentBox);
        page.setCenter(oldPage);
    }

    boolean isValid(IData data) {
        data.getStruct();
        String[] values = data.getValues().split(",");
        for (String value : values) {
            System.out.println(value);
            if (value.isEmpty() || value.equals("null") || value.equals("''")) {
                return false;
            }
        }
        return true;
    }
}