package be.oklw.service;

import be.oklw.model.Account;

/**
 * Created by java on 23.01.16.
 */
public interface IGebruikerService {

    Account login(String userName, String password);
    void createAdmin();
}
