package be.oklw.model;

import be.oklw.util.Datum;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Nieuws implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nieuws;
    private Datum datum;

    @ManyToOne(cascade = CascadeType.ALL)
    private Account account;

    public Nieuws(){}

    public Nieuws(String nieuws, Datum datum, Account account){
        this.nieuws = nieuws;
        this.datum = datum;
        this.account = account;
    }

    public String getNieuws() {
        return nieuws;
    }

    public void setNieuws(String nieuws) {
        this.nieuws = nieuws;
    }

    public Datum getDatum() {
        return datum;
    }

    public void setDatum(Datum datum) {
        this.datum = datum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
