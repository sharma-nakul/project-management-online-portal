package edu.sjsu.cmpe275.project.dao;

import edu.sjsu.cmpe275.project.model.Project;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @author Naks
 *         Implementation class for Project DAO interface
 *         Repository annotation to mark the class as repository entity for a project.
 */
@Repository
public class ProjectDaoImpl extends AbstractDao implements IProjectDao {

    /**
     * Variable of type logger to print data on console
     */
    private static final Logger logger = LoggerFactory.getLogger(ProjectDaoImpl.class);
    /**
     * Object to hold the current session for hibernate connection
     */
    private Session session;

    @Override
    public long addProject(Project project) {
        session = getSession();
        session.save(project);
        session.flush();
        logger.info(project.getTitle() + " added successfully");
        return project.getId();
    }

    @Override
    public boolean updateProject(Project project) {
        try {
            Project currentProject = getProject(project.getId());
            if (!project.getTitle().isEmpty())
                currentProject.setTitle(project.getTitle());
            if (!project.getDescription().isEmpty())
                currentProject.setDescription(project.getDescription());
            if (!project.getState().toString().isEmpty())
                currentProject.setState(project.getState());
            session = getSession();
            session.update(currentProject);
            session.flush();
            logger.info(project.getTitle() + " updated successfully");
            return true;
        } catch (HibernateException e) {
            logger.info("Hibernate Exception: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteProject(Project project) {
        try {
            session = getSession();
            session.delete(project);
            session.flush();
            logger.info(project.getTitle() + " deleted successfully");
            return true;
        } catch (HibernateException e) {
            logger.info("Hibernate Exception: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Project getProject(long id) {
        session = getSession();
        Project project = (Project) session.get(Project.class, id);
        if (project == null)
            logger.info("Returns null while retrieving the project id " + id);
        else
            logger.info("Id " + id + " of a project exists in database.");
        return project;
    }

}
