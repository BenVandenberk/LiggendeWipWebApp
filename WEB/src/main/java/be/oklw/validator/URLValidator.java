package be.oklw.validator;

import org.apache.commons.lang3.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("URLValidator")
public class URLValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }

        String url = value.toString();

        if (StringUtils.isBlank(url)) {
            return;
        }

        String[] urlParts = url.split("\\.");
        boolean valid = true;

        if (urlParts.length < 3) {
            valid = false;
        }

        if (valid && !urlParts[0].toLowerCase().equals("www")) {
            valid = false;
        }

        if (!valid) {
            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Ongeldige url",
                            "De url moet van de vorm 'www.iets.eenderwat' zijn"
                    )
            );
        }
    }
}
