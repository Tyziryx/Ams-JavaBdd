package main.java.data.entities;

import javafx.scene.control.TextField;
import main.java.data.sql.FieldType;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;

public class Fournisseur implements IData {
    private String nom_societe;
    private int siret;
    private String adresse;
    private String email;

    private LinkedHashMap<String, FieldType> map;
    private String values;

    public Fournisseur(String nom_societe, int siret, String adresse, String email) {
        this.nom_societe = nom_societe;
        this.siret = siret;
        this.adresse = adresse;
        this.email = email;
    }

    public Fournisseur(Object[] inputs) {
        this.nom_societe = ((TextField) inputs[1]).getText();
        String input = ((TextField) inputs[0]).getText();
        this.siret = Integer.parseInt(input);
        this.adresse = ((TextField) inputs[2]).getText();
        this.email = ((TextField) inputs[3]).getText();
    }

    @Override
    public void getStruct() {
        LinkedHashMap<String, FieldType> map = new LinkedHashMap<>();
        map.put("siret", FieldType.INT4);
        map.put("nom_societe", FieldType.VARCHAR);
        map.put("adresse", FieldType.VARCHAR);
        map.put("email", FieldType.VARCHAR);

        this.map = map;

        StringBuilder values = new StringBuilder();
        values.append("'"+this.nom_societe+"'").append(", ");
        values.append(this.siret).append(", ");
        values.append("'"+this.adresse+"'").append(", ");
        values.append("'"+this.email+"'");
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
                "%d, '%s', '%s', '%s'",
                this.siret, this.nom_societe, this.adresse, this.email
        );
        return res;
    }

    @Override
    public String toString() {
        return nom_societe + " " + siret + " " + adresse + " " + email;
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
    }}

