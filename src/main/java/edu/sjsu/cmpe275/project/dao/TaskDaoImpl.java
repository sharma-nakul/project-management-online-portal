package edu.sjsu.cmpe275.project.dao;

import edu.sjsu.cmpe275.project.model.Task;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @author Naks
 * Implementation class for Task DAO interface
 * Repository annotation to mark the class as repository entity for a task.
 */
@Repository
public class TaskDaoImpl extends AbstractDao implements ITaskDao {
    /**
     * Variable of type logger to print data on console
     */
    private static final Logger logger = LoggerFactory.getLogger(TaskDaoImpl.class);
    /**
     * Object to hold the current session for hibernate connection
     */
    private Session session;

    @Override
    public long addTask(Task Task) {
        session = getSession();
        session.save(Task);
        session.flush();
        logger.info(Task.getTitle()+ " added successfully");
        return Task.getId();
    }

    @Override
    public boolean updateTask(Task Task) {
        try {
            session = getSession();
            session.update(Task);
            session.flush();
            logger.info(Task.getTitle() + " updated successfully");
            return true;
        } catch (HibernateException e) {
            logger.info("Hibernate Exception: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteTask(Task Task) {
        try {
            session = getSession();
            session.delete(Task);
            session.flush();
            logger.info(Task.getTitle() + " deleted successfully");
            return true;
        } catch (HibernateException e) {
            logger.info("Hibernate Exception: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Task getTask(long id) {
        session = getSession();
        Task Task = (Task) session.get(Task.class, id);
        if (Task == null)
            logger.info("Returns null while retrieving the Task id " + id);
        else
            logger.info("Id " + id + " of a Task exists in database.");
        return Task;
    }
}
