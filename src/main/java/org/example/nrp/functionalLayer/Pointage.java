package org.example.nrp.functionalLayer;

import java.io.Serializable;
import java.util.Calendar;

public class Pointage implements Serializable {
    CarteRestaurant cartePointante;
    Calendar dateDePointage;

    public Pointage(CarteRestaurant cartePointante) {
        this.cartePointante = cartePointante;
        this.dateDePointage = Calendar.getInstance();
    }

    public Pointage() {}

    @Override
    public String toString() {
        return "Pointage<" + cartePointante + "<=>" + dateDePointage + ">";
    }
}
