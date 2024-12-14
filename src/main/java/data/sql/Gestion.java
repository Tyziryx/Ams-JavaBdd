package main.java.data.sql;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.data.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Gestion {

    /**
     * Retourne la structure d’une table sous la forme <nom de la colonne, type>
     *
     * @param table
     * @param display
     * @return HashMap<String, fieldType>
     * @throws SQLException
     */
    public HashMap<String, fieldType> structTable(String table, boolean display) throws SQLException {
        HashMap<String, fieldType> map = new HashMap<String, fieldType>();
        Connexion cn = new Connexion();

        cn.connect();
        String query = "SELECT column_name, data_type FROM information_schema.columns WHERE table_name = '" + table + "'";
        cn.query(query);

        while (cn.getResult().next()) {
            String column = cn.getResult().getString("column_name");
            String type = cn.getResult().getString("data_type");

            if (type.equals("integer")) {
                type = "int4";
            }
            if (type.equals("character varying")) {
                type = "varchar";
            }
            if (type.equals("double precision")) {
                type = "FLOAT8";
            }

            fieldType ft = fieldType.valueOf(type.toUpperCase());
            map.put(column, ft);

            if (display) {
                System.out.println(column + " : " + type);
            }

        }
        cn.disconnect();
        return map;

    }

    /**
     * Affiche le contenu de n'importe quelle table à l’aide de la méthode getMetaData de la classe PreparedStatement.
     *
     * @param table nom de la table à afficher
     * @throws SQLException
     */
    public void displayTable(String table) throws SQLException {
        Connexion cn = new Connexion();
        cn.connect();
        String query = "SELECT * FROM " + table;
        PreparedStatement ps = cn.getConn().prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        for (int i = 1; i <= columnsNumber; i++) {
            System.out.print(rsmd.getColumnName(i) + " | ");
        }
        System.out.println();
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                System.out.print(rs.getString(i) + " | ");
            }
            System.out.println();
        }
        cn.disconnect();

    }

    /**
     * Exécute une requête SQL hors Insert
     *
     * @param query requête SQL
     * @throws SQLException si la requête contient un INSERT
     */
    public static ResultSet execute(String sql) throws SQLException {
        if (sql.trim().toUpperCase().startsWith("INSERT")) {
            throw new SQLException("On ne peux pas utiliser cette méthode pour les requêtes INSERT");
        }
        Connexion cn = new Connexion();
        cn.connect();
        System.out.println("Connected");
        try {
            Statement stmt = cn.getConn().createStatement();
            return stmt.executeQuery(sql);

        } finally {
            cn.disconnect();
            System.out.println("Disconnected");
        }
    }

    /**
     * Cette méthode permet l’insertion des valeurs des attributs d’une instance d’une classe (data par exemple) dans une table.
     *
     * @param data
     * @param table
     * @throws SQLException
     */
    public void insert(IData data, String table) throws SQLException {
        HashMap<String, fieldType> structTable = structTable(table, false);
        data.getStruct();
        HashMap<String, fieldType> structData = data.getMap();

        if (!data.check(structTable)) {
            throw new SQLException("Data and table structure do not match");
        }

        Connexion cn = new Connexion();
        cn.connect();
        String query = "INSERT INTO " + table + " (";
        String values = " VALUES (";

        for (String key : structData.keySet()) {
            query += key + ", ";
        }

        query = query.substring(0, query.length() - 2) + ")";
        values += data.getValues() + ")";
        query += values;
//        System.out.println(query);

        PreparedStatement ps = cn.getConn().prepareStatement(query);
        ps.executeUpdate();
        cn.disconnect();
    }

    /**
     * Cette méthode permet de récupérer le contenu d'une table
     *
     * @param tables type de table
     * @throws SQLException
     */
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
                break;
            case PRIX_FOURNISSEUR:
                ResultSet rs3 = g.execute(Prix_fournisseur.getQuery());
                while (rs3.next()) {
                    int id_fournisseur = rs3.getInt("id_fournisseur");
                    int id_produit = rs3.getInt("id_produit");
                    float prix = rs3.getFloat("prix");
                    items.add(new main.java.data.entities.Prix_fournisseur(id_fournisseur, id_produit, prix));
                }
                break;
                case CONTRAT:
                ResultSet rs4 = g.execute(Contrat.getQuery());
                while (rs4.next()) {
                    int id_fournisseur = rs4.getInt("id_fournisseur");
                    int id_produit = rs4.getInt("id_produit");
                    float prix = rs4.getFloat("prix");
                    items.add(new main.java.data.entities.Prix_fournisseur(id_fournisseur, id_produit, prix));
                }
                break;
        }
        return FXCollections.observableArrayList(items);
    }

}
