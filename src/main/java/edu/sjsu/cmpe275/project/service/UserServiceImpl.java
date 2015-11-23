package edu.sjsu.cmpe275.project.service;

import edu.sjsu.cmpe275.project.dao.IUserDao;
import edu.sjsu.cmpe275.project.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nakul Sharma
 * Handler class for Person. The class intercept REST call to persist or retrieve data.
 * Service annotation to mark the class as service class in application context
 * Transactional annotation to make the class transactional entity i.e. it will be
 * counted a single transaction
 */
@Service
@Transactional("tx1")
public class UserServiceImpl implements IUserService {

    /**
     * Object to log the values on console.
     */
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * Autowire the Person DAO interface object in this class
     */
    @Autowired
    private IUserDao personDao;

    @Override
    public User addUser(String name, String email,String password){
        return personDao.addUser(name, email, password);
    }

}
