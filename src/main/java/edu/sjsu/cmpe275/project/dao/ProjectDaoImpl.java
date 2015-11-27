package edu.sjsu.cmpe275.project.dao;

import edu.sjsu.cmpe275.project.model.Invitation;
import edu.sjsu.cmpe275.project.model.Project;
import edu.sjsu.cmpe275.project.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Naks
 * Implementation class for Project DAO interface
 * Repository annotation to mark the class as repository entity for a project.
 */
@Repository
public class ProjectDaoImpl extends AbstractDao implements IProjectDao{

    /**
     * Variable of type logger to print data on console
     */
    private static final Logger logger = LoggerFactory.getLogger(ProjectDaoImpl.class);
    /**
     * Object to hold the current session for hibernate connection
     */
    private Session session;

    @Override
    public long addProject(Project project){
        session = getSession();
        session.save(project);
        session.flush();
        logger.info(project.getTitle() + " added successfully");
        return project.getId();
    }

    @Override
    public boolean updateProject(Project project){
        try {
            session = getSession();
            session.update(project);
            session.flush();
            logger.info(project.getTitle() + " updated successfully");
            return true;
        }
        catch (HibernateException e)
        {
            logger.info("Hibernate Exception: " +e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteProject(Project project){
        try {
            session = getSession();
            session.delete(project);
            session.flush();
            logger.info(project.getTitle()+ " deleted successfully");
            return true;
        }
        catch (HibernateException e)
        {
            logger.info("Hibernate Exception: " +e.getMessage());
            return false;
        }
    }
    @Override
    public Project getProject(long id){
        session = getSession();
        Project project = (Project) session.get(User.class,id);
        if (project == null)
            logger.info("Returns null while retrieving the project id " + id);
        else
            logger.info("Id " + id +" of a project exists in database.");
        return project;
    }

    @Override
    public List<Project> getProjectsByOwnerId(long ownerId){
        session = getSession();
        Query getProjects = session.createQuery("from PROJECT p where p.owner.id=:id") ;
        getProjects.setParameter("id", ownerId);
        List<Project> projects = (ArrayList<Project>)getProjects.list();
        if(projects!=null)
            logger.info("There is atleast one project owned by owner id: "+ownerId);
        else
            logger.info("No project is owned by owner id: "+ownerId);
        return projects;
    }
}
