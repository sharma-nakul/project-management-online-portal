package edu.sjsu.cmpe275.project.dao;

import edu.sjsu.cmpe275.project.model.User;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


/**
 * @author Naks
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
    public User addUser(User user){
        session = getSession();
        session.save(user);
        session.flush();
        logger.info(user.getName() + " added successfully");
        User refreshUser=(User)session.get(User.class,user.getId());
        return refreshUser;
    }

    @Override
    public boolean updateUser(User user){
        try {
            session = getSession();
            session.update(user);
            session.flush();
            logger.info(user.getName() + " updated successfully");
            return true;
        }
        catch (HibernateException e)
        {
            logger.info("Hibernate Exception: " +e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteUser(User user){
        try {
            session = getSession();
            session.delete(user);
            session.flush();
            logger.info(user.getName() + " deleted successfully");
            return true;
        }
        catch (HibernateException e)
        {
            logger.info("Hibernate Exception: " +e.getMessage());
            return false;
        }
    }
    @Override
    public User getUser(long id){
            session = getSession();
            User user = (User) session.get(User.class, id);
            if (user == null)
                logger.info("Returns null while retrieving the person of id " + id);
            else
                logger.info("Id " + id +" of a user exists in database.");
            return user;
    }

    @Override
    public  User getUserByEmailId(String email){
        session=getSession();
        Query userByEmail = session.createQuery("from USER u where u.email=:email") ;
        userByEmail.setParameter("email",email);
        User user = null;
        user=(User) userByEmail.uniqueResult();
        return user;
    }
}
