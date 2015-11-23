package edu.sjsu.cmpe275.project.service;

import edu.sjsu.cmpe275.project.controller.BadRequestException;
import edu.sjsu.cmpe275.project.dao.IPersonDao;
import edu.sjsu.cmpe275.project.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nakul Sharma
 * Handler class for Person. The class intercept REST call to persist or retrieve data.
 * Service annotation to mark the class as service class in application context
 * Transactional annotation to make the class transactional entity i.e. it will be
 * counted a single transaction
 */
@Service
@Transactional("tx1")
public class PersonServiceImpl implements IPersonService {

    /**
     * Object to log the values on console.
     */
    private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    /**
     * Autowire the Person DAO interface object in this class
     */
    @Autowired
    private IPersonDao personDao;

    /**
     * Method to add a person into database
     * @param firstname firstname of a person
     * @param lastname lastname of a person
     * @param email email id of a person
     * @param description description of a person
     * @param address address of a person
     * @param organization organization object that contains only id of an existing organization
     * @return Object of an added person
     */
    public Person addPerson(String firstname, String lastname, String email, String description, Address address, Organization organization) {
        return personDao.addPerson(firstname, lastname, email, description, address, organization);
    }

    /**
     * Method to get a person from database
     * @param id Id of an existing person
     * @return Object of an existing person
     */
    public Person getPerson(String id) {
        return personDao.getPerson(id);
    }

    /**
     * Method to update a person into database
     * @param person Object of a person to be updated in database
     * @return Object of an updated person information
     */
    public Person updatePerson(Person person) {
        personDao.updatePerson(person);
        return person;
    }

    /**
     * Method to delete a person from database
     * @param person Object of a person that needs to be deleted
     */
    public void deletePerson(Person person) {
        //personDao.deletePerson(person);
        List<Person> l1 = person.getFriends();
        List<Person> l2 = person.getFriendsWith();
        person.getFriends().removeAll(l1);
        person.getFriendsWith().removeAll(l2);
        person.setFriends(person.getFriends());
        person.setFriendsWith(person.getFriends());
        personDao.deletePerson(person);
    }

    /**
     * Method to add (persist) a friend in person's friend list
     * @param p1 Object of a person who wants to add p2 from friend list
     * @param p2 Object of a person that needs to be added in friend list
     */
    public String addFriend(Person p1, Person p2) {
        try {
            int flag = 0;
            List<Person> l1;
            List<Person> l2;
            for (Person person : p1.getFriends()) {
                if(person.getFirstname().equals(p2.getFirstname())) {
                    flag = 1;
                    break;
                }
            }
            if(flag==0)
            {
                l1=p1.getFriends();
                l1.add(p2);
                l2=p1.getFriendsWith();
                l2.add(p2);
                p1.setFriends(l1);
                p1.setFriendsWith(l2);
                personDao.deleteFriend(p1);
                return "Added";
            }
            else
                throw new BadRequestException("Friends");
        }
        catch (BadRequestException e) {
            return e.getMessage();
        }
    }

    /**
     * Method to delete a friend in person's friend list
     * @param p1 Object of a person who wants to delete p2 from friend list
     * @param p2 Object of a person that needs to be deleted in friend list
     */
    public String deleteFriend(Person p1, Person p2) {
        try {
            int flag=0;
            List<Person> l1=new ArrayList<>();
            List<Person> l2=new ArrayList<>();
            for (Person person : p1.getFriends()) {
                if(person.getFirstname().equals(p2.getFirstname())){
                    l1=p1.getFriends();
                    l1.remove(person);
                    l2=p1.getFriendsWith();
                    l2.remove(person);
                    flag=1;
                    break;
                }
            }
            if (flag==1) {
                p1.setFriends(l1);
                p1.setFriendsWith(l2);
                personDao.deleteFriend(p1);
                return "Deleted";
            } else
                throw new BadRequestException("Not friends");
        } catch (BadRequestException e) {
            return e.getMessage();
        }
    }
}
