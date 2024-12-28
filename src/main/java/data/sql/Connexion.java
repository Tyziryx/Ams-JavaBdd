package main.java.data.sql;

import java.net.URL;
import java.sql.*;
import java.util.Properties;


public class Connexion {
    private Properties ids = new Properties();
    Connection cn = null ; Statement st = null ; ResultSet rs = null ;


    public Connexion() {
        URL path = getClass().getResource("/config.properties");
        try {
            ids.load(path.openStream());
        } catch (Exception e) {
            e.printStackTrace();
            // faire une autre exception
        }
    }

    public void connect(){
        try {
            String url = "";
            String id = "";
            String mdp = "";
            try {
                url = (String) ids.get("db.url");
                id = (String) ids.get("db.id");
                mdp = (String) ids.get("db.mdp");
            } catch (NullPointerException e) {
                e.printStackTrace();
                // Faire uue autre exception
            }
            Class.forName("org.postgresql.Driver");
            cn = DriverManager.getConnection(url , id , mdp ) ;
            st= cn.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void disconnect(){
        try {
            cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void query(String sql){
        try {
            rs = st.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();

}
    }
    public ResultSet getResult(){
        return rs;
    }

    public Connection getConn() {
        return cn;
    }

}
