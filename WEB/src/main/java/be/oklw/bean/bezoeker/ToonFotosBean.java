package be.oklw.bean.bezoeker;

import be.oklw.model.Foto;
import be.oklw.model.Kampioenschap;
import be.oklw.service.IKampioenschapService;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.List;

@ManagedBean
@ViewScoped
public class ToonFotosBean {

    @EJB
    IKampioenschapService kampioenschapService;

    private int kampioenschapsId = -999;
    private List<Foto> fotos;
    private Kampioenschap kampioenschap;

    public int getKampioenschapsId() {
        return kampioenschapsId;
    }

    public Kampioenschap getKampioenschap() {
        return kampioenschap;
    }

    // Deze setter wordt aangeroepen bij elke HTTP GET van bezoeker_kampioenschap_fotos?id=<KAMPIOENSCHAP_ID>
    public void setKampioenschapsId(int kampioenschapsId) {
        boolean veranderd = this.kampioenschapsId != kampioenschapsId;

        this.kampioenschapsId = kampioenschapsId;

        try {

            if (veranderd) {
                kampioenschap = kampioenschapService.getKampioenschap(kampioenschapsId);

                // Als een ongeldige id meekomt in de url, redirecten naar de index pagina
                if (kampioenschap == null) {
                    redirect();
                }

                fotos = kampioenschapService.getFotos(kampioenschapsId);
            }

        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Fout",
                            ex.getMessage()
                    )
            );
        }
    }

    public List<Foto> getFotos() {
        return fotos;
    }

    private void redirect() {
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + "bezoeker_fotos.xhtml");
        } catch (Exception ex) {
            System.err.println(String.format("Fout bij redirection: %s", ex.getMessage()));
        }
    }
}
