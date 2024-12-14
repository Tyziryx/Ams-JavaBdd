package main.java.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.data.entities.IData;
import main.java.data.entities.Produit;
import main.java.data.entities.Fournisseur;
import main.java.data.sql.Gestion;
import main.java.data.sql.Tables;
import main.java.ui.pages.PageStock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Util {

    public static ObservableList<IData> getTable(Tables tables) throws SQLException {
        List<IData> items = new ArrayList<>();
        Gestion g = new Gestion();

        switch (tables) {
            case PRODUIT:
                ResultSet rs = g.execute(Produit.getQuery());
                while (rs.next()) {
                    int id = rs.getInt("id_produit");
                    int id_achat = rs.getInt("id_achat");
                    String nom = rs.getString("nom");
                    String desc = rs.getString("description");
                    String categorie = rs.getString("categorie");
                    items.add(new Produit(id, id_achat, nom, desc, categorie));
                }
                break;
            case FOURNISSEUR:
                ResultSet rs2 = g.execute(Fournisseur.getQuery());
                while (rs2.next()) {
                    String nom_societe = rs2.getString("nom_societe");
                    int siret = rs2.getInt("siret");
                    String adresse = rs2.getString("adresse");
                    String email = rs2.getString("email");
                    items.add(new Fournisseur(nom_societe, siret, adresse, email));
                }
        }
        return FXCollections.observableArrayList(items);
    }

}
