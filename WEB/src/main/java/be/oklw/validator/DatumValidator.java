package be.oklw.validator;

import be.oklw.util.Datum;
import org.apache.commons.lang3.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("datumValidator")
public class DatumValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }

        //Leave the null handling of startDate to required="true"
        Object startDateValue = uiComponent.getAttributes().get("startdatum");
        if (startDateValue==null) {
            return;
        }

        Datum startDatum = (Datum)startDateValue;
        Datum eindDatum = (Datum)value;
        if (eindDatum.kleinerDan(startDatum)) {
            throw new ValidatorException(new FacesMessage(""));
        }
    }
}
