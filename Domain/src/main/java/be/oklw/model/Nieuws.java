package be.oklw.model;

import be.oklw.usertype.DatumConverter;
import be.oklw.util.Datum;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Nieuws implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 2000)
    private String nieuws;

    @Convert(converter = DatumConverter.class)
    private Datum datum;

    @Convert(converter = DatumConverter.class)
    private Datum tonenTot;

    @ManyToOne(fetch = FetchType.EAGER)
    private Account account;

    public Nieuws(){}

    public Nieuws(String nieuws, Datum datum, Datum tonenTot, Account account){
        this.nieuws = nieuws;
        this.datum = datum;
        this.account = account;
        this.tonenTot = tonenTot;
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

    public Datum getTonenTot() {
        return tonenTot;
    }

    public void setTonenTot(Datum tonenTot) {
        this.tonenTot = tonenTot;
    }
}
