package data;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println("Hello, World!");

        Gestion cn = new Gestion() ;
        cn.displayTable("test");




    }






}
