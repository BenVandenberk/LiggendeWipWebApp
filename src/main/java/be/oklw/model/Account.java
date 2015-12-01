package be.oklw.model;

public class Account {
    //region PRIVATE MEMBERS

    private int id;
    private String userName;
    private PermissieNiveau permissieNiveau;

    private Club club;

    //endregion

    //region GETTERS & SETTERS

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Club getClub() {
        return club;
    }


    public PermissieNiveau getPermissieNiveau() {
        return permissieNiveau;
    }

    public void setPermissieNiveau(PermissieNiveau permissieNiveau) {
        this.permissieNiveau = permissieNiveau;
    }

    //endregion

    //region CONSTRUCTORS

    /**
     * Deze constructor wordt gebruikt om een club-account aan te maken
     * en wordt automatisch gegenereerd bij het aanmaken van de club
     */

    public Account(Club club){
        this.club = club;
        this.userName = club.getNaam();
        this.permissieNiveau = PermissieNiveau.CLUB;
    }

    /**
     * Deze constructor wordt gebruikt om een lid-account aan te maken
     * en wordt NIET automatisch gegenereerd bij het aanmaken van de club
     */

    public Account(Club club, String lid){
        this.club = club;
        this.userName = lid;
        this.permissieNiveau = PermissieNiveau.LID;
    }

    //endregion
}
