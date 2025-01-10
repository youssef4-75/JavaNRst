package org.example.nrp.functionalLayer;

import java.io.Serializable;

public class CarteRestaurant implements Serializable {
    private int code;
    private Etudiant E;
    private int Solde ;
    private boolean Active;
    private static int __count=0;

    public CarteRestaurant( Etudiant etudiant, int Solde, boolean Active) {
        this.E=etudiant;
        this.Solde= Solde;
        this.Active=Active;
        this.code = __count++;
    }

    public CarteRestaurant() {}

    public Etudiant getE() {return E;}

    public void setE(Etudiant e) {E = e;}

    public int getSolde() {return Solde;}

    public void setSolde(int solde) {Solde = solde;}

    public void reduceSolde(int solde) {setSolde(getSolde()-solde);}

    public boolean isActive() {return Active;}

    public void setActive(boolean active) {Active = active;}

    public int getCode() {return code;}

    public void setCode(int code) {this.code = code;}

    public static int get__count() {return __count;}

    public static void set__count(int __count) {CarteRestaurant.__count = __count;}

    public boolean pointer(TypeRepas tr) throws RuntimeException{
        int price = 0;
        switch (tr){
            case TypeRepas.petitDejeuner:
                price = 1;
                break;
            case TypeRepas.dejeuner:
            case TypeRepas.diner:
                price = 2;
                break;
            default:
                throw new RuntimeException("typeRepas unknown");
        }
        if(getSolde()<price){
            return false;
        }

        reduceSolde(price);
        return true;

    }

    public boolean imThisOne(int cardId){
        return getCode() == cardId;
    }

    public boolean imThisOne(String userName){
        return getE().getNom().equals(userName);
    }

    @Override
    public boolean equals(Object obj) {
        if(! (obj instanceof CarteRestaurant)){
            return false;
        }
        CarteRestaurant carte = (CarteRestaurant) obj;
        return carte.isActive() && this.isActive() && carte.code == this.code;
    }

    @Override
    public String toString(){
        return "CarteRest(" + getCode() + ", solde = " + getSolde() + ")";
    }


}