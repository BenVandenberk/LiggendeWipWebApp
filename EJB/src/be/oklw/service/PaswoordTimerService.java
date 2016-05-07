package be.oklw.service;

import be.oklw.model.PaswoordResetInfo;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
public class PaswoordTimerService implements IPaswoordTimerService {

    @Resource
    TimerService timerService;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void createResetLinkExpirationTimer(long duration, PaswoordResetInfo paswoordResetInfo) {
        timerService.createTimer(duration, paswoordResetInfo);
    }

    @Timeout
    public void verwijderPaswoordResetInfo(Timer timer) {
        PaswoordResetInfo paswoordResetInfo = (PaswoordResetInfo) timer.getInfo();
        entityManager.createQuery("delete from PaswoordResetInfo p where p.id=:id")
                .setParameter("id", paswoordResetInfo.getId())
                .executeUpdate();
    }
}
