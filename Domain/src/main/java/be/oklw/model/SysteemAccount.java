package be.oklw.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
public class SysteemAccount extends Account {

    private static final long serialVersionUID = -6589071286361659827L;

    //region PRIVATE MEMBERS

    private String naam;
    private String email;
    private String telefoonnummer;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "Account_id")
    private List<SiteSponsor> siteSponsors;

    //endregion

    //region GETTERS & SETTERS

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefoonnummer() {
        return telefoonnummer;
    }

    public void setTelefoonnummer(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public List<SiteSponsor> getSiteSponsors() {
        return Collections.unmodifiableList(siteSponsors);
    }

    public void setSiteSponsors(List<SiteSponsor> siteSponsors) {
        this.siteSponsors = siteSponsors;
    }

    //endregion

    //region CONSTRUCTORS

    protected SysteemAccount() {
        super();
        setPermissieNiveau(PermissieNiveau.SYSTEEM);
    }

    public SysteemAccount(String naam) {
        this();
        this.naam = naam;
    }

    //endregion

    //region PUBLIC METHODS

    public void addSiteSponsor(SiteSponsor siteSponsor) {
        siteSponsors.add(siteSponsor);
    }

    public void removeSiteSponsor(int siteSponsorId) {
        for (int i = siteSponsors.size() - 1; i >= 0; i--) {
            if (siteSponsors.get(i).getId() == siteSponsorId) {
                siteSponsors.remove(i);
            }
        }
    }

    //endregion

}
