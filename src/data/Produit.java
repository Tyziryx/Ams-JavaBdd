package data;

import java.util.HashMap;

public class Produit implements IData {
    int id ;
    int numachat;
    String nom ;
    String description;
    String categorie;
    double prixventeactuel;
    private HashMap<String, fieldType> map;
    private String values;

    public Produit(int id, int numachat, String nom, String description, String categorie, double prixventeactuel) {
        this.id = id;
        this.numachat = numachat;
        this.nom = nom;
        this.description = description;
        this.categorie = categorie;
        this.prixventeactuel = prixventeactuel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumachat() {
        return numachat;
    }

    public void setNumachat(int numachat) {
        this.numachat = numachat;
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

    public double getPrixventeactuel() {
        return prixventeactuel;
    }

    public void setPrixventeactuel(double prixventeactuel) {
        this.prixventeactuel = prixventeactuel;
    }

    @Override
    public void getStruct() {
        HashMap<String, fieldType> map = new HashMap<>();
        map.put("id", fieldType.INT4);
        map.put("numachat", fieldType.INT4);
        map.put("nom", fieldType.VARCHAR);
        map.put("description", fieldType.VARCHAR);
        map.put("categorie", fieldType.VARCHAR);
        map.put("prixventeactuel", fieldType.FLOAT8);

        this.map = map;

        StringBuilder valuesBuilder = new StringBuilder();
        for (String key : map.keySet()) {
            if (valuesBuilder.length() > 0) {
                valuesBuilder.append(", ");
            }
            valuesBuilder.append(key);
        }
        this.values = valuesBuilder.toString();


    }

    @Override
    public String getValues() {
        return String.format("%d, %d, '%s', '%s', '%s', %f",
                this.id, this.numachat, this.nom, this.description, this.categorie, this.prixventeactuel);
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
                return false;
            }
        }
        return true;
    }
}
