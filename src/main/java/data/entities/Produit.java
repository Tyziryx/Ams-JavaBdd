package main.java.data.entities;

import main.java.data.sql.fieldType;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;

public class Produit implements IData {
    int id_produit;
    int id_achat;
    String nom;
    String description;
    String categorie;

    // LinkedHashMap pour garder l'ordre des colonnes
    private LinkedHashMap<String, fieldType> map;
    private String values;

    public Produit(int id_produit, int id_achat, String nom, String description, String categorie) {
        this.id_produit = id_produit;
        this.id_achat = id_achat;
        this.nom = nom.replaceAll("'", "''");
        this.description = description.replaceAll("'", "''");
        this.categorie = categorie.replaceAll("'", "''");
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public int getId_achat() {
        return id_achat;
    }

    public void setId_achat(int id_achat) {
        this.id_achat = id_achat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    @Override
    public void getStruct() {
        LinkedHashMap<String, fieldType> map = new LinkedHashMap<>();
        map.put("id_produit", fieldType.INT4);
        map.put("id_achat", fieldType.INT4);
        map.put("nom", fieldType.VARCHAR);
        map.put("description", fieldType.VARCHAR);
        map.put("categorie", fieldType.VARCHAR);
        map.put("prix_vente", fieldType.FLOAT8);

        this.map = map;

        StringBuilder values = new StringBuilder();
        for (String key : map.keySet()) {
            if (values.length() > 0) {
                values.append(", ");
            }
            values.append(key);
        }
        this.values = values.toString();


    }

    @Override
    public String getValues() {
        String res = String.format(
                Locale.US, // par ce que sinon le float est écrit avec une virgule
                "%d, %d, '%s', '%s', '%s', %f",
                this.id_produit, this.id_achat, this.nom, this.description, this.categorie
        );
        return res;
    }

    @Override
    public String toString() {
        return id_produit + " " + id_achat + " " + nom + " " + description + " " + categorie;
    }

    @Override
    public HashMap<String, fieldType> getMap() {
        return this.map;
    }

    @Override
    public boolean check(HashMap<String, fieldType> tableStruct) {
        if (this.map.size() != tableStruct.size()) {
            return false;
        }
        for (String key : this.map.keySet()) {
            if (!tableStruct.containsKey(key) || tableStruct.get(key) != this.map.get(key)) {
//                System.out.println("Key: " + key + " tableStruct: " + tableStruct.get(key) + " this.map: " + this.map.get(key));
                return false;
            }
        }
        return true;
    }

    public static String getQuery() {
        return "SELECT id_produit, id_achat, nom, description, categorie FROM produit;";
    }
}
