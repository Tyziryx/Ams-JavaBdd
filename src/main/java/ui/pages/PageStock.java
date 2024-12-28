package main.java.ui.pages;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import javafx.scene.layout.VBox;
import main.java.data.entities.IData;
import main.java.data.entities.Produit;
import main.java.data.sql.Gestion;
import main.java.data.sql.Tables;
import main.java.ui.components.Table;
import main.java.util.Colonne;

public class PageStock extends Page {
    public PageStock(BorderPane page, double spacing) throws SQLException {
        super(spacing, "Stock");

        ObservableList<Node> components = this.getChildren();
        LinkedList<Colonne> tableContent = new LinkedList<Colonne>() {
            {
                add(new Colonne("id_produit", "ID Produit", 100));
                add(new Colonne("nom", "Nom", 200));
                add(new Colonne("sum", "Quantité", 100));
            }
        };

        LinkedList<Colonne> tableContentProduit = new LinkedList<Colonne>() {
            {
                add(new Colonne("id_produit", "ID Produit", 100));
                add(new Colonne("nom", "Nom", 100));
                add(new Colonne("date_peremption", "Date_Peremption", 200));
                add(new Colonne("quantite", "Quantité", 100));

            }
        };

//        Table table = new Table(this,Tables.LOT_DE_PRODUIT, tableContent, true);
        Table table = new Table(page, this, Tables.UNDEFINED, "SELECT lot_de_produit.id_produit, produit.nom, SUM(quantite) FROM lot_de_produit JOIN produit ON lot_de_produit.id_produit = produit.id_produit GROUP BY lot_de_produit.id_produit, produit.nom;", "Stock", tableContent, true, false);



        Table tableProduit = new Table(page, this, Tables.UNDEFINED, "SELECT lot_de_produit.id_produit, produit.nom,  quantite ,lot_de_produit.date_peremption  FROM lot_de_produit JOIN produit ON lot_de_produit.id_produit = produit.id_produit WHERE date_peremption <= CURRENT_DATE + INTERVAL '30 days'", "Produit Périmés", tableContentProduit, true, false);
//        System.out.println("ici la table : ");
//        System.out.println(tableProduit.getDynamicTable().getItems());
        components.addAll(title, table);

        components.addAll(tableProduit);
    }

}