package ru.stqa.pft.mantis.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.stqa.pft.mantis.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class DBHelper {

    private SessionFactory sessionFactory;

    public DBHelper(){
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
    }

    public List<User> users(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<User> result = session.createQuery( "from User", User.class).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public List<User> simpleUsers() {
        return users().stream()
                .filter((u) -> !u.getUsername().equals("administrator"))
                .collect(Collectors.toList());
    }
}
