package edu.sjsu.cmpe275.project.controller;

import edu.sjsu.cmpe275.project.model.Person;
import edu.sjsu.cmpe275.project.service.IPersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author Nakul Sharma
 * Controller class to handle request when format is HTML for Person and Organization GET request.
 * It also handles the GET request for root URI.
 */
@Controller
public class HtmlViewController {

    /**
     * Variable of type logger to print data on console.
     */
    private static final Logger logger = LoggerFactory.getLogger(HtmlViewController.class);

    /**
     * This Autowire the Person Service interface to serve HTTP request of Person URI.
     */
    @Autowired
    IPersonService personService;

    /**
     * Controller method to handle HTTP GET request of root URI i.e. /
     * HTTP REQUEST - GET
     * @return String with value as "home" to render home.html page.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "home";
    }


    /**
     * Controller method to handle HTTP GET request for Person URI /person/{id}
     * The format parameter is optional. If present, value is 'html'.
     * HTTP REQUEST - GET
     * @param id This is the id of an existing organization.
     * @param model This field is NOT REQUIRED as parameter, it is to provide HTML view when format=HTML.
     * @return String as "org" for correct request, otherwise "error" to map the HTML page org.html and error.html respectively.
     */
    @RequestMapping(value = "person/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String getPersonInHtml(
            @PathVariable("id") String id, Model model) {
        try {
            Person person = this.personService.getPerson(id);
            if (person != null) {
                List<Person> friends = person.getFriends();
                model.addAttribute("friends",friends);
                model.addAttribute("person", person);
                return "person";
            } else
                return "error";
        } catch (Exception e) {
            return "error";
        }
    }
}
