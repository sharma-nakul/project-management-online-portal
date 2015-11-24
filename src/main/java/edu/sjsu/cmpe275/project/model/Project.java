package edu.sjsu.cmpe275.project.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "PROJECT")
public class Project implements Serializable {

    /**
     * Id of a Project. It is an auto generated id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    /**
     * Title of a project
     */
    @Column(name = "TITLE", nullable = false)
    private String title;

    /**
     * Description of a project
     */
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    /** Owner of the project. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OWNERID")
    private User owner;

     /** Tasks of this project. */
    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

    /** Invitation of this project. */
    @OneToMany(mappedBy = "project")
    private List<Invitation> invitations;


    /**
     * Constructor
     */
    public Project() {
     tasks = new ArrayList<Task>();
     invitations = new ArrayList<Invitation>();
    }

    public Project(String title, String description, User owner, List<Task> tasks, List<Invitation> invitations) {
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.tasks = tasks;
        this.invitations = invitations;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
    }

    public List<Invitation> getInvitations() {
        return invitations;
    }

    public void setInvitations(List<Invitation> invitations) {
        this.invitations = invitations;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }




}
