package edu.sjsu.cmpe275.project.controller;

import edu.sjsu.cmpe275.project.model.Invitation;
import edu.sjsu.cmpe275.project.model.Pages;
import edu.sjsu.cmpe275.project.model.User;
import edu.sjsu.cmpe275.project.service.IInvitationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    private HttpSession session;

    @RequestMapping(value = "/invitation", method = RequestMethod.GET)
    public String showInvitation(Model model, HttpServletRequest request) {
        try {
            session = request.getSession();
            if (session == null)
                throw new IllegalStateException("Session doesn't exist");
            User user = (User) session.getAttribute(userSession);
            if (user != null) {
                List<Invitation> invitations = invitationService.getInvitations(user.getId());
                model.addAttribute("invitations", invitations);
                logger.info(request.getRequestURL()+": Invitation list returned for "+user.getName());
                return Pages.invitation.toString();
            } else {
                logger.info(request.getRequestURL()+": Invitation user doesn't exist");
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
                logger.info(request.getRequestURL()+": Invitation accepted for "+user.getName());
                return Pages.invitation.toString();
            } else {
                logger.info(request.getRequestURL()+": Accept invitation user doesn't exist");
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
                logger.info(request.getRequestURL()+": Invitation rejected for "+user.getName());
                return Pages.invitation.toString();
            } else {
                logger.info(request.getRequestURL()+": Reject invitation user doesn't exists for "+user.getName());
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
}
