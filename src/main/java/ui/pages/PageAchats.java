package main.java.ui.pages;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import main.java.data.sql.Tables;
import main.java.ui.components.Table;
import main.java.util.Colonne;

import java.sql.SQLException;
import java.util.ArrayList;

public class PageAchats extends Page {
    public PageAchats(double spacing) throws SQLException {
        super(spacing, "Achats");

        ObservableList<Node> components = this.getChildren();

        ArrayList<Colonne> colonnes = new ArrayList<Colonne>(){{
            add(new Colonne("Nom société", "nom_societe", 110));
            add(new Colonne("Siret", "siret", 85));
            add(new Colonne("Adresse", "adresse", 100));
            add(new Colonne("Email", "email", 100));
        }};
        Table table = new Table(Tables.PRODUIT, colonnes, true);

        components.addAll(title, table);

    }
}
