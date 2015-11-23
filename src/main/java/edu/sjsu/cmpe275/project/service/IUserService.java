package edu.sjsu.cmpe275.project.service;

import edu.sjsu.cmpe275.project.model.User;


/**
 * @author Nakul Sharma
 * Interface to deliver Person related services.
 */
public interface IUserService {

    User addUser(String name, String email,String password);

}
