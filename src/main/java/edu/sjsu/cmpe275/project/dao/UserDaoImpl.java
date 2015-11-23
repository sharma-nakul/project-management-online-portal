package edu.sjsu.cmpe275.project.dao;

import edu.sjsu.cmpe275.project.model.User;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


/**
 * @author Nakul Sharma
 * Implementation class for User DAO interface
 * Repository annotation to mark the class as repository entity for a person.
 */
@Repository
public class UserDaoImpl extends AbstractDao implements IUserDao {

    /**
     * Variable of type logger to print data on console
     */
    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
    /**
     * Object to hold the current session for hibernate connection
     */
    private Session session;

    @Override
    public User addUser(String name, String email,String password){
        User user = new User(name,email,password);
        session = getSession();
        session.save(user);
        session.flush();
        logger.info(user.getName() + " added successfully");
        return user;
    }
}
