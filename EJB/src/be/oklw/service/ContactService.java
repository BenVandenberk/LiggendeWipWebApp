package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Contact;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public Contact getNieuwsteContact() throws BusinessException {
        //Contact contact = (Contact)entityManager.createQuery("SELECT c FROM Contact c WHERE c.id = (SELECT MAX(d.id) FROM Contact d)");
        return new Contact("jos", "03", "@", true);
    }

}
