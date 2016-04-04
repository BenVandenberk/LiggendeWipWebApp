package be.oklw.service;

import javax.annotation.Resource;
import javax.ejb.*;

@Singleton
public class TimedService implements ITimedService {

    @Resource
    TimerService timerService;

    @EJB
    IToernooiService toernooiService;

    @EJB
    IContactService contactService;

    @Override
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
        contactService.verwijderOrphanContacten();
    }


}
