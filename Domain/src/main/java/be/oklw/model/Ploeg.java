package be.oklw.model;

import be.oklw.usertype.DatumConverter;
import be.oklw.util.Datum;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
public class Ploeg {

    //region PRIVATE MEMBERS

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String naam;
    private int aantalLeden;
    private int aantalMaaltijden;
    private boolean betaald;

    @Convert(converter = DatumConverter.class)
    private Datum inschrijfDatum;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Deelnemer> deelnemers;

    @ManyToOne(fetch = FetchType.EAGER)
    private Club club;

    @ManyToOne(fetch = FetchType.EAGER)
    private Toernooi toernooi;

    //endregion

    //region CONSTRUCTORS

    protected Ploeg() {

    }

    private Ploeg(String naam){
        this.naam = naam;
        inschrijfDatum = new Datum();
    }

    //endregion

    //region GETTERS & SETTERS


    public Toernooi getToernooi() {
        return toernooi;
    }

    public void setToernooi(Toernooi toernooi) {
        this.toernooi = toernooi;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public List<Deelnemer> getDeelnemers() {
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
     * @return de ingeschreven Ploeg
     * @throws IllegalArgumentException als club of toernooi null zijn
     * @throws IllegalStateException als inschrijven voor het toernooi niet mogelijk is vanwege de toernooistatus
     */
    public static Ploeg schrijfPloegInVoorToernooi(Club club, Toernooi toernooi, String naam)
            throws IllegalArgumentException, IllegalStateException {
        if (club == null || toernooi == null) {
            throw new IllegalArgumentException(
                    "De Club en het Toernooi moeten verwijzen naar een bestaand object");
        }

        Ploeg ploeg = new Ploeg(naam);

        if (toernooi.getStatus().isInschrijvenMogelijk()) {

            ploeg.setClub(club);
            ploeg.setToernooi(toernooi);
            ploeg.setAantalLeden(toernooi.getPersonenPerPloeg());

            club.addPloeg(ploeg);
            toernooi.addPloeg(ploeg);

        } else {
            throw new IllegalStateException(String.format("Inschrijven voor dit toernooi is niet mogelijk. Toernooistatus: %s", toernooi.getStatus().toString()));
        }

        return ploeg;
    }

    public void addDeelnemer (Deelnemer deelnemer)
            throws IllegalStateException {
        if (!isVolzet()){
        deelnemers.add(deelnemer);
        }
        else {
            throw new IllegalStateException(String.format("Ploeg '%s' is volzet", naam));
        }
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ploeg ploeg = (Ploeg) o;

        if (id != ploeg.id) return false;
        return !(naam != null ? !naam.equals(ploeg.naam) : ploeg.naam != null);

    }

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
