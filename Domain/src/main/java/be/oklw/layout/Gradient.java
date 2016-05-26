package be.oklw.layout;

import javax.persistence.*;

@Entity
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="Gradient_type")
public abstract class Gradient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String startKleur;
    private String eindKleur;

    public Gradient() {

    }

    public Gradient(String startKleur, String eindKleur) {
        this.startKleur = startKleur;
        this.eindKleur = eindKleur;
    }

    public String getStartKleur() {
        return startKleur;
    }

    public void setStartKleur(String startKleur) {
        this.startKleur = startKleur;
    }

    public String getEindKleur() {
        return eindKleur;
    }

    public void setEindKleur(String eindKleur) {
        this.eindKleur = eindKleur;
    }

    public String css() {
        return String.format(
                "%s%s%s%s%s",
                oldBrowsers(),
                firefox(),
                chromeAndSafari(),
                modernBrowsers(),
                internetExplorerFallback()
        );
    }

    public String internetExplorerFallback() {
        return String.format(
                "filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='%s', endColorstr='%s',GradientType=1 );",
                startKleur,
                eindKleur
        );
    }

    public String oldBrowsers() {
        return backgroundProperty(getStartKleur());
    }

    public String backgroundProperty(String value) {
        return String.format(
                "%s: %s;",
                "background",
                value
        );
    }

    public abstract String firefox();
    public abstract String chromeAndSafari();
    public abstract String modernBrowsers();
}
