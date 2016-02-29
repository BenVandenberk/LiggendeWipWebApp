package be.oklw.bean.club;

import be.oklw.bean.club.ClubBeheerBean;
import be.oklw.model.Club;
import be.oklw.model.Sponsor;
import be.oklw.service.IClubService;
import be.oklw.service.ISponsorService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
@ManagedBean
public class SponsorDialogBean {

    @EJB
    IClubService clubService;

    @EJB
    ISponsorService sponsorService;

    @ManagedProperty(value="#{clubBeheerBean}")
    ClubBeheerBean clubBeheerBean;

    private Club club;
    private Sponsor selectedSponsor;
    private List<SelectItem> clubSponsors;
    private int selectedSponsorId;

    public void setClubBeheerBean(ClubBeheerBean clubBeheerBean) {
        this.clubBeheerBean = clubBeheerBean;
    }

    public Sponsor getSelectedSponsor() {
        return selectedSponsor;
    }

    public void setSelectedSponsor(Sponsor selectedSponsor) {
        this.selectedSponsor = selectedSponsor;
    }

    public List<SelectItem> getClubSponsors() {
        List<Sponsor> sponsors = sponsorService.getSponsorsVanExcludeVan(club, clubBeheerBean.getKampioenschap());
        clubSponsors = new ArrayList<>();
        for (Sponsor s : sponsors) {
            clubSponsors.add(new SelectItem(s.getId(), s.getNaam()));
        }
        return clubSponsors;
    }

    public void voegSelectedSponsorToe(ActionEvent event) {
        try {
            sponsorService.voegSponsorToeAan(selectedSponsorId, clubBeheerBean.getKampioenschap());
            clubBeheerBean.refresh();
        } catch (Exception ex) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage("frm_kamppagina:msg_spons", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fout", "Er liep iets mis"));
        }
    }

    public int getSelectedSponsorId() {
        return selectedSponsorId;
    }

    public void setSelectedSponsorId(int selectedSponsorId) {
        this.selectedSponsorId = selectedSponsorId;
    }

    @PostConstruct
    public void init() {
        club = clubBeheerBean.getClub();
    }
}
