package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Account;

import javax.ejb.Remote;

@Remote
public interface IGebruikerService {

    Account login(String userName, String password);
    void createAdmin();
    void veranderPaswoord(Account account, String oud, String nieuw) throws BusinessException;
}
