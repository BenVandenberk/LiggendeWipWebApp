package be.oklw.service;

import javax.ejb.Local;

@Local
public interface ITimedService {

    void createTimer(long duration);

}
