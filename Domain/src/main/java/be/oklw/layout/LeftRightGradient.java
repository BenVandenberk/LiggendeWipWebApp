package be.oklw.layout;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "LeftRight")
public class LeftRightGradient extends Gradient {

    public LeftRightGradient(String startKleur, String eindKleur) {
        super(startKleur, eindKleur);
    }

    public LeftRightGradient() {

    }

    @Override
    public String firefox() {
        return backgroundProperty(String.format(
                "-moz-linear-gradient(left,  %s 51%%, %s 90%%)",
                getStartKleur(),
                getEindKleur()
        ));
    }

    @Override
    public String chromeAndSafari() {
        return backgroundProperty(String.format(
                "-webkit-linear-gradient(left,  %s 51%%,%s 90%%)",
                getStartKleur(),
                getEindKleur()
        ));
    }

    @Override
    public String modernBrowsers() {
        return backgroundProperty(String.format(
                "linear-gradient(to right,  %s 51%%,%s 90%%)",
                getStartKleur(),
                getEindKleur()
        ));
    }
}
