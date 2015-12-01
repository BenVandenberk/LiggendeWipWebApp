package be.oklw.model;

import be.oklw.util.Datum;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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

    //endregion

    //region CONSTRUCTORS

    public Club(String naam, String locatie, Datum sinds){
        sponsors = new ArrayList<Sponsor>();
        ploegen = new HashSet<Ploeg>();
        contacten = new ArrayList<Contact>();
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

    protected void addPloeg(Ploeg ploeg){
        ploegen.add(ploeg);
    }

    public void addContact(Contact contact){
        contacten.add(contact);
    }

    public void addKampioenschap(Kampioenschap kampioenschap) {
        throw new NotImplementedException();
    }

    //endregion



}
