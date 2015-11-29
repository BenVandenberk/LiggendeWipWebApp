package be.oklw.model;

import be.oklw.util.Datum;

public class Evenement {

    //region PRIVATE MEMBERS

    protected int id;
    protected String naam;
    protected String locatie;
    protected Datum beginDatum;
    protected Datum eindDatum;
    protected String omschrijving;

    protected Club club;

    //endregion

    //region CONSTRUCTORS

    public Evenement(Club club) {
        this.club = club;
    }

    //endregion

    //region GETTERS en SETTERS

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

    public Datum getBeginDatum() {
        return beginDatum;
    }

    public void setBeginDatum(Datum beginDatum) {
        this.beginDatum = beginDatum;
    }

    public Datum getEindDatum() {
        return eindDatum;
    }

    public void setEindDatum(Datum eindDatum) {
        this.eindDatum = eindDatum;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Club getClub() {
        return club;
    }

    //endregion

    //region OBJECT METHODS

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Evenement)) return false;

        Evenement evenement = (Evenement) o;

        if (id != evenement.id) return false;
        return !(naam != null ? !naam.equals(evenement.naam) : evenement.naam != null);

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

        result += String.format("Evenement '%s', ID=%d%n", naam, id);
        result += String.format("Van %s tot %s%n", beginDatum.getDatumInEuropeesFormaat(), eindDatum.getDatumInEuropeesFormaat());
        result += String.format("Te %s%n", locatie);
        result += String.format("Georganiseerd door: %s", club);

        return result;
    }

    //endregion

}
