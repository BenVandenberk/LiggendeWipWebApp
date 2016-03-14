package be.oklw.service;

import be.oklw.exception.BusinessException;
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
    public Toernooi save(Toernooi toernooi) throws BusinessException {
        try {
            return entityManager.merge(toernooi);
        } catch (Exception ex) {
            throw new BusinessException("Er ging iets mis: " + ex.getMessage());
        }
    }
}
