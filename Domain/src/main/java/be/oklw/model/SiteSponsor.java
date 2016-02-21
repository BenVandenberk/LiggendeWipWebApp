package be.oklw.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class SiteSponsor implements Serializable {

    private static final long serialVersionUID = -5584883537646670764L;
    private static final String PAD = "upload/sitesponsors/";

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

    public SiteSponsor() {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SiteSponsor that = (SiteSponsor) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
