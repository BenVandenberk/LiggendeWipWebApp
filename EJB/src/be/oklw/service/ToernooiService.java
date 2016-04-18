package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Inschrijving;
import be.oklw.model.Ploeg;
import be.oklw.model.Toernooi;
import be.oklw.model.state.InschrijvingenAfgesloten;
import be.oklw.util.Datum;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ToernooiService implements IToernooiService {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Toernooi getToernooi(int id) throws BusinessException {
        Toernooi toernooi = entityManager.find(Toernooi.class, id);

        if (toernooi == null) {
            throw new BusinessException("Ongeldige toernooi id");
        }

        return toernooi;
    }

    @Override
    public Toernooi save(Toernooi toernooi) throws BusinessException {
        try {
            return entityManager.merge(toernooi);
        } catch (Exception ex) {
            throw new BusinessException("Er ging iets mis: " + ex.getMessage());
        }
    }

    @Override
    public Toernooi saveToernooiInschrijvingen(Toernooi toernooi, Inschrijving inschrijving) throws BusinessException {

        // GEVAL 1: voor de functionaliteit 'Inschrijvingen Beheren' moeten alle inschrijvingen van het Toernooi op geldigheid gecontroleerd worden
        if (inschrijving == null) {
            for (Inschrijving ins : toernooi.getInschrijvingen()) {
                for (Ploeg p : ins.getPloegen()) {
                    if (p.heeftZelfdeLeden()) {
                        throw new BusinessException("Een ploeg mag niet twee dezelfde leden hebben");
                    }
                }
            }
        }

        // GEVAL 2: voor de functionaliteit 'Inschrijven' moet er 1 inschrijving op geldigheid gecontroleerd worden
        if (inschrijving != null) {
            for (Ploeg p : inschrijving.getPloegen()) {
                if (p.heeftZelfdeLeden()) {
                    throw new BusinessException("Een ploeg mag niet twee dezelfde leden hebben");
                }
            }
        }

        return save(toernooi);
    }

    @Override
    public void updateInschrijfStatusAlleToernooien() {
        List<Toernooi> alleToernooien = entityManager.createQuery("select t from Toernooi t", Toernooi.class).getResultList();

        for (Toernooi toernooi : alleToernooien) {

            if (toernooi.inschrijvingenOpen()) {

                if (toernooi.getInschrijfDeadline().compareTo(new Datum()) < 0) {
                    toernooi.setStatus(new InschrijvingenAfgesloten());
                    entityManager.merge(toernooi);
                }

            }

        }
    }
}
