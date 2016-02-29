package be.oklw.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "NewLineConverter")
public class FacesNewLineConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value.replaceAll("<br />", "\r\n");
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString().replaceAll("(\r\n|\n)", "<br />");
    }
}
