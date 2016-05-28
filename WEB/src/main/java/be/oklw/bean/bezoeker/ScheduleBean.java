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
                        // Halve dag bijtellen zodat schedule bij een meerdaags evenement de verschillende dagen inkleurt
                        st.setTime(st.getTime() + 43200000);
                        Date ei = e.getEindDatum().getDatuminDateFormat();
                        ei.setTime(ei.getTime() + 43200000);
                        if (((st.after(start) || st.equals(start)) && (st.before(end) || st.equals(end)))
                                || ((ei.after(start) || ei.equals(end)) && (ei.before(end) || ei.equals(end)))) {
                            DefaultScheduleEvent defaultScheduleEvent;
                            if (e instanceof Kampioenschap) {
                                defaultScheduleEvent = new DefaultScheduleEvent(e.getNaam(), st, ei, "kamp");
                            } else {
                                defaultScheduleEvent = new DefaultScheduleEvent(e.getNaam(), st, ei, "even");
                            }
                            defaultScheduleEvent.setData(e.getId());
                            addEvent(defaultScheduleEvent);
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
        int eventId = (Integer) event.getData();

        for (Evenement e : alleEvenementen) {
            if (eventId == e.getId()) {
                selectedEvenement = e;
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