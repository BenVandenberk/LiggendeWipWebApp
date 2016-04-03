package be.oklw.bean.bezoeker;

import be.oklw.model.Evenement;
import be.oklw.model.Kampioenschap;
import be.oklw.service.IEvenementService;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@ManagedBean
@ViewScoped
public class ScheduleBean implements Serializable {

    private ScheduleModel lazyModel;

    private ScheduleEvent event = new DefaultScheduleEvent();

    private Evenement selectedEvenement;

    @EJB
    IEvenementService evenementService;

    private List<Evenement> alleEvenementen;

    public List<Evenement> getAlleEvenementen() {
        return alleEvenementen;
    }

    @PostConstruct
    public void init() {

        alleEvenementen = evenementService.getAlleEvenementen();

        lazyModel = new LazyScheduleModel() {
            @Override
            public void loadEvents(Date start, Date end) {
                for (Evenement e : alleEvenementen) {
                    try {
                        Date st = e.getBeginDatum().getDatuminDateFormat();
                        Date ei = e.getEindDatum().getDatuminDateFormat();
                        if (((st.after(start) || st.equals(start)) && (st.before(end) || st.equals(end)))
                                || ((ei.after(start) || ei.equals(end)) && (ei.before(end) || ei.equals(end)))) {
                            if (e instanceof Kampioenschap) {
                                addEvent(new DefaultScheduleEvent(e.getNaam(), st, ei, "kamp"));
                            } else {
                                addEvent(new DefaultScheduleEvent(e.getNaam(), st, ei, "even"));
                            }
                        }
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        };
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
        for (Evenement e : alleEvenementen) {
            try {
                if (event.getTitle().equals(e.getNaam())
                        && event.getStartDate().equals(e.getBeginDatum().getDatuminDateFormat())
                        && event.getEndDate().equals(e.getEindDatum().getDatuminDateFormat())) {
                    selectedEvenement = e;
                }
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
    }

    public boolean isKampioenschap() {
        if (selectedEvenement instanceof Kampioenschap) {
            return true;
        }
        return false;
    }

    public ScheduleModel getLazyModel() {
        alleEvenementen = evenementService.getAlleEvenementen();
        return lazyModel;
    }

    public Evenement getSelectedEvenement() {
        return selectedEvenement;
    }

    public void setSelectedEvenement(Evenement selectedEvenement) {
        this.selectedEvenement = selectedEvenement;
    }
}