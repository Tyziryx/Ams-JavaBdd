package data;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException {

        Gestion g = new Gestion();
        g.execute("CREATE TABLE produit (id_produit INT PRIMARY KEY, id_achat INT, nom VARCHAR(255), description VARCHAR(255), categorie VARCHAR(255), prix_vente FLOAT);");
        g.insert(new Produit(1, 2, "Fromage", "Fromage de chèvre", "nourriture", 3.5), "produit");
        g.insert(new Produit(2, 5, "Huile moteur", "Huile moteur 5W30", "automobile", 10.5), "produit");
        g.insert(new Produit(3, 3, "Crayon", "Crayon pour dessiner et écrire.", "fournitures", 2.5), "produit");
        g.insert(new Produit(4, 1, "Jambon", "Jambon supérieur 100% pur porc sans couenne", "nourriture", 4.5), "produit");
        g.insert(new Produit(5, 4, "Papier toilette", "Papier toilette trois couche", "fournitures", 1.5), "produit");
        g.insert(new Produit(6, 6, "Pneu", "Pneu 205/55 R16", "automobile", 50.5), "produit");


        g.displayTable("produit");
        g.execute("DELETE FROM produit WHERE id_produit = 1;");
        g.displayTable("produit");
        g.execute("DROP TABLE produit;");




    }






}
