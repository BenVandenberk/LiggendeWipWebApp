package be.oklw.model;

import be.oklw.usertype.UUIDConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
public class PaswoordResetInfo implements Serializable {

    private static final long serialVersionUID = -3191037682386557065L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int accountId;

    @Convert(converter = UUIDConverter.class)
    private UUID linkUUID;

    public PaswoordResetInfo() {

    }

    public PaswoordResetInfo(int accountId) {
        this.accountId = accountId;
    }

    public static PaswoordResetInfo create(int accountId) {
        PaswoordResetInfo paswoordResetInfo = new PaswoordResetInfo(accountId);
        paswoordResetInfo.linkUUID = UUID.randomUUID();
        return paswoordResetInfo;
    }

    public int getId() {
        return id;
    }

    public int getAccountId() {
        return accountId;
    }

    public UUID getLinkUUID() {
        return linkUUID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaswoordResetInfo that = (PaswoordResetInfo) o;

        if (id != that.id) return false;
        if (accountId != that.accountId) return false;
        return linkUUID.equals(that.linkUUID);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + accountId;
        result = 31 * result + linkUUID.hashCode();
        return result;
    }
}
