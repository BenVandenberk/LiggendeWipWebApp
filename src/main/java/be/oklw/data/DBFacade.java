package be.oklw.data;

import be.oklw.data.util.HibernateUtil;
import be.oklw.model.Account;
import be.oklw.model.Club;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Created by java on 07.12.15.
 */
public class DBFacade implements IDBFacade {

    private HibernateUtil hibernateUtil;
    private static DBFacade dbFacade;

    private DBFacade() {
        hibernateUtil = HibernateUtil.getInstance();
    }

    public static DBFacade getUniekeInstantie() {
        if (dbFacade == null) {
            dbFacade = new DBFacade();
        }
        return dbFacade;
    }

    @Override
    public Account getAccount(String gebruikersnaam) {
        String hql = String.format("FROM Account A WHERE A.userName='%s'", gebruikersnaam);
        Session session = hibernateUtil.getCurrentSession();
        Query query = session.createQuery(hql);
        Account account = (Account)query.uniqueResult();
        hibernateUtil.endSession();
        return account;
    }

    @Override
    public void saveAccount(Account account) {
        Session session = hibernateUtil.getCurrentSession();
        session.saveOrUpdate(account);
        hibernateUtil.endSession();
    }

    @Override
    public void saveClub(Club club) {
        Session session = hibernateUtil.getCurrentSession();
        session.saveOrUpdate(club);
        hibernateUtil.endSession();
    }
}
