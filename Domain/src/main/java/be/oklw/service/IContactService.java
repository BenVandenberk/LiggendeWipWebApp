package be.oklw.service;

import be.oklw.exception.BusinessException;

import javax.ejb.Remote;

@Remote
public interface IContactService {

    void maakNieuwContactAan(String naam, String telefoonnummer, String email, boolean isBeheerder) throws BusinessException;
}