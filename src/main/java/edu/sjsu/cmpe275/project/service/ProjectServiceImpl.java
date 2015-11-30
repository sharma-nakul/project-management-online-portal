package edu.sjsu.cmpe275.project.service;

import edu.sjsu.cmpe275.project.dao.IProjectDao;
import edu.sjsu.cmpe275.project.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Naks
 *         Handler class for Project. The class intercept REST call to persist or retrieve data.
 *         Service annotation to mark the class as service class in application context
 *         Transactional annotation to make the class transactional entity i.e. it will be
 *         counted as a single transaction
 */
@Service
public class ProjectServiceImpl implements IProjectService {

    /**
     * Object to log the values on console.
     */
    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    /**
     * Autowire the Project DAO interface object in this class
     */
    @Autowired
    private IProjectDao projectDao;

    @Transactional(value = "transManager")
    @Override
    public long createProject(Project project) {
        return projectDao.addProject(project);
    }

    @Transactional(value = "transManager")
    @Override
    public boolean editProject(Project project) {
        return projectDao.updateProject(project);
    }

    @Transactional(value = "transManager")
    @Override
    public boolean removeProject(long id) {
        Project project = projectDao.getProject(id);
        return projectDao.deleteProject(project);
    }

    @Transactional(value = "transManager")
    @Override
    public Project getProject(long id) {
        return projectDao.getProject(id);
    }

}

