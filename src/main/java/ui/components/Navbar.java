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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.java.ui.pages.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class Navbar extends VBox {

    private BorderPane page;

    public Navbar(BorderPane page) throws Exception {
        this.page = page;
        this.setSpacing(10);
        VBox vBox = new VBox();
        Label navbarTitle = new Label("MaSupduNet");
        navbarTitle.getStyleClass().add("navbar-title");
        vBox.getChildren().add(navbarTitle);
        vBox.getStyleClass().add("navbar");
        vBox.prefHeightProperty().bind(page.heightProperty());

        Map<String, Page> addButtonList = new LinkedHashMap<String, Page>() {{
            put("Accueil", new PagePrincipale(page,20));
            put("Fournisseurs", new PageFournisseurs(page, 20));
            put("Achats", new PageAchats(page, 20));
            put("Ventes", new PageVentes(20));
            put("Stock", new PageStock(20));
        }};
        HashSet<Button> buttonList = new HashSet<>();

        for (Map.Entry<String, Page> entry : addButtonList.entrySet()) {
            // Récup du nom et de la page
            String name = entry.getKey();
            Page pageType = entry.getValue();

            // Creation et setup du boutton
            Button button = new Button();
            button.getStyleClass().add("navbar-button"); // Classe générale du bouton
            if (name.equals("Accueil")) { button.getStyleClass().add("active"); }
            HBox hBox = new HBox(10); // Espacement entre icône et texte

            // Création de l'icône SVG via la classe CSS

            Image icon ;
            try {
                 icon = new Image("icons/"+name.toLowerCase()+".png");
            }
            catch (Exception e) {
                 icon = new Image("icons/accueilgris.png");
            }
            ImageView iconView = new ImageView(icon);
            iconView.getStyleClass().add("icon-image");

          //  iconView.getStyleClass().add(name + "-button"); // Ajout de la classe CSS
            iconView.setFitWidth(18);
            iconView.setFitHeight(18);
            // Taille de l'icône

            // Création du texte du bouton
            Label label = new Label(name);
            label.getStyleClass().add("navbar-label");

            // Ajout de l'icône et du texte dans le HBox
            hBox.getChildren().addAll(iconView, label);

            // Attachement du HBox au bouton
            button.setGraphic(hBox);
            button.getStyleClass().add("hbox");


            // Ajout de la fonction qui permet de switch de page
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    pageType.getStyleClass().add("content");
                    page.setCenter((Node) pageType);
                    for (Button b : buttonList) {
                        b.getStyleClass().remove("active");
                    }
                    button.getStyleClass().add("active");
                }
            });

            // Ajout du bouton a la navbar
            buttonList.add(button);
            vBox.getChildren().add(button);
        }

        this.getChildren().add(vBox);
    }
}