package main.java.ui.pages;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;

public class PagePrincipale extends VBox implements Page{
    public PagePrincipale(double spacing) {
        super(spacing);

        ObservableList<Node> components = this.getChildren();

        Text someLabel = new Text("C'est la page Principale");

        components.addAll(someLabel);

    }
}
