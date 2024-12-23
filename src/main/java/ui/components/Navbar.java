package main.java.ui.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import main.java.ui.pages.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Navbar extends VBox {

    private BorderPane page;

    public Navbar(BorderPane page) throws SQLException, IOException {
        this.page = page;
        this.setSpacing(10);

        Label navbarTitle = new Label("");
        Map<String, Page> buttonList = new LinkedHashMap<String, Page>() {{
            put("Accueil", new PagePrincipale(20));
            put("Fournisseurs", new PageFournisseurs(20));
            put("Achats", new PageAchats(20));
            put("Ventes", new PageVentes(20));
            put("Stock", new PageStock(20));
        }};

        for (Map.Entry<String, Page> entry : buttonList.entrySet()) {
            // Récup du nom et de la page
            String name = entry.getKey();
            Page pageType = entry.getValue();
            this.getStyleClass().add("navbar");

            // Creation et setup du boutton
            Button button = new Button();
            button.getStyleClass().add("navbar-button"); // Classe générale du bouton
            HBox hBox = new HBox(10); // Espacement entre icône et texte

            // Création de l'icône SVG via la classe CSS



            Image icon = new Image("/icons/"+name.toLowerCase()+".png");
            ImageView iconView = new ImageView(icon);

            // Création du texte du bouton
            Label label = new Label(name);
            label.getStyleClass().add("navbar-label");

            // Ajout de l'icône et du texte dans le HBox
            hBox.getChildren().addAll(iconView, label);

            // Attachement du HBox au bouton
            button.setGraphic(hBox);


            // Ajout de la fonction qui permet de switch de page
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    page.setCenter((Node) pageType);
                }
            });

            // Ajout du bouton a la navbar
            this.getChildren().add(button);
        }
    }
}