package data;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Prix_fournisseur implements IData{

    int id_fournisseur;
    int id_produit;
    float prix ;
    private LinkedHashMap<String, fieldType> map;

    public Prix_fournisseur(int id_fournisseur, int id_produit, float prix) {
        this.id_fournisseur = id_fournisseur;
        this.id_produit = id_produit;
        this.prix = prix;
    }


    @Override
    public void getStruct() {
        map = new LinkedHashMap<>();
        map.put("id_fournisseur", fieldType.INT4);
        map.put("id_produit", fieldType.INT4);
        map.put("prix", fieldType.FLOAT8);
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




    public int getId_fournisseur() {return id_fournisseur;}

    public void setId_produit(int id_produit) {this.id_produit = id_produit;}

    public int getId_produit() {return id_produit;}

    public void setPrix(float prix) {this.prix = prix;
    }

    public float getPrix() {return prix;
    }



    @Override
    public HashMap<String, fieldType> getMap() {

        return null;
    }

    @Override
    public boolean check(HashMap<String, fieldType> tableStruct) {

        return false;
    }
}

