package data;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class Gestion {


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
            fieldType ft = fieldType.valueOf(type.toUpperCase());
            map.put(column, ft);

            if (display) {
                System.out.println(column + " : " + type);
            }

        }
        cn.disconnect();
        return map;

    }

    public void displayTable(String table) throws SQLException {

        //Affiche le contenu de n'importe quelle table à l’aide de la méthode getMetaData de la classe PreparedStatement.
        PreparedStatement cn = new Connexion().cn.prepareStatement("SELECT * FROM " + table);
        cn.getMetaData();

        System.out.println(cn.getMetaData());
    }
}
