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

    /**
     * Gebruik deze method om een ploeg in te schrijven voor een toernooi
     * @param club
     * @param toernooi
     * @param naam
     * @param aantalLeden
     * @return de ingeschreven Ploeg
     * @throws IllegalArgumentException als club of toernooi null zijn
     * @throws IllegalStateException als inschrijven voor het toernooi niet mogelijk is vanwege de toernooistatus
     */
    public static Ploeg koppelClubAanToernooi(Club club, Toernooi toernooi, String naam, int aantalLeden)
            throws IllegalArgumentException, IllegalStateException {
        if (club == null || toernooi == null) {
            throw new IllegalArgumentException(
                    "De Club en het Toernooi moeten verwijzen naar een bestaand object");
        }

        Ploeg ploeg = new Ploeg(naam, aantalLeden);

        if (toernooi.getStatus().isInschrijvenMogelijk()) {
            club.addPloeg(ploeg);
            toernooi.addPloeg(ploeg);
        } else {
            throw new IllegalStateException(String.format("Inschrijven voor dit toernooi is niet mogelijk. Toernooistatus: %s", toernooi.getStatus().toString()));
        }

        return ploeg;
    }

    public void addDeelnemer (Deelnemer deelnemer){
        deelnemers.add(deelnemer);
    }

    public void removeDeelnemer (Deelnemer deelnemer){
        deelnemers.remove(deelnemer);
    }

    public void removeDeelnemer (int id){
        deelnemers.remove(id);
    }

    public boolean isVolzet(){
        if (deelnemers.size()==aantalLeden){
            return true;
        }
        else return false;
    }

    //endregion

    //region OBJECT METHODS

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (naam != null ? naam.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String result = "";

        result += String.format("Ploeg '%s', ID=%d%n", naam, id);
        result += String.format("met deelnemers %n");
        for (Deelnemer d : deelnemers){
        result += String.format("%s%n", d.getNaam());
        }

        return result;
    }

    //endregion

}
