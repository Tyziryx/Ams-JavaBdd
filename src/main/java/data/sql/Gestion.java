package main.java.data.sql;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.data.entities.*;
import main.java.ui.components.Table;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class Gestion {

    /**
     * Retourne la structure d’une table sous la forme <nom de la colonne, type>
     *
     * @param tableType
     * @param display
     * @return HashMap<String, fieldType>
     * @throws SQLException
     */
    public static LinkedHashMap<String, FieldType> structTable(Tables tableType, boolean display) throws SQLException {
        String table = tableType.toString().toLowerCase();
        LinkedHashMap<String, FieldType> map = new LinkedHashMap<>();
        Connexion cn = new Connexion();
        table = table.toLowerCase();

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

            FieldType ft = FieldType.valueOf(type.toUpperCase());
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
    public static void displayTable(String table) throws SQLException {
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
     * @param sql requête SQL
     * @throws SQLException si la requête contient un INSERT
     */
    public static ResultSet execute(String sql) throws SQLException {
        if (sql.trim().toUpperCase().startsWith("INSERT")) {
            throw new SQLException("On ne peux pas utiliser cette méthode pour les requêtes INSERT");
        }
        Connexion cn = new Connexion();
        cn.connect();
        //System.out.println("Connected");
        try {
            Statement stmt = cn.getConn().createStatement();
            return stmt.executeQuery(sql);

        } finally {
            cn.disconnect();
            //System.out.println("Disconnected");
        }
    }

    /**
     * Cette méthode permet l’insertion des valeurs des attributs d’une instance d’une classe (data par exemple) dans une table.
     *
     * @param data
     * @param table
     * @throws SQLException
     */
    public static void insert(IData data, Tables table) throws SQLException {
        HashMap<String, FieldType> structTable = structTable(table, false);

        data.getStruct();
        HashMap<String, FieldType> structData = data.getMap();

        if (!data.check(structTable)) {
            throw new SQLException("Data and table structure do not match");
        }

        Connexion cn = new Connexion();
        cn.connect();
        String query = "INSERT INTO " + table + " (";
        String values = " VALUES (";

        for (String key : structData.keySet()) {
//            System.out.println(key + " : " + structData.get(key));e
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

    public static void update(IData data, Tables table) throws SQLException {
        HashMap<String, FieldType> structTable = structTable(table, false);

        data.getStruct();
        HashMap<String, FieldType> structData = data.getMap();

        if (!data.check(structTable)) {
            throw new SQLException("Data and table structure do not match");
        }

        String[] colonnes = structTable.keySet().toArray(new String[0]);
        String[] valeurs = data.getValues().split(", ");

        Connexion cn = new Connexion();
        cn.connect();
        String query = "UPDATE " + table + " SET ";
        String where = " WHERE ";

        for(int i = 0; i < colonnes.length; i++) {
            query += colonnes[i] + " = " + valeurs[i] + ", ";
        }

        query = query.substring(0, query.length() - 2);
        switch (table) {
            case PRODUIT:
                where += "id_produit = " + valeurs[0];
                break;
            case FOURNISSEUR:
                where += "siret = " + valeurs[1];
                break;
            case PRIX_FOURNISSEUR:
                where += "id_fournisseur = " + valeurs[5] + " AND id_produit = " + valeurs[0];
                break;
            case CONTRAT:
                where += "id_contrat = '" + UUID.fromString(valeurs[6].replace("'", "")) + "'";
                break;
            case VENTE:
                where += "numero_ticket = " + valeurs[0] + " AND id_produit = " + valeurs[1] + " AND num_lot = " + valeurs[2];
                break;
        }
        query += where;
        System.out.println(query);
//        System.out.println(query);
        PreparedStatement ps = cn.getConn().prepareStatement(query);
        ps.executeUpdate();
        cn.disconnect();
    }

    public static boolean delete(ObservableList items, Tables table) throws SQLException {

        switch (table) {
            case PRODUIT:
                int id_produit = (int) items.get(0);
                int id_achat = (int) items.get(1);
                String nom = (String) items.get(2);
                String description = (String) items.get(3);
                String categorie = (String) items.get(4);
                Produit produit = new Produit(id_produit, id_achat, nom, description, categorie);
                return delete(produit, table);
            case FOURNISSEUR:
                String nom_societe = (String) items.get(0);
                int siret = (int) items.get(1);
                String adresse = (String) items.get(2);
                String email = (String) items.get(3);
                Fournisseur fournisseur = new Fournisseur(nom_societe, siret, adresse, email);
                return delete(fournisseur, table);
            case PRIX_FOURNISSEUR:
                int id_fournisseur = (int) items.get(0);
                int id_produit2 = (int) items.get(1);
                float prix = (float) items.get(2);
                PrixFournisseur prixFournisseur = new PrixFournisseur(id_fournisseur, id_produit2, prix);
                return delete(prixFournisseur, table);
            case CONTRAT:
                int id_produit3 = Integer.valueOf((String) items.get(0));
                int quantite_min = Integer.valueOf((String) items.get(1));
                Date date_debut = Date.valueOf((String) items.get(2));
                Date date_fin = Date.valueOf((String) items.get(3));
                float prix_produit = Float.valueOf((String) items.get(4));
                int id_fournisseur2 = Integer.valueOf((String) items.get(5));
                UUID id_contrat = UUID.fromString((String) items.get(6));
                Contrat contrat = new Contrat(id_contrat, id_fournisseur2, id_produit3, quantite_min, date_debut, date_fin, prix_produit);
                return delete(contrat, table);
            case VENTE:
                int numero_ticket = (int) items.get(0);
                int id_produit4 = (int) items.get(1);
                Date date_vente = (Date) items.get(3);
                float prix_unite = (float) items.get(4);
                int quantite = (int) items.get(5);
                Vente vente = new Vente(numero_ticket, id_produit4, date_vente, prix_unite, quantite);
                return delete(vente, table);

        }
        return false;
    }

    public static boolean delete(IData data, Tables table) throws SQLException {
        HashMap<String, FieldType> structTable = structTable(table, false);

        data.getStruct();
        HashMap<String, FieldType> structData = data.getMap();

        if (!data.check(structTable)) {
            throw new SQLException("Data and table structure do not match");
        }

        String[] colonnes = structTable.keySet().toArray(new String[0]);
        String[] valeurs = data.getValues().split(", ");

        Connexion cn = new Connexion();
        cn.connect();
        String query = "DELETE FROM " + table + " WHERE ";

        switch (table) {
            case PRODUIT:
                query += "id_produit = " + valeurs[0];
                break;
            case FOURNISSEUR:
                query += "siret = " + valeurs[1];
                break;
            case PRIX_FOURNISSEUR:
                query += "id_fournisseur = " + valeurs[0] + " AND id_produit = " + valeurs[1];
                break;
            case CONTRAT:
                query += "id_contrat = '" + UUID.fromString(valeurs[6].replace("'", "")) + "'";
                break;
            case VENTE:
                query += "numero_ticket = " + valeurs[0] + " AND id_produit = " + valeurs[1] + " AND num_lot = " + valeurs[2];
                break;
        }
        System.out.println(query);
        PreparedStatement ps = cn.getConn().prepareStatement(query);
        ps.executeUpdate();
        cn.disconnect();
        return true;
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
                ResultSet rs3 = g.execute(PrixFournisseur.getQuery());
                while (rs3.next()) {
                    int id_fournisseur = rs3.getInt("id_fournisseur");
                    int id_produit = rs3.getInt("id_produit");
                    float prix = rs3.getFloat("prix");
                    items.add(new PrixFournisseur(id_fournisseur, id_produit, prix));
                }
                break;
            case CONTRAT:
                ResultSet rs4 = g.execute(Contrat.getQuery());
                while (rs4.next()) {
                    int id_fournisseur = rs4.getInt("id_fournisseur");
                    int id_produit = rs4.getInt("id_produit");
                    float prix = rs4.getFloat("prix_produit");
                    Date date_debut = rs4.getDate("date_debut");
                    Date date_fin = rs4.getDate("date_fin");
                    int quantite_min = rs4.getInt("quantite_min");
                    items.add(new Contrat(id_fournisseur, id_produit, quantite_min, date_debut, date_fin, prix));
                }
                break;
            case VENTE:
                ResultSet rs5 = g.execute(Vente.getQuery());
                // numero_ticket, id_produit, num_lot, date_vente, prix_unite, prix_final, quantite
                while (rs5.next()) {
                    int numero_ticket = rs5.getInt("numero_ticket");
                    int id_produit = rs5.getInt("id_produit");
                    Date vente = rs5.getDate("date_vente");
                    float prix_unite = rs5.getFloat("prix_unite");
                    int quantite = rs5.getInt("quantite");
                    items.add(new Vente(numero_ticket, id_produit, vente, prix_unite, quantite));

                }
                break;
        }
        return FXCollections.observableArrayList(items);
    }

    /**
     * Cette méthode permet de convertir une LocalDate en Date
     *
     * @param localDate
     * @return
     */
    public static java.sql.Date localDateToSqlDate(LocalDate localDate) {
        Date date = java.sql.Date.valueOf(localDate);
        return java.sql.Date.valueOf(localDate);
    }

//fonction qui permet de joindre deux tables
 /* public static Object[] joinTables(Tables table1, Tables table2, String joinCondition1 , String joinCondition2) throws SQLException {
    Connexion cn = new Connexion();
    cn.connect();
    String query = "SELECT * FROM " + table1 + " JOIN " + table2 + " ON " + table1 + "." + joinCondition1 + " = " + table2 + "." +  joinCondition2;
    Statement stmt = cn.getConn().createStatement();
    ResultSet rs = stmt.executeQuery(query);
    cn.disconnect();

     Object[] inputs = new Object[rs.getMetaData().getColumnCount()];
     for (int i = 0; i <= rs.getMetaData().getColumnCount(); i++) {
         inputs[i] = rs.getObject(i);
         System.out.println(inputs[i]);
     }
     return inputs ;
 }
 */



}
