package edu.sjsu.cmpe275.project.dao;

import edu.sjsu.cmpe275.project.model.Invitation;
import edu.sjsu.cmpe275.project.model.Project;
import edu.sjsu.cmpe275.project.model.Task;

import java.util.List;

/**
 * @author Naks
 *         Project Dao interface to perform database opertaions
 */
public interface IProjectDao {
    long addProject(Project project);

    boolean updateProject(Project project);

    boolean deleteProject(Project project);

    Project getProject(long id);

    List<Invitation> getProjectParticipantList (long projectId);

    List<Task> getTaskByProjectId (long projectId);
}
