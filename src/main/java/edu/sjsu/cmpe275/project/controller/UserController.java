package edu.sjsu.cmpe275.project.controller;

import edu.sjsu.cmpe275.project.exception.BadRequestException;
import edu.sjsu.cmpe275.project.model.User;
import edu.sjsu.cmpe275.project.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Naks
 * Controller class to handle User APIs.
 */
@Controller
public class UserController {

    @Autowired
    IUserService userService;

    private HttpSession session = null;

    private static final String signupPage = "signup";
    private static final String errorPage = "error";
    private static final String loginPage = "login";
    private static final String homePage = "home";
    private static final String userSession = "userDetails";


    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signUp(
            @ModelAttribute(value="user") User user, HttpServletRequest request) {
        try {
            if (user.getName() != null && user.getEmail() != null && user.getPassword() != null) {
                user = this.userService.createUser(user.getName(), user.getEmail(), user.getPassword());
                session = request.getSession();
                session.setAttribute(userSession, user);
                return homePage;
            } else
                    return errorPage;

        } catch (DataIntegrityViolationException e) {
            return errorPage;
        } catch (Exception e) {
            return errorPage;
        }
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String showSignUp(User user) {
        return signupPage;
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

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLogin(User user) {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute(value = "user") User user, BindingResult results, HttpServletRequest request, Model model) {
        try {
            if(results.hasErrors())
                throw new Exception("Login Error");
            if (user.getEmail() != null && user.getPassword() != null) {
                session=request.getSession();
                user = this.userService.verifyCredentials(user.getEmail(), user.getPassword());
                if(user!=null) {
                    session.setAttribute(userSession, user);
                    return homePage;
                }
                else
                    throw new BadRequestException("Password is incorrect!");
            } else
                throw new BadRequestException(request.getRequestURI(),"User does not exist.");

        } catch (BadRequestException e) {
            model.addAttribute("user", new User());
            model.addAttribute("loginError", true);
            model.addAttribute("badException",e);
            return loginPage;
        }
        catch (Exception e){
            model.addAttribute("exception",e);
            return errorPage;
        }
    }
}
