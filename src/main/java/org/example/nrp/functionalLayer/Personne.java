package org.example.nrp.functionalLayer;

import java.io.Serializable;

public class Personne implements Serializable {
    /* attributs : nom,prenom
     * constructor
     * Getters & Setters
     *
     */
    private String nom;
    private String prenom;
    public Personne() {

    }
    public Personne(String nom , String prenom) {
        this.nom =nom;
        this.prenom= prenom;

    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

}
