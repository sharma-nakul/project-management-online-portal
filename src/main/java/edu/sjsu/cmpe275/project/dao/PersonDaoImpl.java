package edu.sjsu.cmpe275.project.dao;

import edu.sjsu.cmpe275.project.model.Person;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


/**
 * @author Nakul Sharma
 * Implementation class for Peson DAO interface
 * Repository annotation to mark the class as repository entity for a person.
 */
@Repository
public class PersonDaoImpl extends AbstractDao implements IPersonDao {

    /**
     * Variable of type logger to print data on console
     */
    private static final Logger logger = LoggerFactory.getLogger(PersonDaoImpl.class);
    /**
     * Object to hold the current session for hibernate connection
     */
    private Session session;

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
    @Override
    public Person addPerson(String firstname, String lastname, String email, String description, Address address, Organization organization) {
        Person person = new Person(firstname, lastname, email, description, address, organization);
        session = getSession();
        session.save(person);
        session.flush();
        logger.info(person.getFirstname() + " " + person.getLastname() + " added successfully");
        return person;
    }

    /**
     * Method to get a person from database
     * @param id Id of an existing person
     * @return Object of an existing person
     */
    @Override
    public Person getPerson(String id) {
        session = getSession();
        long p_id = Long.parseLong(id);
        Person person = (Person) session.get(Person.class, p_id);
        if (person == null)
            logger.info("Returns null while retrieving the person of id " + p_id);
        else
            logger.info("Person of id " + p_id + " exists in database.");
        return person;
    }

    /**
     * Method to update a person into database
     * @param person Object of a person to be updated in database
     * @return Object of an updated person information
     */
    @Override
    public Person updatePerson(Person person) {
        session =getSession();
        session.update(person);
        logger.info(person.getFirstname() + " " + person.getLastname() + " profile updated successfully");
        return person;
    }

    /**
     * Method to delete a person from database
     * @param person Object of a person that needs to be deleted
     */
    @Override
    public void deletePerson (Person person){
        session=getSession();
        session.delete(person);
        logger.info(person.getFirstname() + " " + person.getLastname() + " deleted successfully");
    }

    /**
     * Method to add (persist) a friend in person's friend list
     * @param person Object of a person that needs to be added in friend list
     */
    @Override
    public void addFriend(Person person) {
        session =getSession();
        session.update(person);
        logger.info(person.getFirstname() + " " + person.getLastname() + " updated friendship successfully");
    }

    /**
     * Method to delete a friend from person's friend list
     * @param person Object of a person that needs to be deleted
     */
    @Override
    public void deleteFriend(Person person){
        session=getSession();
        session.update(person);
        logger.info(person.getFirstname() + " " + person.getLastname() + " deleted friendship successfully");
    }

}
