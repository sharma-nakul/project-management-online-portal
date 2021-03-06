package edu.sjsu.cmpe275.project.dao;

import edu.sjsu.cmpe275.project.model.Invitation;
import java.util.List;

import java.util.ArrayList;

import edu.sjsu.cmpe275.project.model.Project;
import edu.sjsu.cmpe275.project.model.Task;
import edu.sjsu.cmpe275.project.model.User;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @author Naks
 * Implementation class for Invitation DAO interface
 * Repository annotation to mark the class as repository entity for an invitation.
 */
@Repository
public class InvitationDaoImpl extends AbstractDao implements IInvitationDao{
    /**
     * Variable of type logger to print data on console
     */
    private static final Logger logger = LoggerFactory.getLogger(InvitationDaoImpl.class);
    /**
     * Object to hold the current session for hibernate connection
     */
    private Session session;

    @Override
    public boolean updateInvitation (Invitation invitation){
        try {
            session = getSession();
            session.update(invitation);
            session.flush();
            logger.info("Invitation number "+invitation.getId() + " status updated successfully");
            return true;
        }
        catch (HibernateException e)
        {
            logger.info("Hibernate Exception: " +e.getMessage());
            return false;
        }
    }

    @Override
    public Invitation getInvitation (long id){
        session = getSession();
        Invitation invitation = (Invitation) session.get(Invitation.class,id);
        if (invitation == null)
            logger.info("Returns null while retrieving the invitation of id " + id);
        else
            logger.info("Id " + id +" of an invitation exists in database.");
        return invitation;
    }

    @Override
    public long saveInvitation (Invitation invitation) {
        session = getSession();
        session.save(invitation);
        session.flush();
        logger.info("Invitation added successfully");
        return invitation.getId();
    }

    @Override
    public List<Invitation> getInvitations (long id) {
        session = getSession();
        Query getInvitations = session.createQuery("from INVITATION i where i.participant.id=:id and i.requestStatus = false") ;
        getInvitations.setParameter("id", id);
        List<Invitation> invitations = null;
        invitations = (ArrayList<Invitation>) getInvitations.list();
        return invitations;
    }

    @Override
    public boolean removeInvitation (Invitation invitation){
        try {
            session = getSession();
            session.delete(invitation);
            session.flush();
            logger.info("Invitation deleted successfully");
            return true;
        } catch (HibernateException e) {
            logger.info("Hibernate Exception: " + e.getMessage());
            return false;
        }
    }


    @Override
    public List<User> getAllUser() {
        session = getSession();
        List<User> userList=(ArrayList<User>) session.createCriteria(User.class).list();
        return userList;
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
}
