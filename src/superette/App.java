package superette;

import javafx.scene.layout.BorderPane;
import superette.ui.components.Navbar;
import superette.ui.pages.PagePrincipale;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static final String WINDOW_TITLE = "Ma Supérette du Net";
    private static final double WINDOW_WIDTH = 1080;
    private static final double WINDOW_HEIGHT = 720;

    @Override
    public void start(Stage ps) throws Exception {
        BorderPane page = new BorderPane();
        Navbar navbar = new Navbar(page);
        page.setLeft(navbar);

        PagePrincipale pagePrincipale = new PagePrincipale(20);
        page.setCenter(pagePrincipale);

        Scene scene = new Scene(page, WINDOW_WIDTH, WINDOW_HEIGHT);
        ps.setScene(scene);
        ps.setTitle(WINDOW_TITLE);
        ps.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
