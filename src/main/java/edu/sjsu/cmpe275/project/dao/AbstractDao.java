package edu.sjsu.cmpe275.project.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Naks
 * Abstract class that defines method to get the Hibernate Session.
 */
public abstract class AbstractDao {

    /**
     * Session factory object an Hibernate Session
     */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Abstact method to get the current session
     * @return Current session of type Session
     */
    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

}
