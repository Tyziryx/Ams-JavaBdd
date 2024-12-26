package main.java.data.entities;

import main.java.data.sql.FieldType;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Vente implements IData {

    int numero_ticket;
    int id_produit;
    int num_lot;
    Date date_vente;
    float prix_unite;
    float prix_final;
    int quantite;

    // LinkedHashMap pour garder l'ordre des colonnes
    private LinkedHashMap<String, FieldType> map;
    private String values;

    public Vente(int numero_ticket, int id_produit, int num_lot, Date vente, float prix_unite, float prix_final, int quantite) {
        this.numero_ticket = numero_ticket;
        this.id_produit = id_produit;
        this.num_lot = num_lot;
        this.date_vente = vente;
        this.prix_unite = prix_unite;
        this.prix_final = prix_final;
        this.quantite = quantite;
    }

    public int getNumero_ticket() {
        return numero_ticket;
    }

    public void setNumero_ticket(int numero_ticket) {
        this.numero_ticket = numero_ticket;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public int getNum_lot() {
        return num_lot;
    }

    public void setNum_lot(int num_lot) {
        this.num_lot = num_lot;
    }

    public Date getDate_vente() {
        return date_vente;
    }

    public void setDate_vente(Date date_vente) {
        this.date_vente = date_vente;
    }

    public float getPrix_unite() {
        return prix_unite;
    }

    public void setPrix_unite(float prix_unite) {
        this.prix_unite = prix_unite;
    }

    public float getPrix_final() {
        return prix_final;
    }

    public void setPrix_final(float prix_final) {
        this.prix_final = prix_final;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public void getStruct() {
        LinkedHashMap<String, FieldType> map = new LinkedHashMap<>();
        map.put("numero_ticket", FieldType.INT4);
        map.put("id_produit", FieldType.INT4);
        map.put("num_lot", FieldType.INT4);
        map.put("date_vente", FieldType.DATE);
        map.put("prix_unite", FieldType.FLOAT8);
        map.put("prix_final", FieldType.FLOAT8);
        map.put("quantite", FieldType.INT4);

        this.map = map;
    }

    @Override
    public String getValues() {
        if (values == null) {
            StringBuilder values = new StringBuilder();
            for (String key : map.keySet()) {
                if (values.length() > 0) {
                    values.append(", ");
                }
                values.append(key);
            }
            this.values = values.toString();
        }
        return values;
    }

    @Override
    public String toString() {
        return numero_ticket + " " + id_produit + " " + num_lot + " " + date_vente + " " + prix_unite + " " + prix_final + " " + quantite;
    }

    public void setValues(String values) {
        this.values = values;
    }

    @Override
    public HashMap<String, FieldType> getMap() {
        return map;
    }

    public void setMap(LinkedHashMap<String, FieldType> map) {
        this.map = map;
    }

    public void setMap(HashMap<String, FieldType> map) {
        this.map = new LinkedHashMap<>(map);
    }


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
        return "SELECT numero_ticket, id_produit, num_lot, date_vente, prix_unite, prix_final, quantite FROM vente";
    }


}





