package main.java.data.entities;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import main.java.data.sql.FieldType;
import main.java.data.sql.Gestion;

import java.time.LocalDate;
import java.util.*;

public class Contrat implements IData {
    int id_fournisseur;
    int id_produit;
    int quantite_min;
    java.sql.Date date_debut;
    java.sql.Date date_fin;
    float prix_produit;
    UUID id_contrat;

    // LinkedHashMap pour garder l'ordre des colonnes
    private LinkedHashMap<String, FieldType> map;
    private String values;

    public Contrat(int id_fournisseur , int id_produit, int quantite_min, java.sql.Date date_debut, java.sql.Date date_fin, float prix_produit) {
        this.id_fournisseur = id_fournisseur;
        this.id_produit = id_produit;
        this.quantite_min = quantite_min;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.prix_produit = prix_produit;
    }
    public Contrat(UUID id_contrat ,int id_fournisseur , int id_produit, int quantite_min, java.sql.Date date_debut, java.sql.Date date_fin, float prix_produit) {
        this.id_contrat = id_contrat;
        this.id_fournisseur = id_fournisseur;
        this.id_produit = id_produit;
        this.quantite_min = quantite_min;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.prix_produit = prix_produit;
    }

    public Contrat(Object[] objects) {
        this.id_contrat = (UUID) objects[6];
        this.id_produit = Integer.parseInt(((TextField) objects[0]).getText());
        this.quantite_min = Integer.parseInt(((TextField) objects[1]).getText());
        this.date_debut = Gestion.localDateToSqlDate(((DatePicker) objects[2]).getValue());
        this.date_fin = Gestion.localDateToSqlDate(((DatePicker) objects[3]).getValue());
        this.prix_produit = Float.parseFloat(((TextField) objects[4]).getText());
        this.id_fournisseur = Integer.parseInt(((TextField) objects[5]).getText());
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

    public void setDate_debut(java.sql.Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(java.sql.Date date_fin) {
        this.date_fin = date_fin;
    }

    public float getPrix_produit() {
        return prix_produit;
    }

    public void setPrix_produit(float prix_produit) {
        this.prix_produit = prix_produit;
    }

    public int getId_fournisseur() {
        return id_fournisseur;
    }

    public void setId_fournisseur(int id_fournisseur) {
        this.id_fournisseur = id_fournisseur;
    }

    @Override
    public void getStruct() {
        LinkedHashMap<String, FieldType> map = new LinkedHashMap<>();
        map.put("id_produit", FieldType.INT4);
        map.put("quantite_min", FieldType.INT4);
        map.put("date_debut", FieldType.DATE);
        map.put("date_fin", FieldType.DATE);
        map.put("prix_produit", FieldType.FLOAT8);
        map.put("id_fournisseur", FieldType.INT4);
        map.put("id_contrat", FieldType.UUID);

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
                "%d, %d, '%s', '%s', %f, %d, '%s'",
                this.id_produit, this.quantite_min, this.date_debut, this.date_fin, this.prix_produit, this.id_fournisseur, this.id_contrat
        );
        return res;
    }

    @Override
    public String toString() {
        return id_fournisseur + " " + id_produit + " " + quantite_min + " " + date_debut + " " + date_fin + " " + prix_produit;
    }

    @Override
    public HashMap<String, FieldType> getMap() {
        return this.map;
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
        return "SELECT id_fournisseur, id_produit, quantite_min, date_debut, date_fin, prix_produit FROM contrat";
    }
}
