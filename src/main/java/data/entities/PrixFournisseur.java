package main.java.data.entities;

import main.java.data.sql.FieldType;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class PrixFournisseur implements IData {

    int id_fournisseur;
    int id_produit;
    float prix;
    private LinkedHashMap<String, FieldType> map;

    public PrixFournisseur(int id_fournisseur, int id_produit, float prix) {
        this.id_fournisseur = id_fournisseur;
        this.id_produit = id_produit;
        this.prix = prix;
    }


    @Override
    public void getStruct() {
        map = new LinkedHashMap<>();
        map.put("id_fournisseur", FieldType.INT4);
        map.put("id_produit", FieldType.INT4);
        map.put("prix", FieldType.FLOAT8);
    }

    @Override
    public String getValues() {
        StringBuilder values = new StringBuilder();
        for (String key : map.keySet()) {
            if (values.length() > 0) {
                values.append(", ");
            }
            values.append(key);
        }
        return values.toString();
    }

    public void setId_fournisseur(int id_fournisseur) {
        this.id_fournisseur = id_fournisseur;
    }

    public int getId_fournisseur() {
        return id_fournisseur;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public float getPrix() {
        return prix;
    }


    @Override
    public HashMap<String, FieldType> getMap() {

        return null;
    }

    @Override
    public String toString() {
        return id_fournisseur + " " + id_produit + " " + prix;
    }

    @Override
    public boolean check(HashMap<String, FieldType> tableStruct) {
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
        return "SELECT id_fournisseur, id_produit, prix FROM prix_fournisseur";
    }


}


