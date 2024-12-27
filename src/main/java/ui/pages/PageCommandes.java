package main.java.ui.pages;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableRow;
import javafx.scene.layout.BorderPane;
import main.java.data.entities.Fournisseur;
import main.java.data.entities.IData;
import main.java.data.sql.Tables;
import main.java.ui.components.ModalCommander;
import main.java.ui.components.ModalFournisseurs;
import main.java.ui.components.Table;
import main.java.util.Colonne;

import java.sql.SQLException;
import java.util.LinkedList;

public class PageCommandes extends Page {
    public PageCommandes(BorderPane page, double spacing) throws SQLException {
        super(spacing, "Achats");

        ObservableList<Node> components = this.getChildren();

        LinkedList<Colonne> colonnes = new LinkedList<Colonne>(){{
            add(new Colonne("nom", "Produit", 110));
            add(new Colonne("prix", "Prix unité", 85));
            add(new Colonne("description", "Description", 100));
            add(new Colonne("categorie", "Catégorie", 100));
        }};
        Table table = new Table(page, this, Tables.UNDEFINED, "SELECT * FROM prix_fournisseur JOIN produit ON prix_fournisseur.id_produit = produit.id_produit", "Commander", colonnes, true, false);
        table.getStyleClass().add("commandes");
        components.addAll(title, table);

        table.getDynamicTable().setRowFactory(tv -> {
            TableRow<ObservableList<String>> row = new TableRow<>();
            row.getStyleClass().add("row");
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    System.out.println("Double click");
                    ModalCommander modal;
                    ObservableList<String> items = row.getItem();
                    try {
                        modal = new ModalCommander(page, this,20, "Commander", items);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    modal.affiche();                }
            });
            return row;
        });
    }
}
