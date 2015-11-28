package be.oklw.model;

import java.util.ArrayList;
import java.util.Collections;

public class Kampioenschap extends Evenement {

    private String rekeningnummer;
    private String contact;
    private String overnachtingInfo;

    private ArrayList<Sponsor> sponsors;
    private ArrayList<Toernooi> toernooien;

    protected Kampioenschap(Club club) {
        super(club);
        sponsors = new ArrayList<Sponsor>();
        toernooien = new ArrayList<Toernooi>();
    }

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

    public void addSponsor(Sponsor sponsor) {
        sponsors.add(sponsor);
    }

    public void removeSponsor(Sponsor sponsor) {

    }

    public void removeSponsor(int id) {

    }

    public void addToernooi(Toernooi toernooi) {
        // CONSISTENT
    }

    public void removeToernooi(Toernooi toernooi) {

    }

    public void removeToernooi(int id) {

    }

    public boolean isVerwijderbaar() {
        return false;
    }

}
