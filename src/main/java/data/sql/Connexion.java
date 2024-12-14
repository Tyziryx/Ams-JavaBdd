package main.java.data.sql;

import java.sql.*;


public class Connexion {


    protected final String URL = "jdbc:postgresql://db.tidic.fr:5432/superette01" ;
    protected final String ID = "jdbc_user" ;
    protected final String MDP = "xa32vc8b";
    Connection cn = null ; Statement st = null ; ResultSet rs = null ;


    public Connexion() {

    }

    public void connect(){
        try {
            Class.forName("org.postgresql.Driver");
            cn = DriverManager.getConnection(URL , ID , MDP ) ;
            st= cn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
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
