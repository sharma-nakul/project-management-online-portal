package edu.sjsu.cmpe275.project.controller;

import edu.sjsu.cmpe275.project.model.User;
import edu.sjsu.cmpe275.project.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signUp(
            @ModelAttribute(value="user") User user, HttpServletRequest request) {
        try {
            if (user.getName() != null && user.getEmail() != null && user.getPassword() != null) {
                user = this.userService.createUser(user.getName(), user.getEmail(), user.getPassword());
                session = request.getSession();
                session.setAttribute("userDetails", user);
                return "home";
            } else
                    return "error";

        } catch (DataIntegrityViolationException e) {
            return "error";
        } catch (Exception e) {
            return "error";
        }
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String showSignUp(User user) {
        return "signup";
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
            session=request.getSession();
            if (user.getEmail() != null && user.getPassword() != null) {
                user = this.userService.verifyCredentials(user.getEmail(), user.getPassword());
                if(user!=null) {
                    session.setAttribute("userDetails", user);
                    return "home";
                }
                else
                    throw new BadRequestException("Password is incorrect!");
            } else
                throw new BadRequestException(request.getRequestURI(),"User does not exist.");

        } catch (BadRequestException e) {
            model.addAttribute("user", new User());
            model.addAttribute("loginError", true);
            model.addAttribute("badException",e);
            return "login";
        }
        catch (Exception e){
            model.addAttribute("exception",e);
            return "error";
        }
    }
}
