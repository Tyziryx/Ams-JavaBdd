package main.java.ui.pages;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.nio.charset.Charset;
import java.sql.SQLException;

import main.java.data.entities.IData;
import main.java.data.entities.Produit;
import main.java.data.sql.Gestion;
import main.java.data.sql.Tables;

public class PageStock extends Page {
    public PageStock(double spacing) throws SQLException {
        super(spacing, "Stock");

        /* Box du tableau */
        HBox tableBox = new HBox();
        ObservableList<Node> components = this.getChildren();



        /* Tableau */
        TableView<IData> table = new TableView<>();
        table.getStyleClass().add("table-view");


        /* Barre de recherche */
        TextField searchBar = new TextField();
        searchBar.setPromptText("Rechercher...");
        searchBar.getStyleClass().add("search-bar");
        ObservableList<IData> data = Gestion.getTable(Tables.PRODUIT);
        FilteredList<IData> filteredData = new FilteredList<>(data, p -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(iProduct -> {
                Produit product = (Produit) iProduct;
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (product.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (product.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (Integer.toString(product.getId_produit()).contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        table.setItems(filteredData);


        /* Colonnes */
        TableColumn<IData, String> nom = new TableColumn<>("Nom");
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        nom.getStyleClass().add("table-column");

        TableColumn<IData, Integer> id = new TableColumn<>("id");
        id.setCellValueFactory(new PropertyValueFactory<>("id_produit"));
        id.getStyleClass().add("table-column");

        TableColumn<IData, Integer> desc = new TableColumn<>("description");
        desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        desc.getStyleClass().add("table-column");

        title.getStyleClass().add("table-column");

        table.getColumns().addAll(nom, id, desc);


        /* Taille de la box */
        tableBox.prefWidthProperty().bind(this.widthProperty());
        tableBox.prefHeightProperty().bind(this.heightProperty());
        table.prefWidthProperty().bind(tableBox.widthProperty());
        table.prefHeightProperty().bind(tableBox.heightProperty());

        tableBox.getStyleClass().add("table-box");
        tableBox.getChildren().addAll(table);

        components.addAll(title,searchBar, tableBox);
    }

}