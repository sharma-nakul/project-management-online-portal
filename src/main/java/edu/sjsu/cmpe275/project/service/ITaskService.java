package edu.sjsu.cmpe275.project.service;

import edu.sjsu.cmpe275.project.model.Project;
import edu.sjsu.cmpe275.project.model.Task;
import edu.sjsu.cmpe275.project.model.User;
import edu.sjsu.cmpe275.project.model.Report;

import java.util.List;

/**
 * @author Naks
 * Interface to deliver Task services
 */
public interface ITaskService {
    long createTask(Task task);
    boolean editTask (Task task);
    boolean removeTaskById (long taskId);
    Task getTaskById (long taskId);
    long countFinishedTaskByProject(long projectId);
    long countUnfinishedTaskByProject(long projectId);
    List<User> getParticipantList(long projectId);
    Project getProject(long id);
    long countAllTaskByProject(long projectId);

    long countAllCancelledTaskByProject(long projectId);

    List<Report> getFinishedTaskOfEachUser(long projectId);
}
