package be.oklw.service;

import be.oklw.model.Toernooi;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Local
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ToernooiService implements IToernooiService {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Toernooi getToernooi(int id) {
        return entityManager.find(Toernooi.class, id);
    }

    @Override
    public Toernooi save(Toernooi toernooi) {
        return entityManager.merge(toernooi);
    }
}
