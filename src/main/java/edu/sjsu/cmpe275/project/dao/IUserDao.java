package edu.sjsu.cmpe275.project.dao;

import edu.sjsu.cmpe275.project.model.User;

/**
 * @author Naks
 * User DAO to perform database operation.
 */
public interface IUserDao {
    long addUser(User user);
    boolean updateUser (User user);
    boolean deleteUser (User user);
    User getUser (long id);
}
