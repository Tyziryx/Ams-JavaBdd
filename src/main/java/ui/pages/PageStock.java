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
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javafx.scene.layout.VBox;
import main.java.data.entities.IData;
import main.java.data.entities.Produit;
import main.java.data.sql.Gestion;
import main.java.data.sql.Tables;
import main.java.ui.components.Table;
import main.java.util.Colonne;

public class PageStock extends Page {
    public PageStock(double spacing) throws SQLException {
        super(spacing, "Stock");

        ObservableList<Node> components = this.getChildren();
        ArrayList<Colonne> tableContent = new ArrayList<Colonne>() {
            {
                add(new Colonne("id_produit", "ID Produit", 100));
                add(new Colonne("quantite", "Quantité", 400));
            }
        };
        Table table = new Table(this,Tables.LOT_DE_PRODUIT, tableContent, true);

        components.addAll(title, table);
    }

}