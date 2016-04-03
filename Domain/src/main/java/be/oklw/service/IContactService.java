package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Contact;

import javax.ejb.Local;

import java.util.List;

@Local
public interface IContactService {

    Contact maakNieuwContactAan(String naam, String telefoonnummer, String email, boolean isBeheerder) throws BusinessException;

    Contact wijzigContact(String naam, String telefoonnummer, String email, boolean isBeheerder, int contactId) throws BusinessException;

    Contact getNieuwsteContact () throws BusinessException;

    Contact getContact(int contactId);

    void verwijderOrphanContacten();
}
