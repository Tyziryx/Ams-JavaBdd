package main.java.data.entities;

import javafx.scene.control.TextField;
import main.java.data.sql.FieldType;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;

public class ContactAssocie implements IData {
    private int siret;
    private String nom;
    private String prenom;
    private String fonction;
    private String email;
    private String telephone;

    private LinkedHashMap<String, FieldType> map;
    private String values;

    public ContactAssocie(int siret, String nom, String prenom, String fonction, String email, String telephone) {
        this.siret = siret;
        this.nom = nom;
        this.prenom = prenom;
        this.fonction = fonction;
        this.email = email;
        this.telephone = telephone;
    }

    public ContactAssocie(Object[] objects) {
        this.siret = Integer.parseInt(((TextField)objects[0]).getText());
        this.nom = ((TextField)objects[1]).getText();
        this.prenom = ((TextField)objects[2]).getText();
        this.fonction = ((TextField)objects[3]).getText();
        this.email = ((TextField)objects[4]).getText();
        this.telephone = ((TextField)objects[5]).getText();

        this.nom = this.nom.replaceAll("'", "''");
        this.prenom = this.prenom.replaceAll("'", "''");
        this.fonction = this.fonction.replaceAll("'", "''");
        this.email = this.email.replaceAll("'", "''");
        this.telephone = this.telephone.replaceAll("'", "''");
    }

    @Override
    public void getStruct() {
        LinkedHashMap<String, FieldType> map = new LinkedHashMap<>();
        map.put("siret", FieldType.INT4);
        map.put("nom", FieldType.VARCHAR);
        map.put("prenom", FieldType.VARCHAR);
        map.put("fonction", FieldType.VARCHAR);
        map.put("email", FieldType.VARCHAR);
        map.put("telephone", FieldType.VARCHAR);

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
                Locale.US,
                "%d, '%s', '%s', '%s', '%s', '%s'",
                this.siret, this.nom, this.prenom, this.fonction, this.email, this.telephone
        );
        return res;
    }

    @Override
    public String toString() {
        return "ContactAssocie{" +
                "siret=" + siret +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", fonction='" + fonction + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
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

    public static String getQuery() {
        return "SELECT siret, nom, prenom, fonction, email, telephone FROM contact_associe";
    }
}
