package main.java.ui.pages;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import main.java.data.sql.Tables;
import main.java.ui.components.Table;
import main.java.util.Colonne;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class PageAchats extends Page {
    public PageAchats(BorderPane page, double spacing) throws SQLException {
        super(spacing, "Achats");

        ObservableList<Node> components = this.getChildren();

        LinkedList<Colonne> colonnes = new LinkedList<Colonne>(){{
            add(new Colonne("Nom société", "nom_societe", 110));
            add(new Colonne("Siret", "siret", 85));
            add(new Colonne("Adresse", "adresse", 100));
            add(new Colonne("Email", "email", 100));
        }};
        Table table = new Table(page, Tables.UNDEFINED, "SELECT * FROM fournisseur", "Fournisseurs", colonnes, true, false);

        components.addAll(title, table);

    }
}
