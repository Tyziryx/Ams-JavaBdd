package main.java.data.entities;

import main.java.data.sql.FieldType;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

public class CommandeAEffectuer implements IData {
    UUID id_commande;
    int id_produit;

    private LinkedHashMap<String, FieldType> map;
    private String values;

    public CommandeAEffectuer(UUID id_commande, int id_produit) {
        this.id_commande = id_commande;
        this.id_produit = id_produit;
    }

    public CommandeAEffectuer(int id_produit) {
        this.id_commande = UUID.randomUUID();
        this.id_produit = id_produit;
    }



    @Override
    public void getStruct() {
        LinkedHashMap<String, FieldType> map = new LinkedHashMap<>();
        map.put("id_commande", FieldType.UUID);
        map.put("id_produit", FieldType.INT4);
        this.map = map;

        StringBuilder values = new StringBuilder();
        values.append("'").append(this.id_commande).append("', ");
        values.append(this.id_produit).append(", ");
        this.values = values.toString();
    }

    @Override
    public String getValues() {
        if (this.values == null) {
            getStruct();
        }
        return this.values;
    }

    @Override
    public HashMap<String, FieldType> getMap() {
        if (this.map == null) {
            getStruct();
        }
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
}
