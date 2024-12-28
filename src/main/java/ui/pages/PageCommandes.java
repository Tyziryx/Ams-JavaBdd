package main.java.ui.pages;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableRow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import main.java.data.entities.Fournisseur;
import main.java.data.entities.IData;
import main.java.data.sql.Tables;
import main.java.ui.components.ModalCommandeAEffectuer;
import main.java.ui.components.ModalCommander;
import main.java.ui.components.ModalFournisseurs;
import main.java.ui.components.Table;
import main.java.util.Colonne;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class PageCommandes extends Page {
    public PageCommandes(BorderPane page, double spacing) throws SQLException {
        super(spacing, "Achats");

        ObservableList<Node> components = this.getChildren();

        LinkedList<Colonne> colonnes = new LinkedList<Colonne>(){{
            add(new Colonne("nom", "Produit", 85));
            add(new Colonne("id_produit", "ID Produit", 85));
        }};
        Table tableCommandesAEffectuer = new Table(page, this, Tables.COMMANDE_A_EFFECTUER, "SELECT com.id_commande, com.id_produit, produit.nom FROM commande_a_effectuer as com JOIN produit ON produit.id_produit = com.id_produit", "Commandes à effectuer", colonnes, true, false);
        tableCommandesAEffectuer.getStyleClass().add("commandes");

        LinkedList<Colonne> colonnes2 = new LinkedList<Colonne>(){{
            add(new Colonne("nom", "Produit", 110));
            add(new Colonne("prix", "Prix unité", 85));
            add(new Colonne("description", "Description", 100));
            add(new Colonne("categorie", "Catégorie", 100));
        }};
        Table tableCommander = new Table(page, this, Tables.UNDEFINED, "SELECT * FROM prix_fournisseur JOIN produit ON prix_fournisseur.id_produit = produit.id_produit", "Commander", colonnes2, true, false);
        tableCommander.getStyleClass().add("hoverable");
        tableCommandesAEffectuer.getStyleClass().add("hoverable");

        VBox content = new VBox();
        content.getChildren().addAll(tableCommandesAEffectuer, tableCommander);
        components.addAll(title, content);

        tableCommander.getDynamicTable().setRowFactory(tv -> {
            TableRow<ObservableList<String>> row = new TableRow<>();
            row.getStyleClass().add("row");
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    ModalCommander modal;
                    ObservableList<String> items = row.getItem();
                    try {
                        modal = new ModalCommander(page, this, tableCommander,20, "Commander", items);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    modal.affiche();                }
            });
            return row;
        });

        tableCommandesAEffectuer.getDynamicTable().setRowFactory(tv -> {
            TableRow<ObservableList<String>> row = new TableRow<>();
            row.getStyleClass().add("row");
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
//                    System.out.println(row.getItem());
                    ModalCommandeAEffectuer modal;
                    ObservableList<String> items = row.getItem();
                    try {
                        modal = new ModalCommandeAEffectuer(page, this, tableCommandesAEffectuer,20, "Commander", items);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    modal.affiche();                }
            });
            return row;
        });


    }
}
