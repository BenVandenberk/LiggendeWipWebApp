package be.oklw.service;

import be.oklw.model.Account;
import be.oklw.model.Contact;
import be.oklw.model.Nieuws;
import be.oklw.util.Datum;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Remote
@TransactionManagement(TransactionManagementType.CONTAINER)
public class NieuwsService implements INieuwsService {

    @PersistenceContext
    EntityManager entityManager;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void maakNieuwtjeAan(String tekst, Datum datum, Datum tonenTot, Account account) {
        Nieuws nieuws = new Nieuws(tekst, datum, tonenTot, account);
        List<Nieuws> nieuweNieuwsLijst = account.getNieuwsList();
        nieuweNieuwsLijst.add(nieuws);
        account.setNieuwsList(nieuweNieuwsLijst);
        entityManager.persist(nieuws);
        entityManager.merge(account);
        entityManager.flush();
    }

    @Override
    public List<Nieuws> getLaatsteNieuwtjes(){
        List<Nieuws> laatsteNieuws = (List<Nieuws>) entityManager.createQuery("Select n " +
                "from Nieuws n where n.tonenTot > :today")
                .setParameter("today", new Datum())
                .getResultList();

        return laatsteNieuws;
    }

    @Override
    public void verwijderNieuwtje(Nieuws nieuws){
        Nieuws teVerwijderenNieuws = entityManager.find(Nieuws.class, nieuws.getId());
        nieuws.getAccount().removeNieuwtje(teVerwijderenNieuws);
        entityManager.remove(teVerwijderenNieuws);
        entityManager.merge(nieuws.getAccount());
        entityManager.flush();
    }
}
