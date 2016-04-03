package be.oklw.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Menu implements Serializable {

    private static final long serialVersionUID = 5929884805890656030L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String naam;

    @Column(length = 2000)
    private String omschrijving;
    private BigDecimal prijs;

    @Transient
    private UUID inMemoryKey;

    public Menu() {
        inMemoryKey = UUID.randomUUID();
    }

    public int getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return omschrijving == null ? "" : omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public BigDecimal getPrijs() {
        return prijs == null ? BigDecimal.ZERO : prijs;
    }

    public void setPrijs(BigDecimal prijs) {
        this.prijs = prijs;
    }

    public UUID getInMemoryKey() {
        return inMemoryKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Menu menu = (Menu) o;

        return id == menu.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
