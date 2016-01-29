package be.oklw.usertype;

import be.oklw.util.Datum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DatumConverter implements AttributeConverter<Datum, String> {
    @Override
    public String convertToDatabaseColumn(Datum attribute) {
        return attribute.getDatumInMySQLFormaat();
    }

    @Override
    public Datum convertToEntityAttribute(String dbData) {
        return new Datum(dbData);
    }
}
