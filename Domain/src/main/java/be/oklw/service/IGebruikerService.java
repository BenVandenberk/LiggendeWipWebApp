package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Account;
import be.oklw.model.SysteemAccount;

import javax.ejb.Remote;

@Remote
public interface IGebruikerService {

    Account login(String userName, String password);

    void createAdmin();

    Account veranderPaswoord(Account account, String oud, String nieuw) throws BusinessException;

    SysteemAccount getSysteemAccount(int id);
}
