package main.java.data.entities;

import main.java.data.sql.fieldType;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;

public class Contrat implements IData {
    int id_produit;
    int quantite_min;
    Date date_debut;
    Date date_fin;
    float prix_produit;

    // LinkedHashMap pour garder l'ordre des colonnes
    private LinkedHashMap<String, fieldType> map;
    private String values;

    public Contrat(int id_produit, int quantite_min, Date date_debut, Date date_fin, float prix_produit) {
        this.id_produit = id_produit;
        this.quantite_min = quantite_min;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.prix_produit = prix_produit;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public int getQuantite_min() {
        return quantite_min;
    }

    public void setQuantite_min(int quantite_min) {
        this.quantite_min = quantite_min;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public float getPrix_produit() {
        return prix_produit;
    }

    public void setPrix_produit(float prix_produit) {
        this.prix_produit = prix_produit;
    }

    @Override
    public void getStruct() {
        LinkedHashMap<String, fieldType> map = new LinkedHashMap<>();
        map.put("id_produit", fieldType.INT4);
        map.put("quantite_min", fieldType.INT4);
        map.put("date_debut", fieldType.DATE);
        map.put("date_fin", fieldType.DATE);
        map.put("prix_produits", fieldType.FLOAT8);

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
                this.id_produit, this.quantite_min, this.date_debut, this.date_fin, this.prix_produit
        );
        return res;
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
