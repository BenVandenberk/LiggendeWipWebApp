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
import java.util.stream.Collectors;

@Remote
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
    public void opslaan(Kampioenschap kampioenschap) {
        entityManager.merge(kampioenschap);
        entityManager.flush();
    }

    @Override
    public void nieuwToernooi(Toernooi toernooi, Kampioenschap kampioenschap) {
        entityManager.persist(toernooi);
        kampioenschap.addToernooi(toernooi);
        entityManager.flush();
    }

    @Override
    public void opslaanToernooi(Toernooi toernooi) {
        entityManager.merge(toernooi);
        entityManager.flush();
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
            entityManager.persist(foto);
            entityManager.flush();
            return kampioenschap;

        } catch (Exception ex) {
            throw new BusinessException(
                    String.format("Er ging iets mis met het uploaden van de foto '%s'%n%s", fileName, ex.getMessage())
            );
        }
    }

    @Override
    public List<Foto> getFotos(Kampioenschap kampioenschap) {
        return (List<Foto>) entityManager.createQuery("SELECT f FROM Foto f WHERE f.kampioenschap.id=:kampId")
                .setParameter("kampId", kampioenschap.getId())
                .getResultList();
    }

    @Override
    public List<Foto> getFotos(int kampioenschapId) {
        return (List<Foto>) entityManager.createQuery("SELECT f FROM Foto f WHERE f.kampioenschap.id=:kampId")
                .setParameter("kampId", kampioenschapId)
                .getResultList();
    }

    @Override
    public void verwijderFoto(int fotoId, Kampioenschap kampioenschap) {
        Foto teVerwijderen = entityManager.find(Foto.class, fotoId);
        entityManager.remove(teVerwijderen);
        entityManager.flush();
    }

    @Override
    public void saveFoto(Foto foto) {
        entityManager.merge(foto);
        entityManager.flush();
    }

    @Override
    public List<Kampioenschap> getKampioenschappenMetFotos() {
        List<Kampioenschap> alleKampioenschappen = (List<Kampioenschap>) entityManager.createQuery("SELECT k from Kampioenschap k").getResultList();
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
            result = alleKampioenschappen.stream().filter(k -> k.getEindDatum().compareTo(vandaag) <= 0).collect(Collectors.toList());
        } else {
            result = alleKampioenschappen.stream().filter(k -> k.getEindDatum().compareTo(vandaag) > 0).collect(Collectors.toList());
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
