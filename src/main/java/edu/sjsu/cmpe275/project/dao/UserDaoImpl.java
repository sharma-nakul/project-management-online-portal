package edu.sjsu.cmpe275.project.dao;

import edu.sjsu.cmpe275.project.model.Invitation;
import edu.sjsu.cmpe275.project.model.Project;
import edu.sjsu.cmpe275.project.model.Task;
import edu.sjsu.cmpe275.project.model.User;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Naks
 *         Implementation class for User DAO interface
 *         Repository annotation to mark the class as repository entity for a person.
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
    public User addUser(User user) {
        session = getSession();
        session.save(user);
        session.flush();
        logger.info(user.getName() + " added successfully");
        return (User) session.get(User.class, user.getId());
    }

    @Override
    public boolean updateUser(User user) {
        try {
            session = getSession();
            session.update(user);
            session.flush();
            logger.info(user.getName() + " updated successfully");
            return true;
        } catch (HibernateException e) {
            logger.info("Hibernate Exception: " + e.getMessage());
            return false;
        }
    }

    @Override
    public User getUser(long id) {
        session = getSession();
        User user = (User) session.get(User.class, id);
        if (user == null)
            logger.info("Returns null while retrieving the person of id " + id);
        else
            logger.info("Id " + id + " of a user exists in database.");
        return user;
    }

    @Override
    public User getUserByEmailId(String email) {
        session = getSession();
        Criteria userByEmail = session.createCriteria(User.class).add(Restrictions.eq("email", email));
        User user = (User) userByEmail.uniqueResult();
        return user;
    }

    @Override
    public List<Project> getProjectsByOwnerId(long ownerId) {
        User user = getUser(ownerId);
        Hibernate.initialize(user.getProjects());
        List<Project> projectList = new ArrayList<>();
        projectList.addAll(user.getProjects());
        if (projectList.size() > 0)
            logger.info("There is at least one project available for user id " + ownerId);
        return projectList;
    }

    @Override
    public List<Task> getTasksByOwnerId(long ownerId) {
        User user = getUser(ownerId);
        Hibernate.initialize(user.getTasks());
        List<Task> taskList = new ArrayList<>();
        taskList.addAll(user.getTasks());
        if (taskList.size() > 0)
            logger.info("There is at least one task available for user id " + ownerId);
        return taskList;
    }

    @Override
    public List<Invitation> getInvitationsByOwnerId(long ownerId) {
        User user = getUser(ownerId);
        Hibernate.initialize(user.getInvitations());
        List<Invitation> invitationList = new ArrayList<>();
        invitationList.addAll(user.getInvitations());
        if (invitationList.size() > 0)
            logger.info("There is at least one invitation available for user id " + ownerId);
        return invitationList;
    }
}
