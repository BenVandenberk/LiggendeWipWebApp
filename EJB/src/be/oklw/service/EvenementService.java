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

    @Override
    public void maakNieuwKampioenschapAan(String naam, String clubNaam, Datum start, Datum eind) throws BusinessException{
        Club club = (Club)entityManager.createQuery("select c from Club c where c.naam = :selClub")
                .setParameter("selClub", clubNaam).getSingleResult();
        Kampioenschap kampioenschap = club.maakKampioenschap(naam, start, eind);

        entityManager.persist(kampioenschap);
        //entityManager.merge(club);
        entityManager.flush();
    }

    @Override
    public void maakNieuwEvenementAan(String naam, String clubNaam, Datum start, Datum eind, String locatie, String omschrijving) throws BusinessException{
        Club club = (Club)entityManager.createQuery("select c from Club c where c.naam = :selClub")
                .setParameter("selClub", clubNaam).getSingleResult();
        Evenement evenement = club.maakEvenement(naam, start, eind, locatie, omschrijving);

        entityManager.persist(evenement);
        //entityManager.merge(club);
        entityManager.flush();
    }

    @Override
    public void verwijderEvenement(Evenement evenement) throws BusinessException{

        Evenement teVerwijderenEvenement = entityManager.find(Evenement.class, evenement.getId());

        if (teVerwijderenEvenement instanceof Kampioenschap){
            Kampioenschap teVerwijderenKampioenschap = (Kampioenschap) teVerwijderenEvenement;
            if(teVerwijderenKampioenschap.isVerwijderbaar()){
                Club club = entityManager.find(Club.class, teVerwijderenKampioenschap.getClub().getId());
                club.removeEvenement(teVerwijderenKampioenschap);
                entityManager.remove(teVerwijderenKampioenschap);
                entityManager.merge(club);
                entityManager.flush();
            }
            else{
                throw new BusinessException("Kampioenschap is niet verwijderbaar!");
            }
        }
        else{
            Club club = entityManager.find(Club.class, evenement.getClub().getId());
            club.removeEvenement(teVerwijderenEvenement);
            entityManager.remove(teVerwijderenEvenement);
            entityManager.merge(club);
            entityManager.flush();
        }
    }

}
