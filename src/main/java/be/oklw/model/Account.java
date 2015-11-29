package be.oklw.model;

/**
 * Created by java on 29.11.15.
 */
public class Account {
    //region PRIVATE MEMBERS

    private int id;
    private String userName;

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

    //endregion

    //region CONSTRUCTORS

    public Account(Club club){
        this.club = club;
    }

    //endregion
}
