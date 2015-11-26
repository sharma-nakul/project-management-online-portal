package edu.sjsu.cmpe275.project.controller;

import edu.sjsu.cmpe275.project.exception.BadRequestException;
import edu.sjsu.cmpe275.project.model.Pages;
import edu.sjsu.cmpe275.project.model.User;
import edu.sjsu.cmpe275.project.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Naks
 *         Controller class to handle User APIs.
 */
@Controller
public class UserController {

    private static final String userSession = "userDetails";
    @Autowired
    IUserService userService;
    private HttpSession session = null;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLogin(User user) {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute(value = "user") User user, BindingResult results, HttpServletRequest request, Model model) {
        try {
            if (results.hasErrors())
                throw new Exception("Login Error");
            if (user.getEmail() != null && user.getPassword() != null) {
                session = request.getSession();
                user = this.userService.verifyCredentials(user.getEmail(), user.getPassword());
                if (user != null) {
                    session.setAttribute(userSession, user);
                    return Pages.home.toString();
                } else
                    throw new BadRequestException("Password is incorrect!");
            } else
                throw new BadRequestException(request.getRequestURI(), "User does not exist.");

        } catch (BadRequestException e) {
            model.addAttribute("user", new User());
            model.addAttribute("loginError", true);
            model.addAttribute("badException", e);
            return Pages.login.toString();
        } catch (Exception e) {
            model.addAttribute("exception", e);
            return Pages.error.toString();
        }
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String showSignUp(User user) {
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
                return Pages.home.toString();
            } else
                throw new BadRequestException("Required fields are missing", HttpStatus.BAD_REQUEST.value(), "mandatory");
        } catch (BadRequestException e) {
            model.addAttribute("signupError", true);
            model.addAttribute("badException", e);
            return Pages.signup.toString();
        } catch (Exception e) {
            model.addAttribute("exception", e);
            return Pages.error.toString();
        }
    }

    /*@RequestMapping(value="/user", method = RequestMethod.GET)
    public String getPerson(Model model, HttpServletRequest request) {
      session = request.getSession();
      User user = (User) session.getAttribute("userDetails");
      if (user != null) {
          model.addAttribute("user", user);
          return "user";
      }
      return "error";
    }*/


}
