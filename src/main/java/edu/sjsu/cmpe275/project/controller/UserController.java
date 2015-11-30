package edu.sjsu.cmpe275.project.controller;

import edu.sjsu.cmpe275.project.exception.BadRequestException;
import edu.sjsu.cmpe275.project.model.*;
import edu.sjsu.cmpe275.project.model.Project;
import edu.sjsu.cmpe275.project.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Naks
 *         Controller class to handle User APIs.
 */
@Controller
public class UserController {

    private static final String userSession = "userDetails";

    /**
     * Variable of type logger to print data on console
     */
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    IUserService userService;
    private HttpSession session = null;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLogin(User user, HttpServletRequest request) {
        session = request.getSession();
        logger.info("Redirecting to " + request.getRequestURL());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute(value = "user") User user, HttpServletRequest request, Model model) {
        try {
            session = request.getSession();
            if (!user.getEmail().isEmpty() && !user.getPassword().isEmpty()) {
                user = this.userService.verifyCredentials(user.getEmail(), user.getPassword());
                session.setAttribute(userSession, user);
                logger.info(request.getRequestURL() + ": " + "Login successful for " + user.getName());
                return Pages.home.toString();
            } else {
                if (user.getEmail().isEmpty())
                    throw new NullPointerException("Email id is empty");
                else
                    throw new NullPointerException("Password is empty");
            }
        } catch (NullPointerException e) {
            logger.error("NullPointerException: " + request.getRequestURL() + ": " + e.getMessage());
            model.addAttribute("user", new User());
            model.addAttribute("loginError", true);
            model.addAttribute("credentials", e);
            return Pages.login.toString();
        } catch (Exception e) {
            logger.error("Exception: " + request.getRequestURL() + ": " + e.getMessage());
            model.addAttribute("exception", e);
            return Pages.error.toString();
        }
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String showSignUp(User user, HttpServletRequest request) {
        logger.info("Redirecting to " + request.getRequestURL());
        return Pages.signup.toString();
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signUp(
            @ModelAttribute(value = "user") User user, HttpServletRequest request, Model model) {
        try {
            if (!user.getName().isEmpty() && !user.getEmail().isEmpty() && !user.getPassword().isEmpty()) {
                user = this.userService.createUser(user.getName(), user.getEmail(), user.getPassword());
                session = request.getSession();
                session.setAttribute(userSession, user);
                logger.info(request.getRequestURL() + ": " + "Signup successful for " + user.getName());
                return Pages.home.toString();
            } else
                throw new BadRequestException("Required fields are missing", HttpStatus.BAD_REQUEST.value(), "mandatory");
        } catch (BadRequestException e) {
            logger.error("BadRequestException: " + request.getRequestURL() + ": " + e.getMessage());
            model.addAttribute("signupError", true);
            model.addAttribute("badException", e);
            return Pages.signup.toString();
        } catch (Exception e) {
            logger.error("Exception: " + request.getRequestURL() + ": " + e.getMessage());
            model.addAttribute("exception", e);
            return Pages.error.toString();
        }
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logout(User user, Model model, HttpServletRequest request) {
        try {
            session = request.getSession();
            if (session == null)
                throw new IllegalStateException("Session doesn't exist");
            session.invalidate();
            return Pages.login.toString();
        } catch (NullPointerException e) {
            logger.error("NullPointerException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        } catch (IllegalStateException e) {
            logger.error("IllegalStateException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        }
    }

    @RequestMapping(value="/user", method = RequestMethod.GET)
    public String getUser(Model model, HttpServletRequest request) {
        try {
            session = request.getSession();
            if (session == null)
                throw new IllegalStateException("Session doesn't exist");
            User user = (User) session.getAttribute(userSession);
            if (user != null) {
                model.addAttribute("user", user);
                List<Project> ownedProjectList = userService.getProjectsByOwnerId(user.getId());
                List<Project> participantProjectList = userService.getParticipantProjectsById(user.getId());
                model.addAttribute("ownerProjects", ownedProjectList);
                model.addAttribute("participantProjects", participantProjectList);
                List<Task> taskList = userService.getTasksByOwnerId(user.getId());
                model.addAttribute("ownedTasks", taskList);
                return Pages.user.toString();
            } else
                throw new IllegalStateException("Session doesn't exist");
        } catch (NullPointerException e) {
            logger.error("NullPointerException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        } catch (IllegalStateException e) {
            logger.error("IllegalStateException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        }
    }

    @RequestMapping(value="/home", method = RequestMethod.GET)
    public String getHome(Model model, HttpServletRequest request) {
        try {
            session = request.getSession();
            if (session == null)
                throw new IllegalStateException("Session doesn't exist");
            User user = (User) session.getAttribute(userSession);
            if (user != null) {
                return Pages.home.toString();
            } else
                throw new IllegalStateException("Session doesn't exist");
        } catch (NullPointerException e) {
            logger.error("NullPointerException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        } catch (IllegalStateException e) {
            logger.error("IllegalStateException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        }
    }

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public String allProjects(HttpServletRequest request, Model model) {
        try {
            session = request.getSession();
            if (session == null)
                throw new IllegalStateException("Session doesn't exist");
            User user = (User) session.getAttribute(userSession);
            if (user != null) {
                List<Project> ownedProjectList = userService.getProjectsByOwnerId(user.getId());
                List<Project> participantProjectList = userService.getParticipantProjectsById(user.getId());
                model.addAttribute("ownerProjects", ownedProjectList);
                model.addAttribute("participantProjects", participantProjectList);
                logger.info(request.getRequestURL() + ": project list returned for " + user.getName());
                return Pages.projects.toString();
            } else
                throw new IllegalStateException("Session doesn't exist");
        } catch (NullPointerException e) {
            logger.error("NullPointerException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        } catch (IllegalStateException e) {
            logger.error("IllegalStateException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        }
    }

    @RequestMapping(value = "/viewreport", method = RequestMethod.GET)
    public String viewReport(Project project, Model model, HttpServletRequest request) {
         logger.info("Redirecting to " + request.getRequestURL());
        try {
            session = request.getSession();
            if (session == null)
                throw new IllegalStateException("Session doesn't exist");
            User user = (User) session.getAttribute(userSession);
            if (user != null) {
                List<Project> projectList = userService.getProjectsByOwnerId(user.getId());
                projectList.addAll(userService.getParticipantProjectsById(user.getId()));
                model.addAttribute("projectList", projectList);
                logger.info(request.getRequestURL() + ": project list returned for " + user.getName());
                return Pages.viewreport.toString();
            } else
                throw new IllegalStateException("Session doesn't exist");
        } catch (NullPointerException e) {
            logger.error("NullPointerException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        } catch (IllegalStateException e) {
            logger.error("IllegalStateException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        }
    }
    @RequestMapping(value = "/viewreport", method = RequestMethod.POST)
    public String sendViewReport(
                                @ModelAttribute(value = "project") Project project, HttpServletRequest request, Model model) {
        try {
            if (project != null) {
                return "redirect:/" + Pages.reports.toString() + "?id=" + project.getId();
            } else
                throw new BadRequestException("Required fields are missing while sending invitation", HttpStatus.BAD_REQUEST.value(), "mandatory");
        } catch (BadRequestException e) {
            logger.error("BadRequestException: " + request.getRequestURL() + ": " + e.getMessage());
            model.addAttribute("badException", e);
            return Pages.updateproject.toString();
        } catch (Exception e) {
            logger.error("Exception: " + request.getRequestURL() + ": " + e.getMessage());
            model.addAttribute("exception", e);
            return Pages.error.toString();
        }
    }

    @RequestMapping(value = "/updateuser", method = RequestMethod.GET)
    public String showUpdateUser(Model model, User user, HttpServletRequest request) {
        try {
            session = request.getSession();
            if (session == null)
                throw new IllegalStateException("Session doesn't exist");
            User loggedinUser = (User) session.getAttribute(userSession);
            if (loggedinUser != null) {
                return Pages.updateuser.toString();
            } else
                throw new IllegalStateException("Session doesn't exist");
        } catch (NullPointerException e) {
            logger.error("NullPointerException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        } catch (IllegalStateException e) {
            logger.error("IllegalStateException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        }
    }

    @RequestMapping(value = "/updateuser", method = RequestMethod.POST)
    public String updateUser(
            @ModelAttribute(value = "user") User user, HttpServletRequest request, Model model) {
        try {
            boolean status;
            session = request.getSession();
            User currentUser=(User) session.getAttribute(userSession);
            user.setId(currentUser.getId());
            status = this.userService.editUser(user);
            if (status) {
                session.setAttribute(userSession, user);
                logger.info(request.getRequestURL() + ": " + "Information update successful for " + user.getName());
                return "redirect:/" + Pages.user.toString();
            } else
                throw new BadRequestException("Cannot update user");
        } catch (BadRequestException e) {
            logger.error("BadRequestException: " + request.getRequestURL() + ": " + e.getMessage());
            model.addAttribute("updateError", true);
            model.addAttribute("badException", e);
            return Pages.updateuser.toString();
        } catch (Exception e) {
            logger.error("Exception: " + request.getRequestURL() + ": " + e.getMessage());
            model.addAttribute("exception", e);
            return Pages.error.toString();
        }
    }


    /*@RequestMapping(value = "/owned_projects", method = RequestMethod.GET)
    public String getOwnedProjects(HttpServletRequest request, Model model) {
        try {
            session = request.getSession();
            if (session == null)
                throw new IllegalStateException("Session doesn't exist");
            User user = (User) session.getAttribute(userSession);
            if (user != null) {
                List<Project> projectList = userService.getProjectsByOwnerId(user.getId());
                model.addAttribute("ownerProjects", projectList);
                logger.info(request.getRequestURL() + ": Owned project list returned for " + user.getName());
                return Pages.home.toString();
            } else
                throw new IllegalStateException("Session doesn't exist");
        } catch (NullPointerException e) {
            logger.error("NullPointerException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        } catch (IllegalStateException e) {
            logger.error("IllegalStateException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        }
    }

    @RequestMapping(value = "/participant_projects", method = RequestMethod.GET)
    public String getParticipantProjects(HttpServletRequest request, Model model) {
        try {
            session = request.getSession();
            if (session == null)
                throw new IllegalStateException("Session doesn't exist");
            User user = (User) session.getAttribute(userSession);
            if (user != null) {
                List<Project> projectList = userService.getParticipantProjectsById(user.getId());
                model.addAttribute("participant_projects", projectList);
                logger.info(request.getRequestURL() + ": Participant project list returned for " + user.getName());
                return Pages.home.toString();
            } else
                throw new IllegalStateException("Session doesn't exist");
        } catch (NullPointerException e) {
            logger.error("NullPointerException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        } catch (IllegalStateException e) {
            logger.error("IllegalStateException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        }
    }

    @RequestMapping(value = "/owned_tasks", method = RequestMethod.GET)
    public String getOwnedTask(HttpServletRequest request, Model model) {
        try {
            session = request.getSession();
            if (session == null)
                throw new IllegalStateException("Session doesn't exist");
            User user = (User) session.getAttribute(userSession);
            if (user != null) {
                List<Task> taskList = userService.getTasksByOwnerId(user.getId());
                model.addAttribute("owned_tasks", taskList);
                logger.info(request.getRequestURL() + ": Owned task list returned for " + user.getName());
                return Pages.home.toString();
            } else
                throw new IllegalStateException("Session doesn't exist");
        } catch (NullPointerException e) {
            logger.error("NullPointerException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        } catch (IllegalStateException e) {
            logger.error("IllegalStateException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        }
    }  */

    @RequestMapping(value = "/invitation", method = RequestMethod.GET)
    public String getPendingInvitation(HttpServletRequest request, Model model) {
        try {
            session = request.getSession();
            if (session == null)
                throw new IllegalStateException("Session doesn't exist");
            User user = (User) session.getAttribute(userSession);
            if (user != null) {
                List<Invitation> unacceptedInvitations = userService.getUnacceptedInvitations(user.getId());
                model.addAttribute("invitations", unacceptedInvitations);
                logger.info(request.getRequestURL() + ": Pending invitation(s) list returned for " + user.getName());
                return Pages.invitation.toString();
            } else
                throw new IllegalStateException("Session doesn't exist");
        } catch (NullPointerException e) {
            logger.error("NullPointerException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        } catch (IllegalStateException e) {
            logger.error("IllegalStateException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        }
    }


}
