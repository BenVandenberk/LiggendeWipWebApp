package be.oklw.validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import be.oklw.util.Datum;
import org.omnifaces.validator.MultiFieldValidator;

@Named
@ApplicationScoped
@FacesValidator("datumValidator")
public class DatumValidator implements MultiFieldValidator, Validator{

    @Override
    public boolean validateValues(FacesContext context, List<UIInput> components, List<Object> values) {
        String start = values.get(0).toString();
        String eind = values.get(1).toString();
        if(start.equals("") || eind.equals("")){
            return false;
        }
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = df.parse(start);
            endDate = df.parse(eind);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return (startDate.before(endDate) || startDate.equals(endDate));
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

    }
}
