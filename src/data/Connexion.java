package data;

import java.sql.*;


public class Connexion {


    String url = "jdbc:postgresql://db.tidic.fr:5432/postgres" ;
    String id = "postgres" ;
    String mdp = "bj%4U3c_84";
    Connection cn = null ; Statement st = null ; ResultSet rs = null ;


    public Connexion() {

    }

    public void connect(){
        try {
            Class.forName("org.postgresql.Driver");
            cn = DriverManager.getConnection(url , id , mdp ) ;
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
}
