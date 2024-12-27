package main.java.data.entities;

import main.java.data.sql.FieldType;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

public class LotDeProduit implements IData {
    int id_produit;
    int quantite;
    Date date_achat;
    Date date_peremption;
    float prix_achat;
    float prix_unitaire;
    int id_fournisseur;
    UUID id_lot_de_produit;

    private LinkedHashMap<String, FieldType> map;
    private String values;

    public LotDeProduit(int id_produit, int quantite, Date date_achat, Date date_peremption, float prix_achat, float prix_unitaire, int id_fournisseur) {
        this.id_produit = id_produit;
        this.quantite = quantite;
        this.date_achat = date_achat;
        this.date_peremption = date_peremption;
        this.prix_achat = prix_achat;
        this.prix_unitaire = prix_unitaire;
        this.id_fournisseur = id_fournisseur;
        this.id_lot_de_produit = UUID.randomUUID();
    }

    public LotDeProduit(int id_produit, int quantite, Date date_achat, Date date_peremption, float prix_achat, float prix_unitaire, int id_fournisseur, UUID id_lot_de_produit) {
        this.id_produit = id_produit;
        this.quantite = quantite;
        this.date_achat = date_achat;
        this.date_peremption = date_peremption;
        this.prix_achat = prix_achat;
        this.prix_unitaire = prix_unitaire;
        this.id_fournisseur = id_fournisseur;
        this.id_lot_de_produit = id_lot_de_produit;
    }

    @Override
    public void getStruct() {
        LinkedHashMap<String, FieldType> map = new LinkedHashMap<>();
        map.put("id_produit", FieldType.INT4);
        map.put("quantite", FieldType.INT4);
        map.put("date_achat", FieldType.DATE);
        map.put("date_peremption", FieldType.DATE);
        map.put("prix_achat", FieldType.FLOAT8);
        map.put("prix_unitaire", FieldType.FLOAT8);
        map.put("id_fournisseur", FieldType.INT4);
        map.put("id_lot_de_produit", FieldType.UUID);

        this.map = map;

        StringBuilder values = new StringBuilder();
        values.append(this.id_produit).append(", ");
        values.append(this.quantite).append(", ");
        values.append("'"+this.date_achat+"'").append(", ");
        values.append("'"+this.date_peremption+"'").append(", ");
        values.append(this.prix_achat).append(", ");
        values.append(this.prix_unitaire).append(", ");
        values.append(this.id_fournisseur).append(", ");
        values.append("'"+this.id_lot_de_produit+"'");

        this.values = values.toString();
    }

    @Override
    public String getValues() {
        if (this.values == null) {
            this.getStruct();
        }
        return this.values;
    }

    @Override
    public String toString() {
        return "LotDeProduit{" +
                "id_produit=" + id_produit +
                ", quantite=" + quantite +
                ", date_achat=" + date_achat +
                ", date_peremption=" + date_peremption +
                ", prix_achat=" + prix_achat +
                ", prix_unitaire=" + prix_unitaire +
                ", id_fournisseur=" + id_fournisseur +
                ", id_lot_de_produit=" + id_lot_de_produit +
                '}';
    }

    @Override
    public HashMap<String, FieldType> getMap() {
        return map;
    }

    @Override
    public boolean check(HashMap<String, FieldType> tableStruct) {
        if (this.map.size() != tableStruct.size()) {
            return false;
        }
        for (String key : this.map.keySet()) {
            if (!tableStruct.containsKey(key) || tableStruct.get(key) != this.map.get(key)) {
                return false;
            }
        }
        return true;
    }

    public String getQuery() {
        return "SELECT id_produit, quantite, date_achat, date_peremption, prix_achat, prix_unitaire, id_fournisseur, id_lot_de_produit FROM lot_de_produit";
    }
}
