package edu.sjsu.cmpe275.project.service;

import edu.sjsu.cmpe275.project.dao.ITaskDao;
import edu.sjsu.cmpe275.project.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Naks
 *         Handler class for Task. The class intercept REST call to persist or retrieve data.
 *         Service annotation to mark the class as service class in application context
 *         Transactional annotation to make the class transactional entity i.e. it will be
 *         counted as a single transaction
 */
public class TaskServiceImpl implements ITaskService{
    /**
     * Object to log the values on console.
     */
    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    /**
     * Autowire the Project DAO interface object in this class
     */
    @Autowired
    private ITaskDao taskDao;

    @Transactional(value = "transManager")
    @Override
    public long createTask(Task task){
        return taskDao.addTask(task);
    }

    @Transactional(value = "transManager")
    @Override
    public boolean editTask (Task task){
        Task currentTaskInfo=getTaskById(task.getId());
        if(!task.getTitle().isEmpty())
            currentTaskInfo.setTitle(task.getTitle());
        if(!task.getDescription().isEmpty())
            currentTaskInfo.setDescription(task.getDescription());
        if(task.getActual()!=currentTaskInfo.getActual())
            currentTaskInfo.setActual(task.getActual());
        if(task.getEstimate()!=currentTaskInfo.getEstimate())
            currentTaskInfo.setEstimate(task.getEstimate());
        if(task.getAssignee().getId()!=currentTaskInfo.getAssignee().getId())
            currentTaskInfo.setAssignee(task.getAssignee());
        return taskDao.updateTask(task);
    }

    @Transactional(value = "transManager")
    @Override
    public boolean removeTaskById (long taskId){
        return taskDao.deleteTaskById(taskId);
    }

    @Transactional(value = "transManager")
    @Override
    public List<Task> getTaskByProjectId (long projectId){
        return taskDao.getTaskByProjectId(projectId);
    }

    @Transactional(value = "transManager")
    @Override
    public Task getTaskById (long taskId){
        return taskDao.getTaskById(taskId);
    }

}
