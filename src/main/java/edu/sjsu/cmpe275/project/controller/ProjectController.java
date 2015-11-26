package edu.sjsu.cmpe275.project.controller;

import edu.sjsu.cmpe275.project.exception.BadRequestException;
import edu.sjsu.cmpe275.project.model.Pages;
import edu.sjsu.cmpe275.project.model.Project;
import edu.sjsu.cmpe275.project.model.User;
import edu.sjsu.cmpe275.project.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Naks
 * Controller class to handle Project APIs.
 */

@Controller
public class ProjectController {

    private static final String userSession = "userDetails";
    @Autowired
    IProjectService projectService;
    private HttpSession session;

    @RequestMapping(value = "/project", method = RequestMethod.POST)
    public String createProject(
            @ModelAttribute(value = "project") Project project, HttpServletRequest request, Model model) {
        try {
            model.addAttribute("projectCreateError", false);
            if (!project.getTitle().isEmpty() && !project.getDescription().isEmpty()) {
                project.setState(Project.ProjectState.PLANNING);
                project.setOwner((User) session.getAttribute(userSession));
                long projectId = this.projectService.createProject(project);
                session = request.getSession();
                session.setAttribute("projectId", projectId);
                return Pages.home.toString();
            } else {
                if (project.getTitle().isEmpty() && project.getDescription().isEmpty())
                    throw new BadRequestException("Please provide Title and Description.", HttpStatus.BAD_REQUEST.value(), "mandatory");
                else if (project.getTitle().isEmpty())
                    throw new BadRequestException("Title is not provided.", HttpStatus.BAD_REQUEST.value(), "title");
                else
                    throw new BadRequestException("Description is not provided.", HttpStatus.BAD_REQUEST.value(), "description");
            }
        } catch (BadRequestException e) {
            model.addAttribute("projectCreateError", true);
            model.addAttribute("badException", e);
            return Pages.project.toString();
        } catch (Exception e) {
            model.addAttribute("exception", e);
            return Pages.error.toString();
        }
    }

    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public String showCreateProject(Project project, HttpServletRequest request) {
        try {
            session = request.getSession();
            User user = (User) session.getAttribute(userSession);
            if (user != null)
                return Pages.project.toString();
            else
                throw new IllegalStateException();
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: User should be logged in to create a project");
            return "redirect:/" + Pages.login.toString();
        } catch (NullPointerException e) {
            System.out.println("NullPointerException: User should be logged in to create a project");
            return "redirect:/" + Pages.login.toString();
        }
    }

}
