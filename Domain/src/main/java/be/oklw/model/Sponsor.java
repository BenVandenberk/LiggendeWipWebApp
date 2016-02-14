package be.oklw.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Sponsor implements Serializable {

    private static final long serialVersionUID = -5733677035312717060L;
    private static final String PAD = "upload/clubsponsors/";

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

    @Lob
    @Column(length = 1000)
    private String logoUrlOnline;

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

    protected void setClub(Club club) {
        this.club = club;
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

    public String getLogoUrlOnline() {
        return logoUrlOnline;
    }

    public void setLogoUrlOnline(String logoUrlOnline) {
        this.logoUrlOnline = logoUrlOnline;
    }

    public String getFullPath() {
        if (isLogoOnline()) {
            return logoUrlOnline;
        } else {
            return PAD + logoUrl;
        }
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
