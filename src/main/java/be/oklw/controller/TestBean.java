package be.oklw.controller;

import be.oklw.model.Evenement;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(eager = true)
@ApplicationScoped
public class TestBean {

    @PostConstruct
    public void init() {
        SessionFactory sessionFactory;
        Configuration configuration = new Configuration();
        configuration.configure();
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(ssrb.build());

        Evenement ev = new Evenement();
        Session sess = sessionFactory.openSession();
        sess.saveOrUpdate(ev);
        sess.close();
    }
}
