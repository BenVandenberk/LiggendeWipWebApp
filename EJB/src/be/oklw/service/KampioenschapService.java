package be.oklw.service;

import be.oklw.model.Kampioenschap;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Remote
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class KampioenschapService implements IKampioenschapService {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Kampioenschap getKampioenschap(int id) {
        return entityManager.find(Kampioenschap.class, id);
    }

    @Override
    public void opslaan(Kampioenschap kampioenschap) {
        entityManager.merge(kampioenschap);
        entityManager.flush();
    }
}
