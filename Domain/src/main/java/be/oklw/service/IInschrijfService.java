package be.oklw.service;

import be.oklw.model.Club;
import be.oklw.model.Toernooi;
import be.oklw.model.hulp.Inschrijving;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface IInschrijfService {

    void verwijderPloeg(int ploegId);

    List<Inschrijving> getInschrijvingenVoor(Club club);
}
