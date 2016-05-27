package be.oklw.layout;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "DiagonalDownUp")
public class DiagonalDownUpGradient extends Gradient {

    public DiagonalDownUpGradient() {
    }

    public DiagonalDownUpGradient(String startKleur, String eindKleur, int start, int stop) {
        super(startKleur, eindKleur, start, stop);
    }

    @Override
    public String firefox() {
        return backgroundProperty(String.format(
                "-moz-linear-gradient(45deg,  %s %d%%, %s %d%%)",
                getStartKleur(),
                getStart(),
                getEindKleur(),
                getStop()
        ));
    }

    @Override
    public String chromeAndSafari() {
        return backgroundProperty(String.format(
                "-webkit-linear-gradient(45deg,  %s %d%%,%s %d%%)",
                getStartKleur(),
                getStart(),
                getEindKleur(),
                getStop()
        ));
    }

    @Override
    public String modernBrowsers() {
        return backgroundProperty(String.format(
                "linear-gradient(45deg,  %s %d%%,%s %d%%)",
                getStartKleur(),
                getStart(),
                getEindKleur(),
                getStop()
        ));
    }

    @Override
    public String type() {
        return "DiagonalDownUp";
    }
}
