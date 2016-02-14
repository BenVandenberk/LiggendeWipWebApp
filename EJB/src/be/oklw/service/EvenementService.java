package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Club;
import be.oklw.model.Evenement;
import be.oklw.model.Kampioenschap;
import be.oklw.util.Datum;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Remote
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class EvenementService implements IEvenementService{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Evenement> getAlleEvenementen() {
        Query query = entityManager.createQuery("SELECT k FROM Evenement k");
        return (List<Evenement>) query.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void maakNieuwKampioenschapAan(String naam, String clubNaam, Datum start, Datum eind) throws BusinessException{
        Club club = (Club)entityManager.createQuery("select c from Club c where c.naam = :selClub")
                .setParameter("selClub", clubNaam).getSingleResult();
        Kampioenschap kampioenschap = club.maakKampioenschap(naam, start, eind);

        entityManager.persist(kampioenschap);
        entityManager.flush();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void maakNieuwEvenementAan(String naam, String clubNaam, Datum start, Datum eind, String locatie, String omschrijving) throws BusinessException{
        Club club = (Club)entityManager.createQuery("select c from Club c where c.naam = :selClub")
                .setParameter("selClub", clubNaam).getSingleResult();
        Evenement evenement = club.maakEvenement(naam, start, eind, locatie, omschrijving);

        entityManager.persist(evenement);
        entityManager.flush();
    }
}
