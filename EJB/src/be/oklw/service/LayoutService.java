package be.oklw.service;

import be.oklw.layout.Background;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class LayoutService implements ILayoutService {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void maakSiteLayoutIfNotExists() {
        List<Background> backgrounds = entityManager.createQuery("SELECT b from Background b", Background.class).getResultList();

        if (backgrounds.size() == 0) {
            Background siteBackground = new Background();
            siteBackground.setPrimaireKleurBody("#ffffff");
            siteBackground.setPrimaireKleurBanners("#ffffff");
            entityManager.persist(siteBackground);
        }
    }

    @Override
    public Background siteBackground() {
        return entityManager.createQuery("SELECT b from Background b", Background.class).getResultList().get(0);
    }

    @Override
    public void opslaan(Background background) {
        entityManager.merge(background);
    }
}
