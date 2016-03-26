package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Contact;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ContactService implements IContactService {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Contact maakNieuwContactAan(String naam, String telefoonnummer, String email, boolean isBeheerder) throws BusinessException {
        Contact contact = new Contact(naam, telefoonnummer, email, isBeheerder);

        entityManager.persist(contact);
        entityManager.flush();

        return contact;
    }

    @Override
    public Contact wijzigContact (String naam, String telefoonnummer, String email, boolean isBeheerder, int id) throws BusinessException {
        Contact contact = getContact(id);
        contact.setNaam(naam);
        contact.setEmail(email);
        contact.setTelefoonnummer(telefoonnummer);
        contact.setIsBeheerder(isBeheerder);

        entityManager.merge(contact);
        entityManager.flush();

        return contact;
    }

    @Override
    public Contact getNieuwsteContact (){
        List<Contact> nieuwsteContactList = entityManager.createQuery("SELECT c FROM Contact c ORDER BY c.id desc")
                .setMaxResults(1).getResultList();
        Contact nieuwsteContact = nieuwsteContactList.get(0);
        return nieuwsteContact;
    }

    @Override
    public Contact getContact(int id){
        Contact contact = entityManager.find(Contact.class, id);
        return contact;
    }
}
