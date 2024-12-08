package superette.ui.pages;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.sql.SQLException;
import superette.data.Util;

public class PageStock extends VBox implements Page{
    public PageStock(double spacing) throws SQLException {
        super(spacing);

        ObservableList<Node> components = this.getChildren();

        Text title = new Text("C'est la page du Stock");
        // Attention getProduit != getStock
        String text = Util.getProduit();
        Text content = new Text(text);

        components.addAll(title, content);

    }
}
