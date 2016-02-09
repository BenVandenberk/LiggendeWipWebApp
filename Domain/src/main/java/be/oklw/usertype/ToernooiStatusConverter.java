package be.oklw.usertype;

import be.oklw.model.state.Aangemaakt;
import be.oklw.model.state.ToernooiStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ToernooiStatusConverter implements AttributeConverter<ToernooiStatus, String>{
    @Override
    public String convertToDatabaseColumn(ToernooiStatus attribute) {
        if (attribute != null) {
            return attribute.toStringSimple();
        }
        return null;
    }

    @Override
    public ToernooiStatus convertToEntityAttribute(String dbData) {
        if (dbData != null) {
            return ToernooiStatus.maak(dbData);
        }
        return new Aangemaakt();
    }
}
