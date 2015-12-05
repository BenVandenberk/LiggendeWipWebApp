package be.oklw.model;

import be.oklw.util.Datum;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity
public class Club {

    //region PRIVATE MEMBERS

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Type(type= "be.oklw.data.usertype.DatumUserType")
    private Datum sinds;

    private String adres;
    private String link;
    private String naam;
    private String locatie;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Account account;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "club")
    private List<Sponsor> sponsors;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "club")
    private Set<Ploeg> ploegen;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Contact> contacten;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "club")
    private Set<Evenement> evenementen;

    //endregion

    //region CONSTRUCTORS

    public Club() {
        sponsors = new ArrayList<Sponsor>();
        contacten = new ArrayList<Contact>();
        evenementen = new HashSet<Evenement>();
        this.sinds = new Datum();
    }

    /**
     * Bij het aanmaken van een Club object worden automatisch
     * lege lijsten sponsors, kampioenschappen en contacten,
     * en een club-account aangemaakt
     */

    public Club(String naam, String locatie){
        this();
        account = new Account(this);

        this.naam = naam;
        this.locatie = locatie;
    }

    //endregion

    //region GETTERS & SETTERS

    public List<Sponsor> getSponsors() {
        return Collections.unmodifiableList(sponsors);
    }

    public Set<Ploeg> getPloegen() {
        return Collections.unmodifiableSet(ploegen);
    }

    public List<Contact> getContacten() {
        return Collections.unmodifiableList(contacten);
    }

    public Set<Evenement> getEvenementen() {
        return Collections.unmodifiableSet(evenementen);
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
        for (Sponsor s : sponsors){
            if (s.getId()==id){
                sponsors.remove(s);
            }
        }
    }

    protected void addPloeg(Ploeg ploeg){
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
        for (Contact c : contacten){
            if (c.getId()==id){
                contacten.remove(c);
            }
        }
    }

    /**
     * Deze method maakt een nieuw Kampioenschap object aan
     * en voegt deze toe aan de kampioenschappen lijst van de club
     */

    public Kampioenschap maakKampioenschap(String naam, Datum start, Datum eind){

        Kampioenschap kampioenschap = new Kampioenschap();
        kampioenschap.setNaam(naam);
        kampioenschap.setBeginDatum(start);
        kampioenschap.setEindDatum(eind);
        kampioenschap.setClub(this);

        evenementen.add(kampioenschap);
        return kampioenschap;
    }

    public void removeKampioenschap(Kampioenschap kampioenschap){
        evenementen.remove(kampioenschap);
    }

    //endregion

    //region OBJECT METHODS

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Club club = (Club) o;

        if (id != club.id) return false;
        return !(naam != null ? !naam.equals(club.naam) : club.naam != null);

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

        result += String.format("Club '%s', ID=%d%n", naam, id);
        result += String.format("uit %s%n", locatie);
        result += String.format("Geregistreerd %s", sinds);

        return result;
    }

    //endregion

}
