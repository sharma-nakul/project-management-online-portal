package edu.sjsu.cmpe275.project.service;

import edu.sjsu.cmpe275.project.model.Task;

/**
 * @author Naks
 * Interface to deliver Task services
 */
public interface ITaskService {
    long createTask(String title, String description,Task.TaskState state, int estimate, int actual);
    boolean editTask (String title, String description,Task.TaskState state, int estimate, int actual);
    boolean removeTask (long id);
    Task getTask (long id);
}
