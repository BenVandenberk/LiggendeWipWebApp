package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Account;
import be.oklw.model.SysteemAccount;

import javax.ejb.Local;

import java.util.List;

@Local
public interface IGebruikerService {

    Account login(String userName, String password) throws BusinessException;

    void createAdmin();

    Account veranderPaswoord(Account account, String oud, String nieuw) throws BusinessException;

    SysteemAccount getSysteemAccount(int id);

    List<SysteemAccount> getAllSysteemAccount();

    Account wijzigAdminContactGegevens(SysteemAccount systeemAccount, String newEmail, String newTelefoonNummer) throws BusinessException;
}
