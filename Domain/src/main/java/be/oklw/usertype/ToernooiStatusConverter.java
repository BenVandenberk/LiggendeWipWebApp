package be.oklw.usertype;

import be.oklw.model.state.ToernooiStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ToernooiStatusConverter implements AttributeConverter<ToernooiStatus, String>{
    @Override
    public String convertToDatabaseColumn(ToernooiStatus attribute) {
        return attribute.toStringSimple();
    }

    @Override
    public ToernooiStatus convertToEntityAttribute(String dbData) {
        return ToernooiStatus.maak(dbData);
    }
}
