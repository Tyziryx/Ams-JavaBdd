package main.java.data.sql;

import java.net.URL;
import java.sql.*;
import java.util.Properties;


public class Connexion {
    private Properties ids = new Properties();
    Connection cn = null ; Statement st = null ; ResultSet rs = null ;

    /**
     * Constructeur de la classe Connexion
     * Il charge les identifiants de connexion à la base de données
     */
    public Connexion() {
        URL path = getClass().getResource("/config.properties");
        try {
            ids.load(path.openStream());
        } catch (Exception e) {
            e.printStackTrace();
            // faire une autre exception
        }
    }

    /**
     * Méthode permettant de se connecter à la base de données
     */
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
            Properties props = new Properties();
            props.setProperty("user", id);
            props.setProperty("password", mdp);
            Class.forName("org.postgresql.Driver");
            cn = DriverManager.getConnection(url , props) ;
            st= cn.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Méthode permettant de se déconnecter de la base de données
     */
    public void disconnect(){
        try {
            cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode permettant d'exécuter une requête SQL
     * @param sql : la requête SQL à exécuter
     */
    public void query(String sql){
        try {
            rs = st.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();

}
    }

    /**
     * Permets de récupérer le résultat de la requête
     * @return
     */
    public ResultSet getResult(){
        return rs;
    }

    /**
     * Permets de récupérer la connexion
     * @return
     */
    public Connection getConn() {
        return cn;
    }

}
