package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Foto;
import be.oklw.model.Kampioenschap;
import be.oklw.model.Toernooi;
import be.oklw.util.Datum;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class KampioenschapService implements IKampioenschapService {

    @PersistenceContext
    EntityManager entityManager;

    @EJB
    IFileService fileService;

    @Override
    public Kampioenschap getKampioenschap(int id) {
        return entityManager.find(Kampioenschap.class, id);
    }

    @Override
    public void opslaan(Kampioenschap kampioenschap) throws BusinessException {
        try {
            entityManager.merge(kampioenschap);
        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
    }

    @Override
    public void nieuwToernooi(Toernooi toernooi, Kampioenschap kampioenschap) throws BusinessException {
        if (toernooi.getDatum().compareTo(kampioenschap.getBeginDatum()) < 0 || toernooi.getDatum().compareTo(kampioenschap.getEindDatum()) > 0) {
            throw new BusinessException(String.format(
                    "De toernooidatum moet liggen tussen %s en %s",
                    kampioenschap.getBeginDatum().getDatumInEuropeesFormaat(),
                    kampioenschap.getEindDatum().getDatumInEuropeesFormaat())
            );
        }

        if (toernooi.getInschrijfDeadline().compareTo(toernooi.getDatum()) > 0) {
            throw new BusinessException("De inschrijfdeadline moet voor de toernooidatum liggen");
        }

        if (toernooi.getInschrijfDeadline().compareTo(new Datum()) <= 0) {
            throw new BusinessException("De inschrijfdeadline moet in de toekomst liggen");
        }

        try {
            entityManager.persist(toernooi);
            kampioenschap.addToernooi(toernooi);
            entityManager.merge(kampioenschap);
        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
    }

    @Override
    public void opslaanToernooi(Toernooi toernooi) throws BusinessException {
        if (toernooi.getDatum().compareTo(toernooi.getKampioenschap().getBeginDatum()) < 0 || toernooi.getDatum().compareTo(toernooi.getKampioenschap().getEindDatum()) > 0) {
            throw new BusinessException(String.format(
                    "De toernooidatum moet liggen tussen %s en %s",
                    toernooi.getKampioenschap().getBeginDatum().getDatumInEuropeesFormaat(),
                    toernooi.getKampioenschap().getEindDatum().getDatumInEuropeesFormaat())
            );
        }

        if (toernooi.getInschrijfDeadline().compareTo(toernooi.getDatum()) > 0) {
            throw new BusinessException("De inschrijfdeadline moet voor de toernooidatum liggen");
        }

        if (toernooi.getInschrijfDeadline().compareTo(new Datum()) <= 0) {
            throw new BusinessException("De inschrijfdeadline moet in de toekomst liggen");
        }

        try {
            entityManager.merge(toernooi);
        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
    }

    @Override
    public Kampioenschap verwijderToernooi(Toernooi toernooi, Kampioenschap kampioenschap) throws BusinessException {
        try {
            kampioenschap.removeToernooi(toernooi);
            return entityManager.merge(kampioenschap);
        } catch (Exception ex) {
            throw new BusinessException("Er ging iets mis: " + ex.getMessage());
        }
    }

    @Override
    public Kampioenschap addFoto(byte[] fileContent, String fileName, Kampioenschap kampioenschap) throws BusinessException {
        Foto foto = new Foto();
        foto.setOriginalFilename(fileName);

        try {

            foto.setFilename(
                    fileService.upload(fileContent, fileName, "fotos")
            );
            kampioenschap.addFoto(foto);
            return entityManager.merge(kampioenschap);

        } catch (Exception ex) {
            throw new BusinessException(
                    String.format("Er ging iets mis met het uploaden van de foto '%s'%n%s", fileName, ex.getMessage())
            );
        }
    }

    @Override
    public List<Foto> getFotos(Kampioenschap kampioenschap) throws BusinessException {
        try {
            return entityManager.createQuery("SELECT f FROM Foto f WHERE f.kampioenschap.id=:kampId", Foto.class)
                    .setParameter("kampId", kampioenschap.getId())
                    .getResultList();
        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
    }

    @Override
    public List<Foto> getFotos(int kampioenschapId) throws BusinessException {
        try {
            return entityManager.createQuery("SELECT f FROM Foto f WHERE f.kampioenschap.id=:kampId", Foto.class)
                    .setParameter("kampId", kampioenschapId)
                    .getResultList();
        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
    }

    @Override
    public Kampioenschap verwijderFoto(int fotoId, Kampioenschap kampioenschap) throws BusinessException {
        try {
            Optional<Foto> teVerwijderenFotoOpt = kampioenschap.getFotos().stream().filter(f -> f.getId() == fotoId).findFirst();
            Kampioenschap geupdate;

            if (teVerwijderenFotoOpt.isPresent()) {
                kampioenschap.removeFoto(fotoId);
                geupdate = entityManager.merge(kampioenschap);

                // Als er zich een exception voordoet in bovenstaande code wordt dit niet uitgevoerd.
                fileService.delete(
                        teVerwijderenFotoOpt.get().getFilename(),
                        Foto.getRelativePath()
                );

                return geupdate;
            }
            
            return kampioenschap;
        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
    }

    @Override
    public void saveFoto(Foto foto) throws BusinessException {
        try {
            entityManager.merge(foto);
        } catch (Exception ex) {
            throw new BusinessException("Er liep iets mis: " + ex.getMessage());
        }
    }

    @Override
    public List<Kampioenschap> getKampioenschappenMetFotos() {
        List<Kampioenschap> alleKampioenschappen = entityManager.createQuery("SELECT k from Kampioenschap k", Kampioenschap.class).getResultList();
        List<Kampioenschap> alleKampioenschappenMetFotos = alleKampioenschappen.stream().filter(
                k -> k.getFotos().size() > 0
        ).collect(Collectors.toList());

        return alleKampioenschappenMetFotos;
    }

    @Override
    public List<Kampioenschap> getKampioenschappen(boolean uitVerleden) {
        List<Kampioenschap> alleKampioenschappen = entityManager.createQuery("SELECT k FROM Kampioenschap k", Kampioenschap.class).getResultList();

        Datum vandaag = new Datum();
        List<Kampioenschap> result;
        if (uitVerleden) {
            result = alleKampioenschappen.stream().filter(k -> k.getEindDatum().compareTo(vandaag) < 0).collect(Collectors.toList());
        } else {
            result = alleKampioenschappen.stream().filter(k -> k.getEindDatum().compareTo(vandaag) >= 0).collect(Collectors.toList());
        }

        return result;
    }

    @Override
    public List<Kampioenschap> getKampioenschapenInschrijvenMogelijk() {
        List<Kampioenschap> alleKampioenschappen = entityManager.createQuery("SELECT k FROM Kampioenschap k", Kampioenschap.class).getResultList();

        Datum vandaag = new Datum();
        List<Kampioenschap> toekomstigeKampioenschappen;

        // TOEKOMSTIGE KAMPIOENSCHAPPEN
        toekomstigeKampioenschappen = alleKampioenschappen.stream().filter(k -> k.getEindDatum().compareTo(vandaag) > 0).collect(Collectors.toList());

        boolean toernooiMetInschrijving = false;
        Iterator<Toernooi> iterator;
        Toernooi toernooi;
        List<Kampioenschap> result = new ArrayList<>();
        for (Kampioenschap k : toekomstigeKampioenschappen) {
            toernooiMetInschrijving = false;

            iterator = k.getToernooien().iterator();
            while (!toernooiMetInschrijving && iterator.hasNext()) {
                toernooi = iterator.next();
                toernooiMetInschrijving = toernooi.getStatus().isInschrijvingenOpen();
            }

            if (toernooiMetInschrijving) {
                result.add(k);
            }
        }

        return result;
    }
}
