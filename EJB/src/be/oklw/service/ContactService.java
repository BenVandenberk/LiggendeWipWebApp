package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Contact;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Remote
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ContactService implements IContactService {

    @PersistenceContext
    EntityManager entityManager;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void maakNieuwContactAan(String naam, String telefoonnummer, String email, boolean isBeheerder) throws BusinessException {
        Contact contact = new Contact(naam, telefoonnummer, email, isBeheerder);

        entityManager.persist(contact);
        entityManager.flush();
    }

    @Override
    public List<Contact> alleContacten(int aantalContacten) {
        List<Contact> contacten = entityManager.createQuery("SELECT c FROM Contact c ORDER BY c.id desc")
                .setMaxResults(aantalContacten)
                .getResultList();

        return contacten;
    }
}
