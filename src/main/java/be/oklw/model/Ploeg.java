package be.oklw.model;

import be.oklw.util.Datum;

import java.util.ArrayList;
import java.util.Collections;

public class Ploeg {

    //region PRIVATE MEMBERS

    private int id;
    private String naam;
    private int aantalLeden;
    private Datum inschrijfDatum;
    private int aantalMaaltijden;
    private boolean betaald;

    private ArrayList<Deelnemer> deelnemers;

    //endregion

    //region CONSTRUCTORS

    private Ploeg(String naam, int aantalLeden){
        this.naam = naam;
        this.aantalLeden = aantalLeden;
    }

    //endregion

    //region GETTERS & SETTERS

    public Iterable<Deelnemer> getDeelnemers() {
        return Collections.unmodifiableList(deelnemers);
    }

    public int getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public int getAantalLeden() {
        return aantalLeden;
    }

    public void setAantalLeden(int aantalLeden) {
        this.aantalLeden = aantalLeden;
    }

    public Datum getInschrijfDatum() {
        return inschrijfDatum;
    }

    public void setInschrijfDatum(Datum inschrijfDatum) {
        this.inschrijfDatum = inschrijfDatum;
    }

    public int getAantalMaaltijden() {
        return aantalMaaltijden;
    }

    public void setAantalMaaltijden(int aantalMaaltijden) {
        this.aantalMaaltijden = aantalMaaltijden;
    }

    public boolean isBetaald() {
        return betaald;
    }

    public void setBetaald(boolean betaald) {
        this.betaald = betaald;
    }


    //endregion

    //region PUBLIC METHODS

    public static Ploeg koppelClubAanToernooi(Club club, Toernooi toernooi, String naam, int aantalLeden)
            throws IllegalArgumentException {
        if (club == null || toernooi == null) {
            throw new IllegalArgumentException(
                    "De Club en het Toernooi moeten verwijzen naar een bestaand object");
        }

        Ploeg ploeg = new Ploeg(naam, aantalLeden);
        club.addPloeg(ploeg);
        toernooi.addPloeg(ploeg);
        return ploeg;
    }

    //endregion
}
