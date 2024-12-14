package main.java.data.entities;

import main.java.data.sql.fieldType;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;

public class Fournisseur implements IData {
    private String nom_societe;
    private int siret;
    private String adresse;
    private String email;

    private LinkedHashMap<String, fieldType> map;
    private String values;

    public Fournisseur(String nom_societe, int siret, String adresse, String email) {
        this.nom_societe = nom_societe;
        this.siret = siret;
        this.adresse = adresse;
        this.email = email;
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

    public String getNom_societe() {
        return nom_societe;
    }

    public void setNom_societe(String nom_societe) {
        this.nom_societe = nom_societe;
    }

    public int getSiret() {
        return siret;
    }

    public void setSiret(int siret) {
        this.siret = siret;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static String getQuery() {
        return "SELECT nom_societe, siret, adresse, email FROM fournisseur";
    }

    @Override
    public String getValues() {
        String res = String.format(
                Locale.US, // par ce que sinon le float est écrit avec une virgule
                "%d, %d, '%s', '%s', '%s'",
                this.nom_societe, this.siret, this.adresse, this.email
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
}
