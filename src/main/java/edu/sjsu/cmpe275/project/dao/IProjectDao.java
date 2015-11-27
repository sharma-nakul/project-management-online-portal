package edu.sjsu.cmpe275.project.dao;

import edu.sjsu.cmpe275.project.model.Project;

import java.util.List;

/**
 * @author Naks
 * Project Dao interface to perform database opertaions
 */
public interface IProjectDao {
    long addProject(Project project);
    boolean updateProject (Project project);
    boolean deleteProject (Project project);
    Project getProject (long id);
    List<Project> getProjectsByOwnerId (long ownerId);
}
