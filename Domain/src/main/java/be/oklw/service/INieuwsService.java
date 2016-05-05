package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Account;
import be.oklw.model.Nieuws;
import be.oklw.util.Datum;

import javax.ejb.Local;

import java.util.List;

@Local
public interface INieuwsService {

    void maakNieuwtjeAan(String tekst, Datum datum, Datum tonenTot, Account account);

    List<Nieuws> getLaatsteNieuwtjes();

    void verwijderNieuwtje(Nieuws nieuws, Account account);

    List<Nieuws> getAlleTeTonenNieuwtjes();

    void saveNieuwtje(Nieuws nieuws) throws BusinessException;

}
