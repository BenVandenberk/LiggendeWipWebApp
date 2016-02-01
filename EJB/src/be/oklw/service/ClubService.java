package be.oklw.service;

import be.oklw.model.Club;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

        Club dbClub = entityManager.find(Club.class, club.getId());
        dbClub.setLogoPad(logoPad);
        entityManager.persist(dbClub);
    }
}
