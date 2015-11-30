package edu.sjsu.cmpe275.project.service;

import edu.sjsu.cmpe275.project.model.Task;

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
}
