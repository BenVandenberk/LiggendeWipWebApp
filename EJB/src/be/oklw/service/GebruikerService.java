package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.*;
import be.oklw.util.Authentication;
import org.hibernate.exception.ConstraintViolationException;

import javax.ejb.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Stateless
@TransactionManagement(value = TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class GebruikerService implements IGebruikerService {

    @PersistenceContext
    EntityManager entityManager;

    @EJB
    IMailService mailService;

    @EJB
    IPaswoordTimerService paswoordTimerService;

    @Override
    public Account login(String userName, String password) throws BusinessException {

        try {

            Account account = entityManager.createQuery("select a from Account a where a.userName=:un", Account.class)
                    .setParameter("un", userName)
                    .getSingleResult();

            if (!Authentication.isJuistPaswoord(password, account.getPwHash(), account.getPwSalt())) {
                throw new BusinessException("Onjuist paswoord");
            }

            return account;

        } catch (NoResultException noResEx) {
            throw new BusinessException("Onbestaande gebruiker");
        } catch (NonUniqueResultException nonUniqResEx) {
            throw new BusinessException("Er zijn meerdere Accounts met gebruikersnaam " + userName);
        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
    }

    @Override
    public Account veranderPaswoord(Account account, String oud, String nieuw) throws BusinessException {
        if (!Authentication.isJuistPaswoord(oud, account.getPwHash(), account.getPwSalt())) {
            throw new BusinessException("Onjuist paswoord");
        }

        try {
            byte[] nieuweHash = Authentication.hashPw(nieuw, account.getPwSalt());
            account.setPwHash(nieuweHash);
            entityManager.merge(account);
            return account;
        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
    }

    @Override
    public void createAdmin() {

        SysteemAccount systeemAccount = new SysteemAccount("Webmaster");
        systeemAccount.setUserName("Webmaster");
        byte[] salt = Authentication.nextSalt();
        byte[] pwHash = Authentication.hashPw("adminpw", salt);
        systeemAccount.setPwHash(pwHash);
        systeemAccount.setPwSalt(salt);
        entityManager.persist(systeemAccount);
    }

    @Override
    public SysteemAccount getSysteemAccount(int id) {
        return entityManager.find(SysteemAccount.class, id);
    }

    @Override
    public List<SysteemAccount> getAllSysteemAccount() {
        return entityManager.createQuery("Select s from SysteemAccount s", SysteemAccount.class).getResultList();
    }

    @Override
    public Account wijzigAdminContactGegevens(SysteemAccount systeemAccount, String newEmail, String newTelefoonNummer) throws BusinessException {
        if (isNotBlank(newEmail)) {
            systeemAccount.setEmail(newEmail);
        }
        if (isNotBlank(newTelefoonNummer)) {
            systeemAccount.setTelefoonnummer(newTelefoonNummer);
        }
        entityManager.merge(systeemAccount);
        return systeemAccount;
    }

    @Override
    public Club valideerRegistratieCode(String code) throws BusinessException {
        try {
            return entityManager.createQuery("select c from Club c where c.registratieCode=:regCode", Club.class)
                    .setParameter("regCode", code)
                    .getSingleResult();
        } catch (NoResultException noResEx) {
            return null;
        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
    }

    @Override
    public Account registreer(Lid lid, Club club, String userName, String password) throws BusinessException {
        try {
            lid.setClub(club);
            Account lidAccount = new Account(lid, userName, password);
            entityManager.persist(lidAccount);
            return lidAccount;
        } catch (PersistenceException pEx) {
            if (pEx.getCause() instanceof ConstraintViolationException) { // Bij saven van een niet-unieke username
                throw new BusinessException("Deze gebruikersnaam bestaat al. Kies een andere gebruikersnaam.");
            }
            throw new BusinessException("Er liep iets mis: " + pEx.getMessage());
        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
    }

    @Override
    public void updateLid(Lid lid) throws BusinessException {
        try {
            entityManager.merge(lid);
        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
    }

    @Override
    public void saveAccount(Account account) throws BusinessException {
        try {
            entityManager.merge(account);
        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
    }

    @Override
    public Account getAccount(String userName) throws BusinessException {
        try {
            return entityManager.createQuery("select a from Account a where a.userName=:UN", Account.class)
                    .setParameter("UN", userName)
                    .getSingleResult();
        } catch (NoResultException noResEx) {
            return null;
        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
    }

    @Override
    public void resetPaswoord(Account account) throws BusinessException {
        final int PW_LENGTH = 8;
        final int LOWER_CASE_A = 97;

        try {
            Random random = new Random();
            char[] randomPW = new char[PW_LENGTH];
            int offset;

            for (int i = 0; i < PW_LENGTH; i++) {
                offset = random.nextInt(26);
                randomPW[i] = (char) (LOWER_CASE_A + offset);
            }

            String nieuwPaswoord = new String(randomPW);

            byte[] nieuweHash = Authentication.hashPw(nieuwPaswoord, account.getPwSalt());
            account.setPwHash(nieuweHash);
            entityManager.merge(account);

            StringBuilder emailMessage = new StringBuilder();
            emailMessage.append(String.format("Beste %s,\n\n", account.getUserName()));
            emailMessage.append(String.format("Het paswoord van uw account werd veranderd naar: %s\n", nieuwPaswoord));
            emailMessage.append(String.format("Log in met dit paswoord en verander uw paswoord via de link rechtsbovenaan 'Ingelogd als: %s'", account.getUserName()));
            emailMessage.append("\n\n\nDeze mail is automatisch gegenereerd. Gelieve hier niet op te antwoorden.");

            ArrayList<String> emailAdres = new ArrayList<String>();
            emailAdres.add(account.getEmail());
            mailService.sendMail("Accountsupport LiggendeWip.be", emailMessage.toString(), emailAdres);

        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
    }

    @Override
    public void verstuurPaswoordResetLink(Account account) throws BusinessException {

        try {

            PaswoordResetInfo paswoordResetInfo = PaswoordResetInfo.create(account.getId());
            entityManager.persist(paswoordResetInfo);

            String resetLink = String.format("http://www.liggendewip.be/paswoord_reset.xhtml?uuid=%s", paswoordResetInfo.getLinkUUID().toString());

            StringBuilder emailMessage = new StringBuilder();
            emailMessage.append(String.format("Beste %s,\n\n", account.getUserName()));
            emailMessage.append(String.format("Klik op onderstaande link op uw paswoord te resetten: \n%s", resetLink));
            emailMessage.append(String.format("\nDeze link is 2 uur geldig"));
            emailMessage.append("\n\n\nDeze mail is automatisch gegenereerd. Gelieve hier niet op te antwoorden.");

            ArrayList<String> emailAdres = new ArrayList<String>();
            emailAdres.add(account.getEmail());
            mailService.sendMail("Accountsupport LiggendeWip.be", emailMessage.toString(), emailAdres);

            paswoordTimerService.createResetLinkExpirationTimer(7200000, paswoordResetInfo);

        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
    }

    @Override
    public Account valideerResetLink(UUID linkUUUD) throws BusinessException {
        try {
            PaswoordResetInfo paswoordResetInfo = entityManager.createQuery("select pwInfo from PaswoordResetInfo pwInfo where pwInfo.linkUUID = :uuid", PaswoordResetInfo.class)
                    .setParameter("uuid", linkUUUD)
                    .getSingleResult();

            return entityManager.find(Account.class, paswoordResetInfo.getAccountId());
        } catch (NoResultException noResEx) {
            return null;
        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
    }
}
