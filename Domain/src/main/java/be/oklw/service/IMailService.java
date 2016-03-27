package be.oklw.service;

import be.oklw.exception.BusinessException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IMailService {

    /**
     * Verstuurt een e-mail naar alle meegegeven adressen
     * @param subject het onderwerp van de e-mail
     * @param body de body van de e-mail
     * @param toAdressen de lijst met ontvangadressen
     * @return
     * @throws BusinessException als er iets fout loopt met het versturen van de mail(s)
     */
    boolean sendMail(String subject, String body, List<String> toAdressen) throws BusinessException;
}
