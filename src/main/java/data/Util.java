package main.java.data;

import main.java.data.sql.Gestion;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Util {

    public static String getProduit() throws SQLException {
        String text = "";
        Gestion g = new Gestion();
        ResultSet rs = g.execute("SELECT * FROM produit;");
        while(rs.next()) {
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                text += rs.getString(i) + " | ";
            }
            text +="\n";
        }

        return text;
    }
}
