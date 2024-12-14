package main.java.ui.pages;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class PageFournisseurs extends VBox implements Page{
    public PageFournisseurs(double spacing) {
        super(spacing);

        ObservableList<Node> components = this.getChildren();

        Text someLabel = new Text("C'est la page Fournisseur");
        TextField someTextField = new TextField();

        components.addAll(someLabel, someTextField);

    }
}
