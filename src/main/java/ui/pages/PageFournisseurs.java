package main.java.ui.pages;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.java.data.entities.Fournisseur;
import main.java.data.entities.IData;
import main.java.data.sql.Gestion;
import main.java.data.sql.Tables;
import main.java.ui.components.ModalEdit;
import main.java.ui.components.ModalEditContrat;
import main.java.ui.components.ModalFournisseurs;
import main.java.ui.components.Table;
import main.java.util.Colonne;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class PageFournisseurs extends Page {
    private final BorderPane page;
    public ModalFournisseurs modalFournisseurs;

    /**
     * Classe affichant et gérant les fournisseurs et leurs contrats.
     * Fonctions interactives : double-clic pour détails, clic droit pour gestion (ajout, modification, suppression).
     * @param page
     * @param spacing
     * @throws SQLException
     */
    public PageFournisseurs(BorderPane page, double spacing) throws SQLException {
        super(spacing, "Fournisseurs");
        this.page = page;

        LinkedList<Colonne> tableContentFournisseurs = new LinkedList<Colonne>() {
            {
                add(new Colonne("nom_societe", "Nom société", 150));
                add(new Colonne("siret", "Siret", 100));
            }
        };
        Table tableFournisseurs = new Table(page, this, Tables.FOURNISSEUR, "SELECT siret, nom_societe, adresse, email FROM fournisseur", "Fournisseurs" , tableContentFournisseurs, true, true);


        LinkedList<Colonne> tableContentContrats = new LinkedList<Colonne>() {
            {
                add(new Colonne("id_contrat", "ID", 50));
                add(new Colonne("id_fournisseur", "Fournisseur", 110));
                add(new Colonne("quantite_min", "Quantité min", 60));
                add(new Colonne("date_fin", "Date de fin", 100));
                add(new Colonne("prix_produit", "Prix", 60));
                add(new Colonne("nom", "Produit", 100));
            }
        };
        Table tableContrats = new Table(page, this, Tables.CONTRAT, "SELECT * FROM contrat JOIN produit ON contrat.id_produit = produit.id_produit ", "Contrats", tableContentContrats, true, true);
        tableFournisseurs.getTable().getStyleClass().add("table-view");

        HBox tables = new HBox();
        HBox.setHgrow(tableContrats, Priority.ALWAYS);
        Label descFournisseur = new Label("Double-cliquez sur un fournisseur pour afficher les détails. Ou clique droit pour ajouter, modifier ou supprimer des fournisseurs");
        descFournisseur.getStyleClass().add("desc");
        VBox fournisseurs = new VBox();
        fournisseurs.getChildren().addAll(tableFournisseurs, descFournisseur);

        Label descContrat = new Label("Clic droit sur un contrat pour modifier ou supprimer");
        descContrat.getStyleClass().add("desc");
        VBox contrats = new VBox();
        contrats.getChildren().addAll(tableContrats, descContrat);

        tables.getChildren().addAll(fournisseurs, contrats);

        ObservableList<Node> components = this.getChildren();
        components.addAll(title, tables);
        tableFournisseurs.getStyleClass().add("fournisseurs");

        tableContrats.getDynamicTable().setRowFactory(tv -> {
            TableRow<ObservableList<String>> row = new TableRow<>();
            row.getStyleClass().add("row");
            ContextMenu contextMenu = new ContextMenu();
//                row.setOnContextMenuRequested(event -> {
//                    item[0] = row.getItem();
//                    contextMenu.show(page, event.getScreenX(), event.getScreenY());
//                });
            row.setContextMenu(contextMenu);
            MenuItem menuItem1 = new MenuItem("Ajouter");
            MenuItem menuItem2 = new MenuItem("Modifier");
            MenuItem menuItem3 = new MenuItem("Supprimer");
            menuItem1.setOnAction((event) -> {
                ObservableList<String> item = row.getItem();
//                if (item == null) return;
                try {
                    ModalEditContrat modalEdit = new ModalEditContrat(page, this, tableContrats, 20, "Modifier", item, true);
                    modalEdit.affiche();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            menuItem2.setOnAction((event) -> {
                ObservableList<String> item = row.getItem();
                if (item == null) return;
                try {
                    System.out.println(item);
                    ModalEditContrat modalEdit = new ModalEditContrat(page, this, tableContrats, 20, "Modifier", item, false);
                    modalEdit.affiche();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            menuItem3.setOnAction((event) -> {
                ObservableList<String> item = row.getItem();
                if (item == null) return;
                try {
                    if (Gestion.delete(item, Tables.CONTRAT)) {
                        tableContrats.refreshDynamicTable();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            contextMenu.getItems().addAll(menuItem1, menuItem2, menuItem3);

            return row;
        });
    }

}


