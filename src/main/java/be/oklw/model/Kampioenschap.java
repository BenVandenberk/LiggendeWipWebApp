package be.oklw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class Kampioenschap extends Evenement {

    //region PRIVATE MEMBERS

    private String rekeningnummer;
    private String contact;
    private String overnachtingInfo;

    private ArrayList<Sponsor> sponsors;
    private ArrayList<Toernooi> toernooien;

    //endregion

    //region CONSTRUCTORS

    public Kampioenschap(Club club) {
        super(club);
        sponsors = new ArrayList<Sponsor>();
        toernooien = new ArrayList<Toernooi>();
    }

    //endregion

    //region GETTERS en SETTERS

    public Iterable<Sponsor> getSponsors() {
        return Collections.unmodifiableList(sponsors);
    }

    public Iterable<Toernooi> getToernooien() {
        return Collections.unmodifiableList(toernooien);
    }

    public String getRekeningnummer() {
        return rekeningnummer;
    }

    public void setRekeningnummer(String rekeningnummer) {
        this.rekeningnummer = rekeningnummer;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getOvernachtingInfo() {
        return overnachtingInfo;
    }

    public void setOvernachtingInfo(String overnachtingInfo) {
        this.overnachtingInfo = overnachtingInfo;
    }

    //endregion

    //region PUBLIC STATIC METHODS

    public static void addToernooiAanKampioenschap() {

    }

    //endregion

    //region PUBLIC METHODS

    /**
     * Een Sponsor is gelinkt aan een club. Om een Sponsor te maken moet je de constructor Sponsor(club : Club) gebruiken. Deze method voegt een sponsor
     * toe aan een Kampioenschap enkel en alleen als de Sponsor gelinkt is aan de Club waaraan ook dit Kampioenschap gelinkt is.
     * @param sponsor de toe te voegen Sponsor
     * @throws IllegalArgumentException als de Sponsor niet gelinkt is aan de Club waaraan dit Kampioenschap gelinkt is.
     */
    public void addSponsor(Sponsor sponsor) throws IllegalArgumentException {
        if (!sponsor.getClub().equals(club)) {
            throw new IllegalArgumentException("De Sponsor moet gelinkt zijn aan de Club waar ook het Kampioenschap aan gelinkt is.");
        }
        sponsors.add(sponsor);
    }

    public void removeSponsor(Sponsor sponsor) {
        sponsors.remove(sponsor);
    }

    public void removeSponsor(int id) {
        Optional<Sponsor> sponsor = sponsors.stream().filter(s -> s.getId() == id).findFirst();
        if (sponsor.isPresent()) {
            sponsors.remove(sponsor.get());
        }
    }

    public Toernooi nieuwToernooi() {
        Toernooi toernooi = new Toernooi(this);
        toernooien.add(toernooi);
        return toernooi;
    }

    public void removeToernooi(Toernooi toernooi) {

    }

    public void removeToernooi(int id) {

    }

    public boolean isVerwijderbaar() {
        return false;
    }

    //endregion

    //region OBJECT METHODS

    @Override
    public String toString() {
        String result = super.toString();

        result += String.format("%nEvenement is een Kampioenschap");

        return result;
    }

    //endregion

}
