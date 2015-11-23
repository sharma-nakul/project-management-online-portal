package edu.sjsu.cmpe275.project.controller;

import edu.sjsu.cmpe275.project.model.User;
import edu.sjsu.cmpe275.project.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Naks on 23-Nov-15.
 */
@RestController
@RequestMapping(value = "user",
        produces = {"application/xml", "application/json"},
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    IUserService userService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addPerson(
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "password", required = true) String password) {
        try {
            User user;
            if (name != null && email != null) {
                user = this.userService.addUser(name, email, password);
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else
                return new ResponseEntity<>("Could not save the user at this time, please check your request or try later.", HttpStatus.BAD_REQUEST);

        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Either email or name is not correct.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid Request", HttpStatus.BAD_REQUEST);
        }
    }
}
