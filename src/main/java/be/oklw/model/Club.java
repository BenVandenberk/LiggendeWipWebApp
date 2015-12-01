package be.oklw.model;

import be.oklw.util.Datum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by java on 28.11.15.
 */
public class Club {

    //region PRIVATE MEMBERS

    private int id;
    private String naam;
    private String locatie;
    private Datum sinds;
    private String adres;
    private String link;

    private Account account;
    private ArrayList<Sponsor> sponsors;
    private HashSet<Ploeg> ploegen;
    private ArrayList<Contact> contacten;
    private HashSet<Kampioenschap> kampioenschappen;

    //endregion

    //region CONSTRUCTORS

    /**
     * Bij het aanmaken van een Club object worden automatisch
     * lege lijsten sponsors, kampioenschappen en contacten,
     * en een club-account aangemaakt
     */

    public Club(String naam, String locatie, Datum sinds){
        sponsors = new ArrayList<Sponsor>();
        contacten = new ArrayList<Contact>();
        kampioenschappen = new HashSet<Kampioenschap>();
        account = new Account(this);

        this.naam = naam;
        this.locatie = locatie;
        this.sinds = sinds;
    }

    //endregion

    //region GETTERS & SETTERS

    public Iterable<Sponsor> getSponsors() {
        return Collections.unmodifiableList(sponsors);
    }

    public Iterable<Ploeg> getPloegen() {
        return Collections.unmodifiableSet(ploegen);
    }

    public Iterable<Contact> getContacten() {
        return Collections.unmodifiableList(contacten);
    }

    public Iterable<Kampioenschap> getKampioenschappen() {
        return Collections.unmodifiableSet(kampioenschappen);
    }

    public Account getAccount() {
        return account;
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

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public Datum getSinds() {
        return sinds;
    }

    public void setSinds(Datum sinds) {
        this.sinds = sinds;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    //endregion

    //region PUBLIC METHODS

    public void addSponsor(Sponsor sponsor) {
        sponsors.add(sponsor);
    }

    public void removeSponsor(Sponsor sponsor){
        sponsors.remove(sponsor);
    }

    public void removeSponsor(int id){
        sponsors.remove(id);
    }

    public void addPloeg(Ploeg ploeg){
        ploegen.add(ploeg);
    }

    public void removePloeg(Ploeg ploeg){
        ploegen.remove(ploeg);
    }

    public void addContact(Contact contact){
        contacten.add(contact);
    }

    public void removeContact(Contact contact){
        contacten.remove(contact);
    }

    public void removeContact(int id){
        contacten.remove(id);
    }

    /**
     * Deze method maakt een nieuw Kampioenschap object aan
     * en voegt deze toe aan de kampioenschappen lijst van de club
     */

    public void maakKampioenschap(String naam, Datum start, Datum eind){

        Kampioenschap kampioenschap = new Kampioenschap();
        kampioenschap.setNaam(naam);
        kampioenschap.setBeginDatum(start);
        kampioenschap.setEindDatum(eind);
        kampioenschap.setClub(this);

        kampioenschappen.add(kampioenschap);
    }

    public void removeKampioenschap(Kampioenschap kampioenschap){
        kampioenschappen.remove(kampioenschap);
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

        result += String.format("Club '%s', ID=%d%n", naam, id);
        result += String.format("uit %s%n", locatie);
        result += String.format("Geregistreerd %s", sinds);

        return result;
    }

    //endregion

}
