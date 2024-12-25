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
