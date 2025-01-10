package org.example.nrp.functionalLayer;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

public class Etudiant extends Personne implements Serializable {
    private int code;
    private static int __count=0;
    private Niveau niveau;
    private Filiere filiere;
    private CarteRestaurant carte;

    public Etudiant (String nom, String prenom, Niveau niveau ,Filiere filiere, CarteRestaurant carte) {
        super(nom,prenom);
        this.filiere= filiere;
        this.niveau= niveau;
        this.carte= carte;
        this.code=__count++;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Etudiant(){}

    public int getCode() {
        return code;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public void setNiveau(String niveau){
        switch (niveau){
            case "INE1":
                setNiveau(Niveau.INE1);
                break;
            case "INE2":
                setNiveau(Niveau.INE2);
                break;
            case "INE3":
                setNiveau(Niveau.INE3);
        }
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

    public void setFiliere(String filiere){
        switch (filiere){
            case "Aseds":
                setFiliere(Filiere.ASEDS);
                break;
            case "ICCN":
                setFiliere(Filiere.ICCN);
                break;
            case "DATA":
                setFiliere(Filiere.DATA);
                break;
            case "Cloud":
                setFiliere(Filiere.CLOUD);
                break;
            case "SMART":
                setFiliere(Filiere.SMART);
                break;
            case "SESNUM":
                setFiliere(Filiere.SESNUM);
                break;
            case "AMOA":
                setFiliere(Filiere.AMOA);
                break;
        }
    }
    public CarteRestaurant getCarte() {
        return carte;
    }

    public void setCarte(CarteRestaurant carte) {
        this.carte = carte;
    }

}
