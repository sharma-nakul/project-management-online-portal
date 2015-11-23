package edu.sjsu.cmpe275.project.service;

import edu.sjsu.cmpe275.project.model.Person;


/**
 * @author Nakul Sharma
 * Interface to deliver Person related services.
 */
public interface IPersonService {

    /**
     * Abstract method to add a person into database
     * @param firstname firstname of a person
     * @param lastname lastname of a person
     * @param email email id of a person
     * @param description description of a person
     * @param address address of a person
     * @param organization organization object that contains only id of an existing organization
     * @return Object of an added person
     */
    Person addPerson(String firstname, String lastname, String email,String description, Address address, Organization organization);

    /**
     * Abstract method to update a person into database
     * @param person Object of a person to be updated in database
     * @return Object of an updated person information
     */
    Person updatePerson (Person person);

    /**
     * Abstract method to get a person from database
     * @param id Id of an existing person
     * @return Object of an existing person
     */
    Person getPerson(String id);

    /**
     * Abstract method to delete a person from database
     * @param person Object of a person that needs to be deleted
     */
    void deletePerson (Person person);

    /**
     * Abstract method to add (persist) a friend in person's friend list
     * @param person Object of a person that needs to be added in friend list
     */
    String addFriend (Person person, Person personToAdd);

    /**
     * Abstract method to delete a friend from person's friend list
     * @param person Object of a person that needs to be deleted
     */
    String deleteFriend(Person person, Person personToDelete);
}
