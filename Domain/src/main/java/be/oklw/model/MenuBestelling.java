package be.oklw.model;

import javax.persistence.*;

@Entity
public class MenuBestelling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Menu menu;

    private int aantal;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public int getAantal() {
        return aantal;
    }

    public void setAantal(int aantal) {
        this.aantal = aantal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuBestelling that = (MenuBestelling) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
