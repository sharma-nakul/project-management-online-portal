package edu.sjsu.cmpe275.project.controller;

import edu.sjsu.cmpe275.project.model.Invitation;
import edu.sjsu.cmpe275.project.model.User;
import edu.sjsu.cmpe275.project.service.IInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;


@Controller
public class InvitationController {

    @Autowired
    IInvitationService  invitationService;

    private HttpSession session = null;

    @RequestMapping(value = "/invitation", method = RequestMethod.GET)
    public String showInvitation(Model model, HttpServletRequest request) {
        session = request.getSession();
        User user = (User) session.getAttribute("userDetails");
        if (user != null) {
            List<Invitation> invitations = invitationService.getInvitations(user.getId());
            model.addAttribute("invitations", invitations);
            return "invitation";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/acceptinvitation/{id}", method = RequestMethod.POST)
    public String acceptInvitation(@PathVariable("id") String id, HttpServletRequest request, Model model) {
        invitationService.acceptInvitation(Long.valueOf(id));
        session = request.getSession();
        User user = (User) session.getAttribute("userDetails");
        if (user != null) {
            List<Invitation> invitations = invitationService.getInvitations(user.getId());
            model.addAttribute("invitations", invitations);
            return "invitation";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/rejectinvitation/{id}", method = RequestMethod.POST)
    public String  rejectInvitation(@PathVariable("id") String id, HttpServletRequest request, Model model) {
        invitationService.rejectInvitation(Long.valueOf(id)) ;
        session = request.getSession();
        User user = (User) session.getAttribute("userDetails");
        if (user != null) {
            List<Invitation> invitations = invitationService.getInvitations(user.getId());
            model.addAttribute("invitations", invitations);
            return "invitation";
        } else {
            return "redirect:/login";
        }
    }
}
