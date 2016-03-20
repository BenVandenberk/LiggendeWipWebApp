package be.oklw.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Lid implements Serializable {

    private static final long serialVersionUID = 8221573288199674018L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String voornaam;
    private String achternaam;
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    private Club club;

    public Lid() {

    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}
