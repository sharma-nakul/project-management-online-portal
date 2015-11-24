package edu.sjsu.cmpe275.project.dao;

import edu.sjsu.cmpe275.project.model.Task;

/**
 * @author Naks
 * Task Dao interface to perform database operations
 */
public interface ITaskDao {
    long addTask(Task task);
    boolean updateTask (Task Task);
    boolean deleteTask (Task Task);
    Task getTask (long id);
}