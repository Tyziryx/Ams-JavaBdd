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
import main.java.ui.components.Table;
import main.java.util.Colonne;

import java.sql.SQLException;
import java.util.ArrayList;

public class PageVentes extends Page {
    public PageVentes(double spacing) throws SQLException {
        super(spacing, "Ventes");

        ObservableList<Node> components = this.getChildren();

        ArrayList<Colonne> colonnes = new ArrayList<Colonne>(){{
            add(new Colonne("numero_ticket", "Numero Ticket", 100));
            add(new Colonne("id_produit", "ID Produit", 100));
            add(new Colonne("num_lot", "Numero Lot", 100));
            add(new Colonne("date_vente", "Date Vente", 100));
            add(new Colonne("prix_unite", "Prix Unite", 100));
            add(new Colonne("prix_final", "Prix Final", 100));
            add(new Colonne("quantite", "Quantite", 100));
        }};

        Table table = new Table(Tables.VENTE, colonnes, true);

        components.addAll(title, table);

    }
}