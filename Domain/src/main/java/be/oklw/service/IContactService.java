package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Contact;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface IContactService {

    void maakNieuwContactAan(String naam, String telefoonnummer, String email, boolean isBeheerder) throws BusinessException;

    List<Contact> alleContacten(int aantalContacten);


}
