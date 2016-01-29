package be.oklw.usertype;

import be.oklw.util.Datum;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Created by java on 03.12.15.
 */
public class DatumUserType implements UserType {
    @Override
    public int[] sqlTypes() {
        return new int[] {Types.VARCHAR};
    }

    @Override
    public Class returnedClass() {
        return Datum.class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        if (o == null || o1 == null) {
            return false;
        }
        return o.equals(o1);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        if (o!=null)
            return o.hashCode();
        else
            return 0;
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor sessionImplementor, Object o) throws HibernateException, SQLException {
        Datum datum = null;

        String datumString = resultSet.getString(names[0]);
        if (datumString != null) {
            datum = new Datum(datumString);
        }

        return datum;
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SessionImplementor sessionImplementor) throws HibernateException, SQLException {
        if(o==null){
            preparedStatement.setNull(i, Types.VARCHAR);
        }else{
            preparedStatement.setString(i, ((Datum) o).getDatumInMySQLFormaat());
        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        if (o == null) {
            return null;
        }

        Datum datum = (Datum)o;
        return new Datum(datum);
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return (Serializable)deepCopy(o);
    }

    @Override
    public Object assemble(Serializable serializable, Object o) throws HibernateException {
        return deepCopy(serializable);
    }

    @Override
    public Object replace(Object o, Object o1, Object o2) throws HibernateException {
        return deepCopy(o);
    }
}
