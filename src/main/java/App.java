package main.java;

import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import main.java.data.sql.Gestion;
import main.java.ui.components.Navbar;
import main.java.ui.pages.PagePrincipale;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class App extends Application {

    private static final String WINDOW_TITLE = "Ma Supérette du Net";
    private static final double WINDOW_WIDTH = 1300;
    private static final double WINDOW_HEIGHT = 800;

    @Override
    public void start(Stage ps) throws Exception {
        ps.setTitle(WINDOW_TITLE);
//        ps.show();
        showApp(ps);
        Gestion.updateCommandeAEffectuer();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void showApp(Stage ps) throws Exception {
        BorderPane page = new BorderPane();
        Navbar navbar = new Navbar(page);
        page.setLeft(navbar);

        PagePrincipale pagePrincipale = new PagePrincipale(page, 20);
        pagePrincipale.getStyleClass().add("content");
        page.setCenter(pagePrincipale);


        Scene scene = new Scene(page, WINDOW_WIDTH, WINDOW_HEIGHT);
        URL url = getClass().getResource("/css/style.css");
        String css = url.toExternalForm();
        page.getStylesheets().add(css);
        scene.getStylesheets().add(css);
        ps.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));

        ps.setScene(scene);
        ps.show();
    }

}
