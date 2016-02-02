package be.oklw.service;

import be.oklw.model.Account;
import be.oklw.model.Club;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import java.io.IOException;

@Stateless
@Remote
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ClubService implements IClubService {

    @EJB
    IFileService fileService;

    @PersistenceContext
    EntityManager entityManager;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void veranderClubLogo(byte[] fileContent, String fileName, Club club) throws IOException {
        String logoPad = fileService.upload(fileContent, fileName, "clublogos");

        club.setLogoBreedte(120);
        club.setLogoHoogte(120);
        club.setLogoPad(logoPad);
        entityManager.merge(club);
        entityManager.flush();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public Club getClub(Account account) {
        Club club = (Club)entityManager.createQuery("select c from Club c where c.account.id = :accId")
                                        .setParameter("accId", account.getId())
                                        .getSingleResult();
        return club;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void bewaarAfmetingen(Club club) {
        entityManager.merge(club);
        entityManager.flush();
    }
}
