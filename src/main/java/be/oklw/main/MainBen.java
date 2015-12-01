package be.oklw.main;

import be.oklw.model.Club;
import be.oklw.model.Evenement;
import be.oklw.model.Kampioenschap;
import be.oklw.model.Toernooi;
import be.oklw.util.Datum;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.*;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

/**
 * Created by java on 29.11.15.
 * Om in te testen
 */
public class MainBen {

    public static void main(String[] args) {
/*        Club club = new Club();
        Kampioenschap kampioenschap = new Kampioenschap(club);
        kampioenschap.setNaam("Mijn Kampioenschap");
        kampioenschap.setBeginDatum(new Datum(2, 2, 2015));
        kampioenschap.setEindDatum(new Datum(3, 2, 2015));
        kampioenschap.setLocatie("Kerkstraat 25, Aarschot");

        Toernooi toernooi = new Toernooi(kampioenschap);

        toernooi.setNaam("Mijn Toernooi");
        toernooi.setDatum(new Datum(2, 2, 2015));
        toernooi.setStartTijdstip(LocalTime.of(9, 0, 0));
        toernooi.setAantalWippen(20);
        toernooi.setMaximumAantalPloegen(40);
        toernooi.setPersonenPerPloeg(4);
        toernooi.setInlegPerPloeg(new BigDecimal(20));

        System.out.println(kampioenschap);
        System.out.println(toernooi);*/

        SessionFactory sessionFactory;
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addAnnotatedClass(Evenement.class);
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(ssrb.build());


        Evenement ev = new Evenement();
        Session sess = sessionFactory.openSession();
        sess.saveOrUpdate(ev);
        sess.close();
    }
}
