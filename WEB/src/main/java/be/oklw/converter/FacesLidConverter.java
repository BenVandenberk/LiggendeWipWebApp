package be.oklw.converter;

import be.oklw.model.Lid;
import be.oklw.service.IClubService;
import be.oklw.service.ILedenService;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "LidConverter")
public class FacesLidConverter implements Converter {

    @EJB
    ILedenService ledenService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                int id = Integer.parseInt(value);
                return ledenService.getLid(id);
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fout", "Geen geldig Lid."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && !(value instanceof String)) {
            return String.valueOf(((Lid) value).getId());
        } else {
            return null;
        }
    }

}
