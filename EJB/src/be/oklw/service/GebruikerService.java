package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Account;
import be.oklw.model.Club;
import be.oklw.model.SysteemAccount;
import be.oklw.util.Authentication;
import org.hibernate.Transaction;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Stateless
@Remote(IGebruikerService.class)
@TransactionManagement(value = TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
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

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Account veranderPaswoord(Account account, String oud, String nieuw) throws BusinessException {
        if (!Authentication.isJuistPaswoord(oud, account.getPwHash(), account.getPwSalt())) {
            throw new BusinessException("Onjuist paswoord");
        }

        byte[] nieuweHash = Authentication.hashPw(nieuw, account.getPwSalt());
        account.setPwHash(nieuweHash);
        entityManager.merge(account);
        entityManager.flush();
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

        entityManager.flush();
    }

    @Override
    public SysteemAccount getSysteemAccount(int id) {
        return entityManager.find(SysteemAccount.class, id);
    }

    @Override
    public List<SysteemAccount> getAllSysteemAccount(){return (List<SysteemAccount>) entityManager.createQuery("Select s from SysteemAccount s").getResultList();}

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Account wijzigAdminContactGegevens(SysteemAccount systeemAccount, String newEmail, String newTelefoonNummer) throws BusinessException{
        if(isNotBlank(newEmail)){systeemAccount.setEmail(newEmail);}
        if(isNotBlank(newTelefoonNummer)){systeemAccount.setTelefoonnummer(newTelefoonNummer);}
        entityManager.merge(systeemAccount);
        return systeemAccount;
    }
}
