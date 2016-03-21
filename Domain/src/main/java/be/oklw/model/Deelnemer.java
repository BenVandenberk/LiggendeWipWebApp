package be.oklw.model;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Deelnemer implements Serializable {

    private static final long serialVersionUID = 120194243711261552L;
    private static final String DEFAULT_DEELNEMER_PREFIX = "Ploeglid";

    //region PRIVATE MEMBERS

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String naam;
    private boolean deelnemerIsLid;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    private Lid lid;

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

    public boolean isDeelnemerIsLid() {
        return deelnemerIsLid;
    }

    public void setDeelnemerIsLid(boolean isLid) {
        if (!isLid) {
            this.lid = null;
        } else {
            naam = "";
        }
        this.deelnemerIsLid = isLid;
    }

    public Lid getLid() {
        return lid;
    }

    public void setLid(Lid lid) {
        this.lid = lid;
    }

    //endregion

    //region CONSTRUCTORS

    public Deelnemer() {
    }

    public Deelnemer(String naam) {
        this.naam = naam;
    }

    //endregion

    //region PUBLIC METHODS

    public boolean naamBekend() {
        if (deelnemerIsLid && lid != null) {
            return true;
        }
        if (StringUtils.isBlank(naam)) {
            return false;
        }
        if (naam.startsWith(DEFAULT_DEELNEMER_PREFIX)) {
            return false;
        }
        return true;
    }

    //endregion


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deelnemer deelnemer = (Deelnemer) o;

        if (id != deelnemer.id) return false;
        return !(naam != null ? !naam.equals(deelnemer.naam) : deelnemer.naam != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (naam != null ? naam.hashCode() : 0);
        return result;
    }
}
