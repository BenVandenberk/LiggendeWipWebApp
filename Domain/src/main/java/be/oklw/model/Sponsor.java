package be.oklw.model;


import javax.persistence.*;

@Entity
public class Sponsor {

    //region PRIVATE MEMBERS

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String naam;
    private String linksTo;
    private int logoHoogte;
    private int logoBreedte;
    private boolean logoOnline;
    private String logoUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    private Club club;

    //endregion

    //region CONSTRUCTORS

    public Sponsor() {

    }

    protected Sponsor(Club club) {
        club.addSponsor(this);
        this.club = club;
    }

    //endregion

    //region GETTERS en SETTERS


    public Club getClub() {
        return club;
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

    public String getLinksTo() {
        return linksTo;
    }

    public void setLinksTo(String linksTo) {
        this.linksTo = linksTo;
    }

    public int getLogoHoogte() {
        return logoHoogte;
    }

    public void setLogoHoogte(int logoHoogte) {
        this.logoHoogte = logoHoogte;
    }

    public int getLogoBreedte() {
        return logoBreedte;
    }

    public void setLogoBreedte(int logoBreedte) {
        this.logoBreedte = logoBreedte;
    }

    public boolean isLogoOnline() {
        return logoOnline;
    }

    public void setLogoOnline(boolean logoOnline) {
        this.logoOnline = logoOnline;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    //endregion

    //region PUBLIC METHODS


    //endregion

    //region OBJECT METHODS

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sponsor sponsor = (Sponsor) o;

        if (id != sponsor.id) return false;
        return !(naam != null ? !naam.equals(sponsor.naam) : sponsor.naam != null);

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

        result += String.format("Sponsor '%s', ID=%d%n", naam, id);
        result += String.format("Linkt naar: %s%n", linksTo);
        result += String.format("Logo %s", logoOnline ? "staat online" : "bevindt zich offline");

        return result;
    }


    //endregion
}
