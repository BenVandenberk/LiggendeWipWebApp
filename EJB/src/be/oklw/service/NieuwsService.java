package be.oklw.service;

import be.oklw.model.Account;
import be.oklw.model.Contact;
import be.oklw.model.Nieuws;
import be.oklw.util.Datum;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Remote
@TransactionManagement(TransactionManagementType.CONTAINER)
public class NieuwsService implements INieuwsService {

    @PersistenceContext
    EntityManager entityManager;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void maakNieuwtjeAan(String tekst, Datum datum, Account account) {
        Nieuws nieuws = new Nieuws(tekst, datum, account);

        entityManager.persist(nieuws);
        entityManager.flush();
    }
}
