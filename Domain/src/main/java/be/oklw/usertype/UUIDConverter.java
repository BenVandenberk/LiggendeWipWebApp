package be.oklw.usertype;


import javax.persistence.AttributeConverter;
import java.util.UUID;

public class UUIDConverter implements AttributeConverter<UUID, String> {

    @Override
    public String convertToDatabaseColumn(UUID attribute) {
        if (attribute != null) {
            return attribute.toString();
        }
        return "";
    }

    @Override
    public UUID convertToEntityAttribute(String dbData) {
        return UUID.fromString(dbData);
    }
}
