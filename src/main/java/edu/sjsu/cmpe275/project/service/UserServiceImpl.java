package edu.sjsu.cmpe275.project.service;

import edu.sjsu.cmpe275.project.dao.IUserDao;
import edu.sjsu.cmpe275.project.model.Invitation;
import edu.sjsu.cmpe275.project.model.Project;
import edu.sjsu.cmpe275.project.model.Task;
import edu.sjsu.cmpe275.project.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Naks
 *         Handler class for User. The class intercept REST call to persist or retrieve data.
 *         Service annotation to mark the class as service class in application context
 *         Transactional annotation to make the class transactional entity i.e. it will be
 *         counted a single transaction
 */
@Service
public class UserServiceImpl implements IUserService {

    /**
     * Object to log the values on console.
     */
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * Autowire the User DAO interface object in this class
     */
    @Autowired
    private IUserDao userDao;


    @Transactional(value = "transManager")
    @Override
    public User createUser(String name, String email, String password) {
        User user = new User(name, email, password);
        return userDao.addUser(user);
    }

    @Transactional(value = "transManager")
    @Override
    public boolean editUser(User user) {
        User currentUser = getUser(user.getId());
        if (!user.getName().isEmpty())
            currentUser.setName(user.getName());
        if (!user.getEmail().isEmpty())
            currentUser.setEmail(user.getEmail());
        if (!user.getPassword().isEmpty())
            currentUser.setPassword(user.getPassword());
        return userDao.updateUser(currentUser);
    }

    @Transactional(value = "transManager")
    @Override
    public User getUser(long id) {
        return userDao.getUser(id);
    }

    @Transactional(value = "transManager")
    @Override
    public User verifyCredentials(String email, String password) {
        try {
            User user = this.userDao.getUserByEmailId(email);
            if (user.getPassword().equals(password))
                return user;
            else
                throw new NullPointerException("Incorrect Password!");
        } catch (NullPointerException e) {
            throw new NullPointerException("Invalid Credentials!");
        }
    }

    @Transactional(value = "transManager")
    @Override
    public List<Project> getProjectsByOwnerId(long ownerId) {
        try {
            return this.userDao.getProjectsByOwnerId(ownerId);
        } catch (NullPointerException e) {
            throw new NullPointerException("Project doesn't exists");
        }
    }

    @Transactional(value = "transManager")
    @Override
    public List<Task> getTasksByOwnerId(long ownerId) {
        try {
            return this.userDao.getTasksByOwnerId(ownerId);
        } catch (NullPointerException e) {
            throw new NullPointerException("Task doesn't exists");
        }
    }

    @Transactional(value = "transManager")
    @Override
    public List<Invitation> getUnacceptedInvitations(long ownerId) {
        try {
            List<Invitation> invitationList = userDao.getInvitationsByOwnerId(ownerId);
            List<Invitation> newInvitationList = new ArrayList<Invitation>() ;
            logger.info("invitationList Count: " + invitationList.size());
            for (Invitation in: invitationList) {
                logger.info("RequestId: " + in.getId());
                logger.info("RequestStatus: " + in.getRequestStatus());
                if (in.getRequestStatus()) {
                    newInvitationList.add(in); // This will provide only unaccepted invitations
                }
            }
            invitationList.removeAll(newInvitationList);
            logger.info("There is at least one invitation for user id " + ownerId + " is pending for the approval");
            logger.info("invitationList Count: " + invitationList.size());
            return invitationList;
        } catch (NullPointerException e) {
            throw new NullPointerException("Invitation doesn't exists");
        }
    }

    @Transactional(value = "transManager")
    @Override
    public List<Project> getParticipantProjectsById(long ownerId) {
        try {
            List<Project> projectList = new ArrayList<>();
            List<Invitation> invitationList = userDao.getInvitationsByOwnerId(ownerId);
            for (Invitation in : invitationList) {
                if (in.getRequestStatus())
                    projectList.add(in.getProject());
            }
            if (projectList.size() > 0)
                logger.info("There is at least one project available for which user " + ownerId + " is participant");
            return projectList;
        } catch (NullPointerException e) {
            throw new NullPointerException("Invitation doesn't exists");
        }
    }

}
