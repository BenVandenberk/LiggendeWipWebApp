package be.oklw.main;

import be.oklw.model.*;
import be.oklw.model.state.Ingesteld;
import be.oklw.model.state.ToernooiStatus;
import be.oklw.util.Datum;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.math.BigDecimal;
import java.time.LocalTime;

/**
 * Created by java on 29.11.15.
 * Om in te testen
 */
public class MainBen {

    public static void main(String[] args) {
        Club club = new Club();
        club.setNaam("De Hoef");
        club.setSinds(new Datum());

        Contact contact = new Contact();
        contact.setNaam("Ben");
        contact.setEmail("emailB@h.com");
        contact.setIsBeheerder(true);
        contact.setTelefoonnummer("telll");
        club.addContact(contact);

        club.maakKampioenschap("Mijn kampioenschap", new Datum(), new Datum(12, 12, 2015));

        Kampioenschap kampioenschap = (Kampioenschap)club.getEvenementen().iterator().next();
        kampioenschap.setLocatie("Kerkstraat 25, Aarschot");


        Toernooi toernooi = new Toernooi();

        toernooi.setNaam("Mijn Toernooi");
        toernooi.setDatum(new Datum(2, 2, 2015));
        toernooi.setStartTijdstip(LocalTime.of(9, 0, 0));
        toernooi.setAantalWippen(20);
        toernooi.setMaximumAantalPloegen(40);
        toernooi.setPersonenPerPloeg(4);
        toernooi.setInlegPerPloeg(new BigDecimal(20));
        toernooi.setStatus(new Ingesteld());

        kampioenschap.addToernooi(toernooi);

        /*System.out.println(kampioenschap);
        System.out.println(toernooi);*/

        SessionFactory sessionFactory;
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addAnnotatedClass(Evenement.class);
        configuration.addAnnotatedClass(Club.class);
        configuration.addAnnotatedClass(Account.class);
        configuration.addAnnotatedClass(Kampioenschap.class);
        configuration.addAnnotatedClass(Contact.class);
        configuration.addAnnotatedClass(Deelnemer.class);
        configuration.addAnnotatedClass(Ploeg.class);
        configuration.addAnnotatedClass(Sponsor.class);
        configuration.addAnnotatedClass(SysteemAccount.class);
        configuration.addAnnotatedClass(Toernooi.class);
        configuration.addAnnotatedClass(ToernooiStatus.class);
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(ssrb.build());




        Session sess = sessionFactory.openSession();

        sess.saveOrUpdate(club);

        sess.close();

        Club gefetchteClub;
        Toernooi gefetchtToernooi;

        sess = sessionFactory.openSession();

        gefetchteClub = sess.get(Club.class, 1);
        gefetchtToernooi = sess.get(Toernooi.class, 1);

        sess.close();

        System.out.println(gefetchteClub);
        System.out.println(gefetchtToernooi);
        System.out.println(gefetchtToernooi.getStatus());


    }
}
