package be.oklw.service;

import be.oklw.model.Account;
import be.oklw.util.Datum;

import javax.ejb.Remote;

@Remote
public interface INieuwsService {

    void maakNieuwtjeAan(String tekst, Datum datum, Account account);

}
