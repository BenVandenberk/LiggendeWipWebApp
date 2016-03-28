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

    @OneToOne(fetch = FetchType.EAGER)
    private Account account;

    public Lid() {

    }

    /**
     * Copy constructor die een nieuw Lid maakt met ENKEL DEZELFDE VOORNAAM, ACHTERNAAM EN EMAIL
     * @param lid het te kopiëren Lid
     */
    public Lid(Lid lid) {
        this.voornaam = lid.getVoornaam();
        this.achternaam = lid.getAchternaam();
        this.email = lid.getEmail();
    }

    public int getId() {
        return id;
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
        if (account != null) {
            account.setEmail(email);
        }
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public String getFullName() {
        return String.format("%s %s", voornaam, achternaam);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lid lid = (Lid) o;

        return id == lid.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
