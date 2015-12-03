package be.oklw.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class SysteemAccount extends Account {

    //region PRIVATE MEMBERS

    private String naam;
    private String email;
    private String telefoonnummer;

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

    //endregion

}
