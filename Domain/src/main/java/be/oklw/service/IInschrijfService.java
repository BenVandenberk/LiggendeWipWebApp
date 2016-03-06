package be.oklw.service;

import be.oklw.model.Club;
import be.oklw.model.Toernooi;

import javax.ejb.Remote;

@Remote
public interface IInschrijfService {

    void verwijderPloeg(int ploegId);
}
