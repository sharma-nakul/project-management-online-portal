package edu.sjsu.cmpe275.project.dao;

import edu.sjsu.cmpe275.project.model.User;

/**
 * @author Nakul Sharma
 * Person DAO to perform database operation.
 */
public interface IUserDao {

    User addUser(String name, String email,String password);
}
