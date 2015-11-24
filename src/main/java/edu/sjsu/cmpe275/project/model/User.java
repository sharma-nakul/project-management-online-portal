package edu.sjsu.cmpe275.project.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "USER")
public class User implements Serializable {

    /**
     * Id of an User. It is an auto generated id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    /**
     * Name of an user
     */
    @Column(name = "NAME", nullable = false)
    private String name;

    /**
     * Email id of an user. It should be unique
     */
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    /**
     * Password of an user.
     */
    @Column(name = "PASSWORD", nullable = false, unique = true)
    private String password;

     /** Projects owned by this user. */
    @OneToMany(mappedBy = "owner")
    private List<User> projects;

     /** Tasks assigned this user. */
    @OneToMany(mappedBy = "assignee")
    private List<Task> tasks;

    /** Invitation of this user. */
    @OneToMany(mappedBy = "participant")
    private List<Invitation> invitations;

    public User(String name, String email, String password, List<User> projects, List<Task> tasks, List<Invitation> invitations) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.projects = projects;
        this.tasks = tasks;
        this.invitations = invitations;
    }

    /**
     * Constructor
     */
    public User() {
    }

    public List<User> getProjects() {
        return projects;
    }

    public void setProjects(List<User> projects) {
        this.projects = projects;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
