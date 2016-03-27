package be.oklw.service;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

@Singleton
public class TimedService implements ITimedService {

    @EJB
    IToernooiService toernooiService;

    /**
     * Elke dag om 00:05:00 worden alle toernooistatussen geüpdate. Als de inschrijfdeadline verstreken is, verandert de status naar 'Inschrijvingen Afgesloten'
     */
    @Schedule(hour = "0", minute = "5", second = "0")
    public void elkeDag() {
        toernooiService.updateInschrijfStatusAlleToernooien();
    }
}
