package be.oklw.service;

import javax.annotation.Resource;
import javax.ejb.*;

@Singleton
public class TimedService implements ITimedService {

    @Resource
    TimerService timerService;

    @EJB
    IToernooiService toernooiService;

    // Roep deze method aan wanneer je een contact opslaagt
    public void createTimer(long duration) {
        timerService.createTimer(duration, "Timer contacten gestart");
    }

    /**
     * Elke dag om 00:05:00 worden alle toernooistatussen geüpdate. Als de inschrijfdeadline verstreken is, verandert de status naar 'Inschrijvingen Afgesloten'
     */
    @Schedule(hour = "0", minute = "5", second = "0")
    public void elkeDag() {
        toernooiService.updateInschrijfStatusAlleToernooien();
    }

    @Timeout
    private void orphanContactenVerwijderen() {
        // Injecteer de service die je nodig hebt bovenaan en roep hier de method aan die de oprhan contacten verwijderd
    }


}
