package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Club;
import be.oklw.model.Inschrijving;
import be.oklw.model.Toernooi;
import be.oklw.util.Datum;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        List<Inschrijving> result = new ArrayList<>();

        toekomstigeToernooien.stream()
                .forEach(toer -> {
                    Inschrijving inschrijving = toer.getInschrijvingVan(club);
                    if (inschrijving != null) {
                        result.add(inschrijving);
                    }
                });

        return result;
    }

    @Override
    public String openInschrijvingen(Toernooi toernooi) throws BusinessException {
        toernooi.openInschrijvingen();
        toernooiService.save(toernooi);

        try {
            List<String> emailAdressen = entityManager.createQuery("select c.email from Contact c", String.class).getResultList();

            StringBuilder builder = new StringBuilder();
            builder.append("Beste beheerder/boogschutter,\n\n");
            builder.append(String.format("De inschrijvingen voor het toernooi %s van club %s zijn geopend", toernooi.getNaam(), toernooi.getKampioenschap().getClub().getNaam()));
            builder.append("\n\n\nDeze mail is automatisch gegenereerd. Gelieve hier niet op te antwoorden.");

            mailService.sendMail("Inschrijvingen opengesteld", builder.toString(), emailAdressen);

            builder = new StringBuilder();
            builder.append("De inschrijvingen zijn geopend. Er werd een mail verstuurd naar:\n\n");
            for (String adres : emailAdressen) {
                builder.append(adres + "\n");
            }
            return builder.toString();

        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
    }

    @Override
    public void save(Inschrijving inschrijving) throws BusinessException {
        try {
            entityManager.merge(inschrijving);
        } catch (Exception ex) {
            throw new BusinessException("Er ging iets mis: " + ex.getMessage());
        }
    }
}
