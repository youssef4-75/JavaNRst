package org.example.nrp.functionalLayer;

import org.example.nrp.dataLayer.BddComm;
import org.example.nrp.dataLayer.FileComm;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Restaurant implements Serializable {
    TypeRepas tpRepas;
    private List<Pointage> Aseds;
    private final List<CarteRestaurant> Cloud = BddComm.getCRest();
    public int sessionId;

    public List<Pointage> AsedsUP() {
        return Aseds;
    }

    private void init(boolean increment){
        sessionId = FileComm.getLastSessionId(increment);
        System.out.println("[Restaurant.java::] SessionId: " + sessionId);
        switch (sessionId %3){
            case 0:
                tpRepas = TypeRepas.petitDejeuner;
                break;
            case 1:
                tpRepas = TypeRepas.dejeuner;
                break;
            case 2:
                tpRepas = TypeRepas.diner;
        }
        if(increment){
            Aseds = new ArrayList<Pointage>();
        } else {
            Aseds = FileComm.getPointageList(sessionId);
        }
    }

    public Restaurant(){
        init(false);
    }

    public Restaurant(boolean nextSession) {
        // if true: create a new session
        // else: load the latest session from the file and use it
        init(nextSession);
    }

    public String submit(Object o){
        if (!(o instanceof Integer || o instanceof String)){
            return "unknown data type encountered, check the data you entered";
        }
        CarteRestaurant cr = null;
        boolean exist = false;

        for(CarteRestaurant c: Cloud){
            boolean yp;
            try{
                yp = c.imThisOne((Integer) o);
            } catch(ClassCastException e) {
                yp = c.imThisOne((String) o);
            }
            if(yp){
                exist = true;
                cr = c;
                break;
            }
        }

        if(!exist){
            return "this card id or username doesn't exist";
        }

        if(!cr.isActive()){
            return "this card is not active anymore";
        }

        for(Pointage p:Aseds){
            if(cr.equals(p.cartePointante)){
                return "this card has already been submitted";
            }
        }

        if(!cr.pointer(tpRepas)){
            return "User of this card doesnt have enough balance";
        };
        Pointage p = new Pointage(cr);
        Aseds.add(p);
        Etudiant e = cr.getE();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        String pointingDate = formater.format(p.dateDePointage.getTime());

        return "              Ticket:\nNom et Prenom:"+e.getNom()+" "+e.getPrenom()+
                    "\nStatus:"+e.getFiliere()+" "+e.getNiveau()+"\nType de repas: " +
                tpRepas+"\nDate: "+pointingDate+"\nSolde restant: "+cr.getSolde();
    }

    @Override
    public String toString() {
        String res = "NewRest(Pointage{\n";

        for (Pointage p : Aseds) {
            res += p.toString() + ",\n";
        }

        res += "}, \nCarteRestaut{\n";

        for(CarteRestaurant cr:Cloud){
            res += cr.toString() + ",\n";
        }

        res += "})";

        return res;
    }
}
