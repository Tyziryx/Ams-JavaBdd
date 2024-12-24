package main.java.ui.pages;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;

public class PagePrincipale extends Page{
    public PagePrincipale(double spacing) {
        super(spacing, "Page Principale");

        ObservableList<Node> components = this.getChildren();
        components.addAll(title);

    }
}
