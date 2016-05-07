package be.oklw.service;

import be.oklw.model.PaswoordResetInfo;

import javax.ejb.Local;

@Local
public interface IPaswoordTimerService {

    void createResetLinkExpirationTimer(long duration, PaswoordResetInfo paswoordResetInfo);
}
