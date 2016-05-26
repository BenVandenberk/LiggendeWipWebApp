package be.oklw.service;

import be.oklw.layout.Background;

import javax.ejb.Local;

@Local
public interface ILayoutService {

    void maakSiteLayoutIfNotExists();

    Background siteBackground();

    void opslaan(Background background);
}
