package be.oklw.layout;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "DiagonalDownUp")
public class DiagonalDownUpGradient extends Gradient {

    public DiagonalDownUpGradient() {
    }

    public DiagonalDownUpGradient(String startKleur, String eindKleur) {
        super(startKleur, eindKleur);
    }

    @Override
    public String firefox() {
        return backgroundProperty(String.format(
                "-moz-linear-gradient(45deg,  %s 51%%, %s 90%%)",
                getStartKleur(),
                getEindKleur()
        ));
    }

    @Override
    public String chromeAndSafari() {
        return backgroundProperty(String.format(
                "-webkit-linear-gradient(45deg,  %s 51%%,%s 90%%)",
                getStartKleur(),
                getEindKleur()
        ));
    }

    @Override
    public String modernBrowsers() {
        return backgroundProperty(String.format(
                "linear-gradient(45deg,  %s 51%%,%s 90%%)",
                getStartKleur(),
                getEindKleur()
        ));
    }
}
