package be.oklw.data.usertype;

import be.oklw.model.Toernooi;
import be.oklw.model.state.ToernooiStatus;
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
public class ToernooiStatusUserType implements UserType {
    @Override
    public int[] sqlTypes() {
        return new int[] {Types.VARCHAR};
    }

    @Override
    public Class returnedClass() {
        return ToernooiStatus.class;
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
        ToernooiStatus toernooiStatus = null;

        String statusString = resultSet.getString(names[0]);
        if (statusString != null) {
            toernooiStatus = ToernooiStatus.maak(statusString);
        }

        return toernooiStatus;
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SessionImplementor sessionImplementor) throws HibernateException, SQLException {
        if(o==null){
            preparedStatement.setNull(i, Types.VARCHAR);
        }else{
            preparedStatement.setString(i, ((ToernooiStatus) o).toStringSimple());
        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        if (o == null) {
            return null;
        }
        ToernooiStatus status = (ToernooiStatus)o;
        return ToernooiStatus.maak(status.toStringSimple());
    }

    @Override
    public boolean isMutable() {
        return false;
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
