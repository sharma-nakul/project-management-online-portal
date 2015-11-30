package edu.sjsu.cmpe275.project.dao;

import edu.sjsu.cmpe275.project.model.Invitation;
import edu.sjsu.cmpe275.project.model.Project;
import edu.sjsu.cmpe275.project.model.Task;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Invitation> getProjectParticipantList (long projectId){
        session = getSession();
        Criteria criteria=session.createCriteria(Invitation.class);
        criteria.add(Restrictions.eq("project.id", projectId));
        criteria.add(Restrictions.eq("requestStatus", true));
        List<Invitation> acceptedProjectInvitation=(ArrayList<Invitation>) criteria.list();
        if (acceptedProjectInvitation.size() > 0)
            logger.info("Database returned participant list of project id" + projectId);
        return acceptedProjectInvitation;
    }

    @Override
    public List<Task> getTaskByProjectId(long projectId) {
        session = getSession();
        Criteria taskByProjectId = session.createCriteria(Task.class).add(Restrictions.eq("project.id", projectId));
        List<Task> taskListOfProject = (ArrayList<Task>) taskByProjectId.list();
        if (taskListOfProject.size() > 0)
            logger.info("There is at least one task available for project id " + projectId);
        else
            logger.info("Task list is empty for project id " + projectId);
        return taskListOfProject;
    }

}
