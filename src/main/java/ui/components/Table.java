package main.java.ui.components;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import main.java.data.entities.IData;
import main.java.data.sql.Gestion;
import main.java.data.sql.Tables;
import main.java.util.Colonne;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class Table extends VBox {
    private TableView<IData> table = new TableView<>();

    public Table(Tables type, ArrayList<Colonne> tableContent, boolean head) throws SQLException {
        super();
        table.getStyleClass().add("table-view");

        for (Colonne colonne : tableContent) {
            TableColumn<IData, String> column = new TableColumn<>(colonne.getTitre());
            column.setCellValueFactory(new PropertyValueFactory<>(colonne.getNom()));
            column.setPrefWidth(colonne.getLargeur());
            column.getStyleClass().add("table-column");
            table.getColumns().add(column);
        }
        if (head) {
            Label tableTitle = new Label(type.toString());
            tableTitle.getStyleClass().add("table-title");

            TextField searchBar = new TextField();
            searchBar.setPromptText("Rechercher...");
            searchBar.getStyleClass().add("search-bar");
            searchBar.setMaxWidth(200);

            ObservableList<IData> data = Gestion.getTable(type);
            FilteredList<IData> filteredData = new FilteredList<>(data, p -> true);

            searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(item -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();
                    return item.toString().toLowerCase().contains(lowerCaseFilter);
                });
            });
            table.setItems(filteredData);

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            HBox header = new HBox();
            header.getStyleClass().add("table-header");
            header.getChildren().addAll(tableTitle, spacer,  searchBar);

            this.getChildren().add(header);
        } else {
            this.getChildren().add(table);
        }
        VBox.setVgrow(this, Priority.ALWAYS);
        this.getStyleClass().add("table-box");
        this.getChildren().addAll(table);
    }


    public TableView.TableViewSelectionModel<IData> getSelectionModel() {
        return table.getSelectionModel();
    }}
