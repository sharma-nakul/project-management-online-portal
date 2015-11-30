package edu.sjsu.cmpe275.project.service;

import edu.sjsu.cmpe275.project.model.Project;
import edu.sjsu.cmpe275.project.model.Task;
import edu.sjsu.cmpe275.project.model.User;

import java.util.List;

/**
 * @author Naks
 *         Interface to deliver Project Services
 */
public interface IProjectService {
    long createProject(Project project);

    boolean editProject(Project project);

    boolean removeProject(long id);

    Project getProject(long id);

    List<User> getParticipantList(long projectId);

    List<Task> getTaskByProjectId (long projectId);
}
