package main.java.data.entities;

import main.java.data.sql.FieldType;

import java.util.HashMap;

//LES CLASSES IMPLEMENTANT CETTE INTERFACE DOIVENT DISPOSEES DES ATTRIBUTS SUPPLEMENTAIRES : private String values ; private HashMap<String, fieldType> map;

public interface IData {
    //REMPLIE DANS LA CLASSE UNE HashMap<String, fieldType> LE NOM DES CHAMPS ET LE TYPE DE VARIABLE ET CREE LA CHAINE values ...
    public void getStruct();

    //RETOURNE UNE CHAINE DE CARACTERE PRE-REMPLIE PERMETTANT DE COMPOSER LA REQUETE INSERT ...
    public String getValues();

    public String toString();

    //GETTER DE LA MAP CREE AVEC LA METHODE getStruct ...
    public HashMap<String, FieldType> getMap();

    //METHODE PERMETTANT DE VERIFIER QUE LA TABLE ET L'INSTANCE PARTAGE LES MEMES ATTRIBUTS ET MEMES TYPES
    //PREND EN PARAMETRE LA MAP ATTRIBUT/TYPE DE LA TABLE ...
    public boolean check(HashMap<String, FieldType> tableStruct);
}