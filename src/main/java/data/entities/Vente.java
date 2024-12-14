package main.java.data.entities;

import main.java.data.sql.fieldType;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;

public class Vente implements IData {




        int numero_ticket;
        int id_produit;
       int num_lot;
        Date vente;
        float prix_unité;
        float prix_final;,
        int quantité;

        // LinkedHashMap pour garder l'ordre des colonnes
        private LinkedHashMap<String, fieldType> map;
        private String values;

        public Vente(int id_produit, int id_achat, String nom, String description, String categorie) {
           this.numero_ticket = numero_ticket;
            this.id_produit = id_produit;
            this.num_lot = num_lot;
            this.vente = vente;
            this.prix_unité = prix_unité;
            this.prix_final = prix_final;
            this.quantité = quantité;
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

        public Date getVente() {
            return vente;
        }

        public void setVente(Date vente) {
            this.vente = vente;
        }

        public float getPrix_unité() {
            return prix_unité;
        }

        public void setPrix_unité(float prix_unité) {
            this.prix_unité = prix_unité;
        }

        public float getPrix_final() {
            return prix_final;
        }

        public void setPrix_final(float prix_final) {
            this.prix_final = prix_final;
        }

        public int getQuantité() {
            return quantité;
        }

        public void setQuantité(int quantité) {
            this.quantité = quantité;
        }

        @Override
        public void getStruct() {
            LinkedHashMap<String, fieldType> map = new LinkedHashMap<>();
            map.put("numero_ticket", fieldType.INT4);
            map.put("id_produit", fieldType.INT4);
            map.put("num_lot", fieldType.INT4);
            map.put("vente", fieldType.DATE);
            map.put("prix_unité", fieldType.FLOAT8);
            map.put("prix_final", fieldType.FLOAT8);
            map.put("quantité", fieldType.INT4);

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

        public void setValues(String values) {
            this.values = values;
        }

        @Override
        public HashMap<String, fieldType> getMap() {
            return map;
        }

        public void setMap(LinkedHashMap<String, fieldType> map) {
            this.map = map;
        }

        public void setMap(HashMap<String, fieldType> map) {
            this.map = new LinkedHashMap<>(map);
        }


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
        }}





