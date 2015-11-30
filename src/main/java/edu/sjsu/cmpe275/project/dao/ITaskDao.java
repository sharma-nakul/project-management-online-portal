package edu.sjsu.cmpe275.project.dao;

import edu.sjsu.cmpe275.project.model.Task;

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
   // long countAllTaskByProject(long projectId);
}