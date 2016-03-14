package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Toernooi;

import javax.ejb.Local;

@Local
public interface IToernooiService {

    Toernooi getToernooi(int id);

    Toernooi save(Toernooi toernooi) throws BusinessException;
}
