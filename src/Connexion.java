import java.sql.*;


public class Connexion {


    String url = "db.tidic.fr" ;
    String id = "jdbc" ;
    String mdp = "xa32vc8b" ;
    Connection cn = null ; Statement st = null ; ResultSet rs = null ;


    public Connexion() {
        System.out.println("j'essaie");


    }
}
