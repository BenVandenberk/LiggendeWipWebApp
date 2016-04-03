package be.oklw.validator;

import org.apache.commons.lang3.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("emailValidator")
public class EmailValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }

        String email = (String) value;

        // Leeg email is ok
        if (StringUtils.isBlank(email)) {
            return;
        }

        // Minimum length 6: 'x@y.zz'
        if (email.length() < 6) {
            throw new ValidatorException(new FacesMessage("Ongeldige email"));
        }

        String[] parts = email.split("@");

        // Juist één '@' teken
        if (parts.length != 2) {
            throw new ValidatorException(new FacesMessage("Ongeldige email"));
        }

        String[] partsAfterAt = parts[1].split("\\.");

        // Na de @ minstens één '.'
        if (partsAfterAt.length < 2) {
            throw new ValidatorException(new FacesMessage("Ongeldige email"));
        }

        // 'x@y.zz' -> x is minstens 1 alfanumerisch teken lang
        if (!StringUtils.isAsciiPrintable(parts[0]) || parts[0].length() < 1) {
            throw new ValidatorException(new FacesMessage("Ongeldige email"));
        }

    }
}
