package be.oklw.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.time.LocalTime;
import java.time.temporal.TemporalUnit;

@FacesConverter(value = "TimeConverter")
public class FacesTimeConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return LocalTime.parse(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        LocalTime localTime = (LocalTime)value;
        return String.format("%02d:%02d", localTime.getHour(), localTime.getMinute());
    }
}
