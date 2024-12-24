package main.java.ui.pages;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.java.data.entities.IData;
import main.java.data.sql.Gestion;
import main.java.data.sql.Tables;

import java.sql.SQLException;

public class PageFournisseurs extends Page{
    public PageFournisseurs(double spacing) throws SQLException {
        super(spacing, "Fournisseurs");

        HBox tableBox = new HBox();
        ObservableList<Node> components = this.getChildren();

        TableView<IData> table = new TableView<>();
        table.getStyleClass().add("table-view");
        table.setItems(Gestion.getTable(Tables.FOURNISSEUR));

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

        tableBox.prefWidthProperty().bind(this.widthProperty());
        tableBox.prefHeightProperty().bind(this.heightProperty());

        table.prefWidthProperty().bind(tableBox.widthProperty());
        table.prefHeightProperty().bind(tableBox.heightProperty());

        tableBox.getStyleClass().add("table-box");
        tableBox.getChildren().addAll(table);

        components.addAll(title, tableBox);

    }
}
