package main.java.util;

/**
 * Classe qui sert a stocker les informations d'une colonne pour créer un tableau
 */
public class Colonne {
    private String nom;
    private String titre;
    private int largeur;

    public Colonne(String nom, String titre, int taille) {
        this.nom = nom;
        this.titre = titre;
        this.largeur = taille;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getLargeur() {
        return largeur;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }
}
