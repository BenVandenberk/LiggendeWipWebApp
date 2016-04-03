package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Contact;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ContactService implements IContactService {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Contact maakNieuwContactAan(String naam, String telefoonnummer, String email, boolean isBeheerder) throws BusinessException {
        try{
        Contact contact = new Contact(naam, telefoonnummer, email, isBeheerder);

        entityManager.persist(contact);

        return contact;
        } catch (Exception ex) {
            throw new BusinessException(String.format("Er liep iets mis: %s", ex.getMessage()));
        }
    }

    @Override
    public Contact wijzigContact (String naam, String telefoonnummer, String email, boolean isBeheerder, int id) throws BusinessException {
       try{
        Contact contact = getContact(id);
        contact.setNaam(naam);
        contact.setEmail(email);
        contact.setTelefoonnummer(telefoonnummer);
        contact.setIsBeheerder(isBeheerder);

        entityManager.merge(contact);

        return contact;
       } catch (Exception ex) {
           throw new BusinessException(String.format("Er liep iets mis: %s", ex.getMessage()));
       }
    }

    @Override
    public Contact getNieuwsteContact () throws BusinessException {
        try{
        List<Contact> nieuwsteContactList = entityManager.createQuery("SELECT c FROM Contact c ORDER BY c.id desc")
                .setMaxResults(1).getResultList();
        Contact nieuwsteContact = nieuwsteContactList.get(0);
        return nieuwsteContact;
        } catch (Exception ex) {
            throw new BusinessException(String.format("Er liep iets mis: %s", ex.getMessage()));
        }
    }

    @Override
    public Contact getContact(int id){
        Contact contact = entityManager.find(Contact.class, id);
        return contact;
    }

    @Override
    public void verwijderOrphanContacten(){
        List<Contact> orphanContacten = entityManager.createQuery("SELECT c FROM Contact c WHERE c.Club_id = :null")
                .setParameter("null", null)
                .getResultList();
        for (Contact c : orphanContacten){
            entityManager.remove(c);
        }
    }
}
