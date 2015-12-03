package be.oklw.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Kampioenschap extends Evenement {

    //region PRIVATE MEMBERS

    private String rekeningnummer;
    private String contact;
    private String overnachtingInfo;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Sponsor> sponsors;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "kampioenschap")
    private Set<Toernooi> toernooien;

    //endregion

    //region CONSTRUCTORS

    public Kampioenschap() {
        sponsors = new ArrayList<Sponsor>();
        toernooien = new HashSet<Toernooi>();
    }

    //endregion

    //region GETTERS en SETTERS

    public List<Sponsor> getSponsors() {
        return Collections.unmodifiableList(sponsors);
    }

    public Set<Toernooi> getToernooien() {
        return Collections.unmodifiableSet(toernooien);
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

    public void addToernooi(Toernooi toernooi) {
        toernooien.add(toernooi);
        toernooi.setKampioenschap(this);
    }

    public void removeToernooi(Toernooi toernooi) {
        toernooien.remove(toernooi);
    }

    public void removeToernooi(int id) {
        Optional<Toernooi> toernooi = toernooien.stream().filter(t -> t.getId() == id).findFirst();
        if (toernooi.isPresent()) {
            toernooien.remove(toernooi.get());
        }
    }

    /**
     * Een Kampioenschap is verwijderbaar als al de Toernooien in het Kampioenschap verwijderbaar zijn
     * @return true als dit Kampioenschap verwijderbaar is
     */
    public boolean isVerwijderbaar() {
        boolean verwijderbaar = true;

        Iterator<Toernooi> it = toernooien.iterator();
        while (it.hasNext() && verwijderbaar) {
            verwijderbaar = it.next().isVerwijderbaar();
        }

        return  verwijderbaar;
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
