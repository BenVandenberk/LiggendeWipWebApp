package be.oklw.model;

public class Contact {

    //region PRIVATE MEMBERS

    private int id;
    private String naam;
    private String telefoonnummer;
    private String email;
    private boolean isBeheerder;

    //endregion

    //region GETTERS & SETTERS

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public int getId() {
        return id;
    }

    public String getTelefoonnummer() {
        return telefoonnummer;
    }

    public void setTelefoonnummer(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isBeheerder() {
        return isBeheerder;
    }

    public void setIsBeheerder(boolean isBeheerder) {
        this.isBeheerder = isBeheerder;
    }

    //endregion

    //region CONSTRUCTORS

    //endregion

    //region PUBLIC METHODS

    //endregion

}
