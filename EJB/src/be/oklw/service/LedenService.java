package be.oklw.service;

import be.oklw.hulp.LidInschrijving;
import be.oklw.model.Club;
import be.oklw.model.Inschrijving;
import be.oklw.model.Lid;
import be.oklw.model.Ploeg;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class LedenService implements ILedenService {

    @PersistenceContext
    EntityManager entityManager;

    @EJB
    IInschrijfService inschrijfService;

    @Override
    public List<Lid> ledenVanClub(Club club) {
        return entityManager.createQuery("select l from Lid l where l.club.id=:clubId", Lid.class)
                .setParameter("clubId", club.getId())
                .getResultList();
    }

    @Override
    public Lid getLid(int id) {
        return entityManager.find(Lid.class, id);
    }

    @Override
    public List<Lid> alleLeden() {
        return entityManager.createQuery("select l from Lid l", Lid.class).getResultList();
    }

    @Override
    public List<LidInschrijving> alleToekomstigeInschrijvingen(Lid lid) {
        List<Inschrijving> inschrijvingenVanClub = inschrijfService.getInschrijvingenVoor(lid.getClub());

        List<LidInschrijving> lidInschrijvingen = new ArrayList<>();

        for (Inschrijving inschrijving : inschrijvingenVanClub) {
            for (Ploeg ploegMetLid : inschrijving.getPloegenMetLid(lid)) {
                lidInschrijvingen.add(
                        new LidInschrijving(
                                ploegMetLid,
                                inschrijving.getToernooi().getKampioenschap(),
                                inschrijving.getToernooi()
                        )
                );
            }
        }

        // Sorteren op toernooidatum
        lidInschrijvingen.sort(
                (ins, ins2) -> ins.getToerDatum().compareTo(ins2.getToerDatum())
        );
        return lidInschrijvingen;
    }
}
