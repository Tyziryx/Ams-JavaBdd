package main.java.ui.pages;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.sql.SQLException;

import main.java.data.entities.IData;
import main.java.data.sql.Tables;

public class PageStock extends VBox implements Page {
    public PageStock(double spacing) throws SQLException {
        super(spacing);

        ObservableList<Node> components = this.getChildren();

        Text title = new Text("C'est la page du Stock");

        TableView<IData> table = new TableView<>();
        table.getStyleClass().add("table-view");
        table.setItems(Util.getTable(Tables.PRODUIT));

        TableColumn<IData, String> nom = new TableColumn<>("Nom");
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        nom.getStyleClass().add("table-column");

        TableColumn<IData, Integer> id = new TableColumn<>("id");
        id.setCellValueFactory(new PropertyValueFactory<>("id_produit"));
        id.getStyleClass().add("table-column");

        TableColumn<IData, Integer> desc = new TableColumn<>("description");
        desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        desc.getStyleClass().add("table-column");

        table.getColumns().addAll(nom, id, desc);

        components.addAll(title, table);
    }
}