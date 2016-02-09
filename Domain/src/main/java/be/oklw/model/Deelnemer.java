package be.oklw.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Deelnemer implements Serializable {

    private static final long serialVersionUID = 120194243711261552L;

    //region PRIVATE MEMBERS

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String naam;

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

    //endregion

    //region CONSTRUCTORS

    public Deelnemer(){}

    public Deelnemer(String naam){
        this.naam = naam;
    }

    //endregion

    //region PUBLIC METHODS

    //endregion


}
