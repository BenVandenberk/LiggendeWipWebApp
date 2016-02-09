package be.oklw.usertype;

import be.oklw.util.Datum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DatumConverter implements AttributeConverter<Datum, String> {
    @Override
    public String convertToDatabaseColumn(Datum attribute) {
        if (attribute != null) {
            return attribute.getDatumInMySQLFormaat();
        }
        return null;
    }

    @Override
    public Datum convertToEntityAttribute(String dbData) {
        if(dbData != null) {
            return new Datum(dbData);
        }
        return new Datum();
    }
}
