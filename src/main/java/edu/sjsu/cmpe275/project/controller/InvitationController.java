package edu.sjsu.cmpe275.project.controller;

import edu.sjsu.cmpe275.project.exception.BadRequestException;
import edu.sjsu.cmpe275.project.model.*;
import edu.sjsu.cmpe275.project.service.IInvitationService;
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


@Controller
public class InvitationController {

    /**
     * Variable of type logger to print data on console
     */
    private static final Logger logger = LoggerFactory.getLogger(InvitationController.class);
    private static final String userSession = "userDetails";
    @Autowired
    IInvitationService invitationService;

    @Autowired
    IUserService userService;

    private HttpSession session;

    @RequestMapping(value = "/acceptinvitation/{id}", method = RequestMethod.POST)
    public String acceptInvitation(@PathVariable("id") String id, HttpServletRequest request, Model model) {
        try {
            session = request.getSession();
            if (session == null)
                throw new IllegalStateException("Session doesn't exist");
            invitationService.acceptInvitation(Long.valueOf(id));
            User user = (User) session.getAttribute(userSession);
            if (user != null) {
                List<Invitation> invitations = invitationService.getInvitations(user.getId());
                model.addAttribute("invitations", invitations);
                logger.info(request.getRequestURL() + ": Invitation accepted for " + user.getName());
                return "redirect:/" + Pages.invitation.toString();
            } else {
                logger.info(request.getRequestURL() + ": Accept invitation user doesn't exist");
                return "redirect:/" + Pages.login.toString();
            }
        } catch (IllegalStateException e) {
            logger.error("IllegalStateException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        } catch (NullPointerException e) {
            logger.error("NullPointerException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        }
    }

    @RequestMapping(value = "/rejectinvitation/{id}", method = RequestMethod.POST)
    public String rejectInvitation(@PathVariable("id") String id, HttpServletRequest request, Model model) {
        try {
            session = request.getSession();
            if (session == null)
                throw new IllegalStateException("Session doesn't exist");
            invitationService.rejectInvitation(Long.valueOf(id));
            User user = (User) session.getAttribute(userSession);
            if (user != null) {
                List<Invitation> invitations = invitationService.getInvitations(user.getId());
                model.addAttribute("invitations", invitations);
                logger.info(request.getRequestURL() + ": Invitation rejected for " + user.getName());
                return "redirect:/" + Pages.invitation.toString();
            } else {
                logger.info(request.getRequestURL() + ": Reject invitation user doesn't exists for " + user.getName());
                return "redirect:/" + Pages.login.toString();
            }
        } catch (IllegalStateException e) {
            logger.error("IllegalStateException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        } catch (NullPointerException e) {
            logger.error("NullPointerException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        }
    }

    @RequestMapping(value = "/sendinvitation", method = RequestMethod.GET)
    public String showUpdateTask(@RequestParam("id") String id, Invitation invitation,Project project, Model model, HttpServletRequest request) {
        logger.info("Redirecting to " + request.getRequestURL());
        List<User> users = invitationService.getNonParticipants(Long.valueOf(id));
        model.addAttribute("users", users);
        return Pages.sendinvitation.toString();
    }

    @RequestMapping(value = "/sendinvitation", method = RequestMethod.POST)
    public String updateProject(@RequestParam("id") String id,
                                @ModelAttribute(value = "invitation") Invitation invitation, HttpServletRequest request, Model model) {
        try {
            if (invitation != null) {
                invitation.setProject(invitationService.getProject(Long.valueOf(id)));
                invitation.setRequestStatus(false);
                invitationService.sendInvitation(invitation.getParticipant(), invitation.getProject()) ;
                Mail sendEmail = new Mail();
                //System.out.println("ID:" + invitation.getParticipant().getId());
                User user = userService.getUser(invitation.getParticipant().getId());
                logger.info("Sending Email to: " + user.getEmail());
                sendEmail.sendEmail(user.getEmail(),"localhost:8080/login");
                logger.info(request.getRequestURL() + ": " + "Project updated of id " + id);
                return "redirect:/" + Pages.viewproject.toString() + "?id=" + id;
            } else
                throw new BadRequestException("Required fields are missing while sending invitation", HttpStatus.BAD_REQUEST.value(), "mandatory");
        } catch (BadRequestException e) {
            logger.error("BadRequestException: " + request.getRequestURL() + ": " + e.getMessage());
            model.addAttribute("sendInvitationError", true);
            model.addAttribute("badException", e);
            return Pages.updateproject.toString();
        } catch (Exception e) {
            logger.error("Exception: " + request.getRequestURL() + ": " + e.getMessage());
            model.addAttribute("exception", e);
            return Pages.error.toString();
        }
    }
}
