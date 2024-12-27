package main.java.ui.pages;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableRow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import main.java.data.entities.Fournisseur;
import main.java.data.entities.IData;
import main.java.data.sql.Tables;
import main.java.ui.components.ModalFournisseurs;
import main.java.ui.components.Table;
import main.java.util.Colonne;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class PageFournisseurs extends Page {
    private final BorderPane page;
    public ModalFournisseurs modalFournisseurs;

    public PageFournisseurs(BorderPane page, double spacing) throws SQLException {
        super(spacing, "Fournisseurs");
        this.page = page;

        ArrayList<Colonne> tableContentFournisseurs = new ArrayList<Colonne>() {
            {
                add(new Colonne("nom_societe", "Nom société", 150));
                add(new Colonne("siret", "Siret", 100));
            }
        };
        Table tableFournisseurs = new Table(this, Tables.FOURNISSEUR, tableContentFournisseurs, true);


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
        Table tableContrats = new Table(page, this, Tables.CONTRAT,"SELECT * FROM contrat JOIN produit ON contrat.id_produit = produit.id_produit ", "Contrats", tableContentContrats, true, true);
        tableFournisseurs.getTable().getStyleClass().add("table-view");
        HBox tables = new HBox();
        HBox.setHgrow(tableContrats, Priority.ALWAYS);
        tables.getChildren().addAll(tableFournisseurs, tableContrats);



        ObservableList<Node> components = this.getChildren();
        components.addAll(title, tables );
        tableFournisseurs.getStyleClass().add("fournisseurs");

        tableFournisseurs.getTable().setRowFactory(tv -> {
            TableRow<IData> row = new TableRow<>();
            row.getStyleClass().add("row");
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    System.out.println("Double click");
                    Fournisseur fournisseur = (Fournisseur)row.getItem();
                    try {
                        modalFournisseurs = new ModalFournisseurs(page, this,20, "Fournisseur", fournisseur);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    modalFournisseurs.affiche();                }
            });
            return row;
        });}





}


