package main.java.ui.pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.java.data.Util;
import main.java.data.entities.IData;
import main.java.data.entities.Produit;
import main.java.data.sql.Tables;

import java.sql.SQLException;

public class PageFournisseurs extends VBox implements Page{
    public PageFournisseurs(double spacing) throws SQLException {
        super(spacing);

        ObservableList<Node> components = this.getChildren();

        Text title = new Text("C'est la page du Stock");

        TableView<IData> table = new TableView<>();
        table.getStyleClass().add("table-view");
        table.setItems(Util.getTable(Tables.FOURNISSEUR));

        TableColumn<IData, String> nom = new TableColumn<>("Nom société");
        nom.setCellValueFactory(new PropertyValueFactory<>("nom_societe"));
        nom.getStyleClass().add("table-column");

        TableColumn<IData, Integer> id = new TableColumn<>("Siret");
        id.setCellValueFactory(new PropertyValueFactory<>("siret"));
        id.getStyleClass().add("table-column");

        TableColumn<IData, String> adresse = new TableColumn<>("adresse");
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        adresse.getStyleClass().add("table-column");

        TableColumn<IData, String> email = new TableColumn<>("email");
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        email.getStyleClass().add("table-column");

        table.getColumns().addAll(nom, id, adresse, email);

        components.addAll(title, table);

    }
}
