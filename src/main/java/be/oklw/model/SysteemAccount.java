package be.oklw.model;

public class SysteemAccount extends Account {

    //region PRIVATE MEMBERS

    private String naam;
    private String email;
    private String telefoonnummer;
    private final PermissieNiveau permissieNiveau = PermissieNiveau.SYSTEEM;

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

    public SysteemAccount(Club club, String naam) {
        super(club);
        this.naam = naam;
    }

    //endregion

    //region PUBLIC METHODS

    //endregion

}
