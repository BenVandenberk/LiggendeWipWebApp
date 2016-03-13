package be.oklw.service;

import be.oklw.model.Club;
import be.oklw.model.Ploeg;
import be.oklw.model.Toernooi;
import be.oklw.model.hulp.Inschrijving;
import be.oklw.util.Datum;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Local
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class InschrijfService implements IInschrijfService {

    @EJB
    IToernooiService toernooiService;

    @EJB
    IMailService mailService;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void verwijderPloeg(int ploegId) {
        entityManager.createQuery("DELETE from Ploeg p where p.id=:ploegID")
                .setParameter("ploegID", ploegId)
                .executeUpdate();
    }

    @Override
    public List<Inschrijving> getInschrijvingenVoor(Club club) {
        List<Toernooi> alleToernooien = entityManager.createQuery("SELECT t FROM Toernooi t", Toernooi.class).getResultList();

        Datum vandaag = new Datum();
        List<Toernooi> toekomstigeToernooien = alleToernooien.stream().filter(t -> t.getDatum().compareTo(vandaag) > 0).collect(Collectors.toList());

        List<Ploeg> ploegenVanClub;
        List<Inschrijving> inschrijvingenVanClub = new ArrayList<>();
        Inschrijving inschrijving;
        for (Toernooi toernooi : toekomstigeToernooien) {

            ploegenVanClub = toernooi.getPloegenVan(club);

            if (ploegenVanClub.size() > 0) {

                inschrijving = new Inschrijving();
                inschrijving.setAantalPloegenIngeschreven(ploegenVanClub.size());
                inschrijving.setDeelnemersPerPloeg(toernooi.getPersonenPerPloeg());
                inschrijving.setKampioenschapsnaam(toernooi.getKampioenschap().getNaam());
                inschrijving.setToernooinaam(toernooi.getNaam());
                inschrijving.setPloegen(ploegenVanClub);
                inschrijving.setToernooiHeeftMaaltijd(toernooi.isHeeftMaaltijd());

                inschrijvingenVanClub.add(inschrijving);

            }
        }

        return inschrijvingenVanClub;
    }

    @Override
    public String openInschrijvingen(Toernooi toernooi) {
        toernooi.openInschrijvingen();
        toernooiService.save(toernooi);

        List<String> emailAdressen = entityManager.createQuery("select c.email from Contact c", String.class).getResultList();

        StringBuilder builder = new StringBuilder();
        builder.append("Beste beheerder/boogschutter,\n\n");
        builder.append(String.format("De inschrijvingen voor het toernooi %s van club %s zijn geopend", toernooi.getNaam(), toernooi.getKampioenschap().getClub().getNaam()));

        mailService.sendMail("Inschrijvingen opengesteld", builder.toString(), emailAdressen);

        builder = new StringBuilder();
        builder.append("De inschrijvingen zijn geopend. Er werd een mail verstuurd naar:\n\n");
        for (String adres : emailAdressen) {
            builder.append(adres + "\n");
        }
        return builder.toString();
    }
}
