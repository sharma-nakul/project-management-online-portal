package edu.sjsu.cmpe275.project.service;

import edu.sjsu.cmpe275.project.model.Project;
import edu.sjsu.cmpe275.project.model.User;

/**
 * @author Naks
 *         Interface to deliver Project Services
 */
public interface IProjectService {
    long createProject(Project project);

    boolean editProject(String title, String description, Project.ProjectState state, User owner);

    boolean removeProject(long id);

    Project getProject(long id);
}
