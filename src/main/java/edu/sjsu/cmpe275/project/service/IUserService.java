package edu.sjsu.cmpe275.project.service;

import edu.sjsu.cmpe275.project.model.Invitation;
import edu.sjsu.cmpe275.project.model.Project;
import edu.sjsu.cmpe275.project.model.Task;
import edu.sjsu.cmpe275.project.model.User;

import java.util.List;

/**
 * @author Naks
 *         Interface to deliver User services.
 */
public interface IUserService {

    User createUser(String name, String email, String password);

    boolean editUser(User user);

    boolean removeUser(long id);

    User getUser(long id);

    User verifyCredentials(String email, String password);

    List<Project> getProjectsByOwnerId(long ownerId);

    List<Task> getTasksByOwnerId(long ownerId);

    List<Invitation> getUnacceptedInvitations(long ownerId);

    List<Project> getParticipantProjectsById(long ownerId);
}
