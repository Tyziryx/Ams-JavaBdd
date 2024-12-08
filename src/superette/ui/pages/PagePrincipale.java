package superette.ui.pages;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;

public class PagePrincipale extends VBox implements Page{
    public PagePrincipale(double spacing) {
        super(spacing);

        ObservableList<Node> components = this.getChildren();

        Label someLabel = new Label("C'est la page Principale");
        TextField someTextField = new TextField();

        components.addAll(someLabel, someTextField);

    }
}
