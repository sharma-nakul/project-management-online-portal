package edu.sjsu.cmpe275.project.dao;

import edu.sjsu.cmpe275.project.model.Invitation;
import edu.sjsu.cmpe275.project.model.Project;
import edu.sjsu.cmpe275.project.model.Task;
import edu.sjsu.cmpe275.project.model.Report;

import java.util.List;

/**
 * @author Naks
 * Task Dao interface to perform database operations
 */
public interface ITaskDao {
    long addTask(Task task);
    boolean updateTask (Task Task);
    boolean deleteTaskById (long taskId);
    Task getTaskById (long taskId);
    long countFinishedTaskByProject(long projectId);
    long countUnfinishedTaskByProject(long projectId);
    List<Invitation> getProjectParticipantList (long projectId);
    Project getProject(long id);
    long countAllTaskByProject(long projectId);

    long countAllCancelledTaskByProject(long projectId);

    List<Report> getFinishedTaskOfEachUser(long projectId);
}