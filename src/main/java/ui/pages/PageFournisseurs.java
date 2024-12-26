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
import main.java.ui.components.Modal;
import main.java.ui.components.Table;
import main.java.util.Colonne;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

public class PageFournisseurs extends Page {
    private final BorderPane page;
    public Modal modal;

    public PageFournisseurs(double spacing , BorderPane page) throws SQLException {
        super(spacing, "Fournisseurs");
        this.page = page;

        ArrayList<Colonne> tableContentFournisseurs = new ArrayList<Colonne>() {
            {
                add(new Colonne("nom_societe", "Nom société", 150));
                add(new Colonne("siret", "Siret", 100));
            }
        };
        Table tableFournisseurs = new Table(Tables.FOURNISSEUR, tableContentFournisseurs, true);


        ArrayList<Colonne> tableContentContrats = new ArrayList<Colonne>() {
            {
                add(new Colonne("id_fournisseur", "Fournisseur", 110));
                add(new Colonne("quantite_min", "Quantité min", 60));
                add(new Colonne("date_fin", "Date de fin", 100));
                add(new Colonne("prix_produit", "Prix", 60));
                add(new Colonne("nom", "Produit", 100));
            }
        };
        Table tableContrats = new Table("SELECT * FROM contrat JOIN produit ON contrat.id_produit = produit.id_produit ", "Contrats", tableContentContrats, true);

        HBox tables = new HBox();
        HBox.setHgrow(tableContrats, Priority.ALWAYS);
        tables.getChildren().addAll(tableFournisseurs, tableContrats);

        ObservableList<Node> components = this.getChildren();
        components.addAll(title, tables );
        tableFournisseurs.getStyleClass().add("fournisseurs");

        // Ajouter un gestionnaire d'événements pour le tableau des fournisseurs
        tableFournisseurs.getTable().setRowFactory(tv -> {
            TableRow<IData> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    System.out.println("Double click");
                    Fournisseur fournisseur = (Fournisseur)row.getItem();
                    try {
                        modal = new Modal(20, "Fournisseur", fournisseur);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    modal.affiche(page);                }
            });
            return row;
        });}





}


