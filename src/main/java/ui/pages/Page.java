package main.java.ui.pages;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public abstract class Page extends VBox {
    protected BorderPane page;
    Text title;
    public Page(double spacing, String title) {
        super(spacing);
        this.title = new Text(title);
        this.title.getStyleClass().add("content-title");
    }
}
