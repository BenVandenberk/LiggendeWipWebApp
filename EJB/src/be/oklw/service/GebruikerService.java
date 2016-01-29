package be.oklw.service;

import be.oklw.model.Account;
import be.oklw.model.Club;
import be.oklw.model.SysteemAccount;
import be.oklw.util.Authentication;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@Remote(IGebruikerService.class)
public class GebruikerService implements IGebruikerService {

    @PersistenceContext(unitName = "myunitname")
    EntityManager entityManager;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Account login(String userName, String password) {

        List<Account> accounts =  entityManager.createQuery(
                "select a from Account a where a.userName LIKE :un"
        ).setParameter("un", userName)
         .getResultList();

        if (accounts.size() == 0) {
            throw new IllegalArgumentException("Onbestaande gebruiker");
        }

        Account account = accounts.get(0);
        if (!Authentication.isJuistPaswoord(password, account.getPwHash(), account.getPwSalt())) {
            throw new IllegalArgumentException("Onjuist paswoord");
        }

        return account;
    }

    public void createAdmin() {
        Club club = new Club("club", "Leuven");
        entityManager.persist(club);

        SysteemAccount systeemAccount = new SysteemAccount("admin");
        systeemAccount.setUserName("admin");
        byte[] salt = Authentication.nextSalt();
        byte[] pwHash = Authentication.hashPw("admin", salt);
        systeemAccount.setPwHash(pwHash);
        systeemAccount.setPwSalt(salt);
        entityManager.persist(systeemAccount);
    }
}
