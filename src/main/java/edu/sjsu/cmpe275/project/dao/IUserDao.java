package edu.sjsu.cmpe275.project.dao;

import edu.sjsu.cmpe275.project.model.Invitation;
import edu.sjsu.cmpe275.project.model.Project;
import edu.sjsu.cmpe275.project.model.Task;
import edu.sjsu.cmpe275.project.model.User;

import java.util.List;

/**
 * @author Naks
 * User DAO to perform database operation.
 */
public interface IUserDao {
    User addUser(User user);
    boolean updateUser (User user);
    boolean deleteUser (User user);
    User getUser (long id);
    User getUserByEmailId(String email);
    List<Project> getProjectsByOwnerId(long ownerId);
    List<Task> getTasksByOwnerId (long ownerId);
    List<Invitation> getInvitationsByOwnerId (long ownerId);
}
