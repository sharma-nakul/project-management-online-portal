package edu.sjsu.cmpe275.project.service;

import edu.sjsu.cmpe275.project.model.Project;

/**
 * @author Naks
 *         Interface to deliver Project Services
 */
public interface IProjectService {
    long createProject(Project project);

    boolean editProject(Project project);

    boolean removeProject(long id);

    Project getProject(long id);
}
