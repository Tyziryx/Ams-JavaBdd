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

public class PageVentes extends Page {
    public PageVentes(double spacing) throws SQLException {
        super(spacing, "Ventes");

        HBox tableBox = new HBox();
        ObservableList<Node> components = this.getChildren();

        TableView<IData> table = new TableView<>();
        table.getStyleClass().add("table-view");
        table.setItems(Gestion.getTable(Tables.VENTE));
        // numero_ticket, id_produit, num_lot, date_vente, prix_unite, prix_final, quantite

        TableColumn<IData, Integer> numero_ticket = new TableColumn<>("Numero Ticket");
        numero_ticket.setCellValueFactory(new PropertyValueFactory<>("numero_ticket"));
        numero_ticket.getStyleClass().add("table-column");

        TableColumn<IData, Integer> id_produit = new TableColumn<>("ID Produit");
        id_produit.setCellValueFactory(new PropertyValueFactory<>("id_produit"));
        id_produit.getStyleClass().add("table-column");

        TableColumn<IData, Integer> num_lot = new TableColumn<>("Numero Lot");
        num_lot.setCellValueFactory(new PropertyValueFactory<>("num_lot"));
        num_lot.getStyleClass().add("table-column");

        TableColumn<IData, String> date_vente = new TableColumn<>("Date Vente");
        date_vente.setCellValueFactory(new PropertyValueFactory<>("date_vente"));
        date_vente.getStyleClass().add("table-column");

        TableColumn<IData, Float> prix_unite = new TableColumn<>("Prix Unite");
        prix_unite.setCellValueFactory(new PropertyValueFactory<>("prix_unite"));
        prix_unite.getStyleClass().add("table-column");

        TableColumn<IData, Float> prix_final = new TableColumn<>("Prix Final");
        prix_final.setCellValueFactory(new PropertyValueFactory<>("prix_final"));
        prix_final.getStyleClass().add("table-column");

        TableColumn<IData, Integer> quantite = new TableColumn<>("Quantite");
        quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        quantite.getStyleClass().add("table-column");

        table.getColumns().addAll(numero_ticket, id_produit, num_lot, date_vente, prix_unite, prix_final, quantite);

        tableBox.prefWidthProperty().bind(this.widthProperty());
        tableBox.prefHeightProperty().bind(this.heightProperty());

        table.prefWidthProperty().bind(tableBox.widthProperty());
        table.prefHeightProperty().bind(tableBox.heightProperty());

        tableBox.getStyleClass().add("table-box");
        tableBox.getChildren().addAll(table);

        components.addAll(title, tableBox);

    }
}
