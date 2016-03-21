package be.oklw.service;

import be.oklw.model.Club;
import be.oklw.model.Lid;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class LedenService implements ILedenService {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Lid> ledenVanClub(Club club) {
        List<Lid> leden = entityManager.createQuery("from Lid l where l.club.id=:clubId", Lid.class)
                .setParameter("clubId", club.getId())
                .getResultList();
        return leden;
    }

    @Override
    public Lid getLid(int id) {
        return entityManager.find(Lid.class, id);
    }

    @Override
    public List<Lid> alleLeden() {
        return entityManager.createQuery("from Lid", Lid.class).getResultList();
    }
}
