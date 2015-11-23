package edu.sjsu.cmpe275.project.model;

import javax.persistence.*;
import java.io.Serializable;

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

    @Column(name = "PASSWORD", nullable = false, unique = true)
    private String password;


    /**
     * Initiliazed friend list
     */
    public User() {
    }

    /**
     * Set person's personal details
     *
     * @param name     Name of an user
     * @param email    Email id of an user
     * @param password Password of an user
     */
    public User(String name, String email, String password) {

        this.name = name;
        this.email = email;
        this.password = password;
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
