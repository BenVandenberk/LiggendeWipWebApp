package be.oklw.model;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Kampioenschap extends Evenement {

    private static final long serialVersionUID = 4883897423279187027L;

    //region PRIVATE MEMBERS

    private String rekeningnummer;

    @Column(length = 2000)
    private String contact;

    @Column(length = 2000)
    private String overnachtingInfo;

    private double locatieLat;
    private double locatieLng;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @Fetch(FetchMode.SELECT)
    private List<Sponsor> sponsors;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "kampioenschap", orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    private List<Toernooi> toernooien;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "kampioenschap", orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    private List<Foto> fotos;

    //endregion

    //region CONSTRUCTORS

    public Kampioenschap() {
        sponsors = new ArrayList<Sponsor>();
        toernooien = new ArrayList<Toernooi>();
        fotos = new ArrayList<Foto>();
    }

    //endregion

    //region GETTERS en SETTERS

    public List<Sponsor> getSponsors() {
        return Collections.unmodifiableList(sponsors);
    }

    public List<Toernooi> getToernooien() {
        return Collections.unmodifiableList(toernooien);
    }

    public List<Foto> getFotos() {
        return Collections.unmodifiableList(fotos);
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

    public double getLocatieLat() {
        return locatieLat;
    }

    public void setLocatieLat(double locatieLat) {
        this.locatieLat = locatieLat;
    }

    public double getLocatieLng() {
        return locatieLng;
    }

    public void setLocatieLng(double locatieLng) {
        this.locatieLng = locatieLng;
    }

    //endregion

    //region PUBLIC METHODS

    /**
     * Een Sponsor is gelinkt aan een club. Om een Sponsor te maken moet je de constructor Sponsor(club : Club) gebruiken. Deze method voegt een sponsor
     * toe aan een Kampioenschap enkel en alleen als de Sponsor gelinkt is aan de Club waaraan ook dit Kampioenschap gelinkt is.
     *
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

    public void addFoto(Foto foto) {
        fotos.add(foto);
        foto.setKampioenschap(this);
    }

    public void removeFoto(int fotoId) {
        Optional<Foto> foto = fotos.stream().filter(f -> f.getId() == fotoId).findFirst();
        if (foto.isPresent()) {
            fotos.remove(foto.get());
        }
    }

    public void removeFoto(Foto foto) {
        fotos.remove(foto);
    }

    public boolean heeftRekeningnummer() {
        return StringUtils.isNotBlank(rekeningnummer);
    }

    public boolean heeftOvernachtingInfo() {
        return StringUtils.isNotBlank(overnachtingInfo);
    }

    public List<Toernooi> getToernooienInschrijvenMogelijk() {
        return toernooien.stream().filter(t -> t.getStatus().isInschrijvingenOpen()).collect(Collectors.toList());
    }

    /**
     * Een Kampioenschap is verwijderbaar als al de Toernooien in het Kampioenschap verwijderbaar zijn
     *
     * @return true als dit Kampioenschap verwijderbaar is
     */
    public boolean isVerwijderbaar() {
        boolean verwijderbaar = true;

        Iterator<Toernooi> it = toernooien.iterator();
        while (it.hasNext() && verwijderbaar) {
            verwijderbaar = it.next().isVerwijderbaar();
        }

        return verwijderbaar;
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
