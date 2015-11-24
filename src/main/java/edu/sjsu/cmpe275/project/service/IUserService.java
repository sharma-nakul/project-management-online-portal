package edu.sjsu.cmpe275.project.service;

import edu.sjsu.cmpe275.project.model.User;

/**
 * @author Naks
 * Interface to deliver User services.
 */
public interface IUserService {

    long createUser(String name, String email,String password);
    boolean editUser (String name, String email,String password);
    boolean removeUser (long id);
    User getUser (long id);

}