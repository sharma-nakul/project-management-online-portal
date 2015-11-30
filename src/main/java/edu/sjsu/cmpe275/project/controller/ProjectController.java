package edu.sjsu.cmpe275.project.controller;

import edu.sjsu.cmpe275.project.exception.BadRequestException;
import edu.sjsu.cmpe275.project.model.*;
import edu.sjsu.cmpe275.project.service.IInvitationService;
import edu.sjsu.cmpe275.project.service.IProjectService;
import edu.sjsu.cmpe275.project.service.ITaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Naks
 *         Controller class to handle Project APIs.
 */

@Controller
public class ProjectController {

    /**
     * Variable of type logger to print data on console
     */
    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    private static final String userSession = "userDetails";
    @Autowired
    IProjectService projectService;

    private HttpSession session;

    @RequestMapping(value = "/project", method = RequestMethod.POST)
    public String createProject(
            @ModelAttribute(value = "project") Project project, HttpServletRequest request, Model model) {
        try {
            session = request.getSession();
            if (session == null)
                throw new IllegalStateException("Session doesn't exist");
            model.addAttribute("projectCreateError", false);
            if (!project.getTitle().isEmpty() && !project.getDescription().isEmpty()) {
                project.setState(Project.ProjectState.PLANNING);
                project.setOwner((User) session.getAttribute(userSession));
                long projectId = this.projectService.createProject(project);
                session.setAttribute("projectId", projectId);
                logger.info(request.getRequestURL()+": "+"Project created successfully having project id "+projectId);
                return "redirect:/" + Pages.projects.toString();
            } else {
                if (project.getTitle().isEmpty() && project.getDescription().isEmpty())
                    throw new BadRequestException("Please provide Title and Description.", HttpStatus.BAD_REQUEST.value(), "mandatory");
                else if (project.getTitle().isEmpty())
                    throw new BadRequestException("Title is not provided.", HttpStatus.BAD_REQUEST.value(), "title");
                else
                    throw new BadRequestException("Description is not provided.", HttpStatus.BAD_REQUEST.value(), "description");
            }
        } catch (BadRequestException e) {
            logger.error("BadRequestException: " + request.getRequestURL() + ": " + e.getMessage());
            model.addAttribute("projectCreateError", true);
            model.addAttribute("badException", e);
            return Pages.project.toString();
        } catch (IllegalStateException e) {
            logger.error("IllegalStateException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        } catch (Exception e) {
            logger.error("Exception:" + request.getRequestURL() + ": " + e.getMessage());
            model.addAttribute("exception", e);
            return Pages.error.toString();
        }
    }

    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public String showCreateProject(Project project, HttpServletRequest request) {
        String message = "User should be logged in to create a project";
        try {
            session = request.getSession();
            if (session == null)
                throw new IllegalStateException("Session doesn't exist");
            User user = (User) session.getAttribute(userSession);
            if (user != null){
                logger.info("Redirecting to "+request.getRequestURL());
                return Pages.project.toString();}
            else
                throw new IllegalStateException(message);
        } catch (IllegalStateException e) {
            logger.error("IllegalStateException: "+request.getRequestURL()+": "+e.getMessage());
            return "redirect:/" + Pages.login.toString();
        } catch (NullPointerException e) {
            logger.error("NullPointerException: " + request.getRequestURL() + ": " + message);
            return "redirect:/" + Pages.login.toString();
        }
    }

    @RequestMapping(value = "/viewproject", method = RequestMethod.GET)
    public String viewProject(@RequestParam("id") String id, Model model, HttpServletRequest request) {
        String message = "User should be logged in to view a project";
        try {
            session = request.getSession();
            if (session == null)
                throw new IllegalStateException("Session doesn't exist");
            User user = (User) session.getAttribute(userSession);
            if (user != null){
                Project project = projectService.getProject(Long.valueOf(id));
                model.addAttribute("project", project);
                List<User> participantList = projectService.getParticipantList(Long.valueOf(id));
                model.addAttribute("partcipants", participantList);
                List<Task> taskList = projectService.getTaskByProjectId(Long.valueOf(id));
                model.addAttribute("tasks", taskList);
                logger.info("Redirecting to "+request.getRequestURL());
                return Pages.viewproject.toString();}
            else
                throw new IllegalStateException(message);
        } catch (IllegalStateException e) {
            logger.error("IllegalStateException: "+request.getRequestURL()+": "+e.getMessage());
            return "redirect:/" + Pages.login.toString();
        } catch (NullPointerException e) {
            logger.error("NullPointerException: "+request.getRequestURL()+": "+message);
            return "redirect:/" + Pages.login.toString();
        }
    }

    @RequestMapping(value = "/updateproject", method = RequestMethod.GET)
    public String showUpdateProject(@RequestParam("id") String id, Project project, HttpServletRequest request) {
        logger.info("Redirecting to " + request.getRequestURL());
        return Pages.updateproject.toString();
    }

    @RequestMapping(value = "/updateproject", method = RequestMethod.POST)
    public String updateProject(@RequestParam("id") String id,
                             @ModelAttribute(value = "project") Project project, HttpServletRequest request, Model model) {
        try {
            if (project != null) {
                project.setId(Long.valueOf(id));
                boolean status = projectService.editProject(project);
                if (status) {
                    logger.info(request.getRequestURL() + ": " + "Project updated of id " + id);
                    return "redirect:/" + Pages.viewproject.toString() + "?id=" + id;
                } else
                    throw new BadRequestException("Error updating project");
            } else
                throw new BadRequestException("Required fields are missing while updating project", HttpStatus.BAD_REQUEST.value(), "mandatory");
        } catch (BadRequestException e) {
            logger.error("BadRequestException: " + request.getRequestURL() + ": " + e.getMessage());
            model.addAttribute("projectUpdateError", true);
            model.addAttribute("badException", e);
            return Pages.updateproject.toString();
        } catch (Exception e) {
            logger.error("Exception: " + request.getRequestURL() + ": " + e.getMessage());
            model.addAttribute("exception", e);
            return Pages.error.toString();
        }
    }

}
