package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Account;
import be.oklw.model.Contact;
import be.oklw.model.Nieuws;
import be.oklw.util.Datum;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class NieuwsService implements INieuwsService {

    @PersistenceContext
    EntityManager entityManager;

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
    public void verwijderNieuwtje(Nieuws nieuws, Account account){
        Nieuws teVerwijderenNieuws = entityManager.find(Nieuws.class, nieuws.getId());
        account.removeNieuwtje(teVerwijderenNieuws);
        entityManager.remove(teVerwijderenNieuws);
        entityManager.merge(account);
    }

    @Override
    public List<Nieuws> getAlleTeTonenNieuwtjes() {
        List<Nieuws> alleNieuwtjes = entityManager.createQuery("select n from Nieuws n", Nieuws.class).getResultList();
        Datum vandaag = new Datum();

        return alleNieuwtjes.stream().filter(n -> vandaag.kleinerDan(n.getTonenTot())).collect(Collectors.toList());
    }

    @Override
    public void saveNieuwtje(Nieuws nieuws) throws BusinessException {
        Datum vandaag = new Datum();

        if (nieuws.getTonenTot() == null) {
            throw new BusinessException("'Te tonen tot' is verplicht");
        }

        if (StringUtils.isBlank(nieuws.getNieuws())) {
            throw new BusinessException("'Nieuws' is verplicht");
        }

        if (nieuws.getTonenTot().kleinerDan(vandaag)) {
            throw new BusinessException("'Te tonen tot' moet in de toekomst liggen");
        }

        try {
            entityManager.merge(nieuws);
        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
    }
}
