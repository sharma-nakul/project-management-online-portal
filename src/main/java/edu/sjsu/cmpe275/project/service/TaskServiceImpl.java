package edu.sjsu.cmpe275.project.service;

import edu.sjsu.cmpe275.project.dao.ITaskDao;
import edu.sjsu.cmpe275.project.model.Report;
import edu.sjsu.cmpe275.project.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Naks
 *         Handler class for Task. The class intercept REST call to persist or retrieve data.
 *         Service annotation to mark the class as service class in application context
 *         Transactional annotation to make the class transactional entity i.e. it will be
 *         counted as a single transaction
 */
@Service
public class TaskServiceImpl implements ITaskService {
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
    public long createTask(Task task) {
        return taskDao.addTask(task);
    }

    @Transactional(value = "transManager")
    @Override
    public boolean editTask(Task task) {
        return taskDao.updateTask(task);
    }

    @Transactional(value = "transManager")
    @Override
    public boolean removeTaskById(long taskId) {
        return taskDao.deleteTaskById(taskId);
    }

    @Transactional(value = "transManager")
    @Override
    public Task getTaskById(long taskId) {
        return taskDao.getTaskById(taskId);
    }

    @Transactional(value = "transManager")
    @Override
    public long countFinishedTaskByProject(long projectId){
        return taskDao.countFinishedTaskByProject(projectId);
    }

    @Transactional(value = "transManager")
    @Override
    public long countUnfinishedTaskByProject(long projectId){
        return taskDao.countUnfinishedTaskByProject(projectId);
    }

    @Transactional(value = "transManager")
    @Override
    public long countAllTaskByProject(long projectId){
        return taskDao.countAllTaskByProject(projectId);
    }

    @Transactional(value = "transManager")
    @Override
    public long countAllCancelledTaskByProject(long projectId){
        return taskDao.countAllCancelledTaskByProject(projectId);
    }

    @Transactional(value = "transManager")
    @Override
    public List<Report> getFinishedTaskOfEachUser (long projectId){
        return taskDao.getFinishedTaskOfEachUser(projectId);
    }

}
