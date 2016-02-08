package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Contact;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by java on 02.02.16.
 */
public class ContactService implements IContactService{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void maakNieuwContactAan(String naam, String telefoonnummer, String email, boolean isBeheerder) throws BusinessException {
        Contact contact = new Contact(naam, telefoonnummer, email, isBeheerder);
        entityManager.persist(contact);
    }
}
