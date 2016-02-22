package be.oklw.service;

import be.oklw.exception.BusinessException;
import be.oklw.model.Foto;
import be.oklw.model.Kampioenschap;
import be.oklw.model.Toernooi;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

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
    public Kampioenschap addFoto(byte[] fileContent, String fileName, Kampioenschap kampioenschap) throws BusinessException{
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
        return (List<Foto>)entityManager.createQuery("SELECT f FROM Foto f WHERE f.kampioenschap.id=:kampId")
                                        .setParameter("kampId", kampioenschap.getId())
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
}
