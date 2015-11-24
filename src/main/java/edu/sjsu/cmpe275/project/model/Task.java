package edu.sjsu.cmpe275.project.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TASK")
public class Task implements Serializable {

    /**
     * Id of a Task. It is an auto generated id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    /**
     * Title of a task
     */
    @Column(name = "TITLE", nullable = false)
    private String title;

    /**
     * Description of a task
     */
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    public enum TaskState {
      NEW, ASSIGNED, STARTED, FINISHED, CANCELLED
    }
    /**
     * State of a task
         */
    @Column(name = "STATE", nullable = false)
    private TaskState state;

    /**
     * Estimated units of work of a task
     */
    @Column(name = "ESTIMATE", nullable = false)
    private int estimate;

    /**
     * Actual units of work of a task
     */
    @Column(name = "ACTUAL", nullable = false)
    private int actual;

    /** Assignee of the task. */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ASSIGNEEID")
    private User assignee;

    /** Project to which this task belongs. */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PROJECTID")
    private Project project;


    /**
     * Constructor
     */
    public Task() {
    }

    public Task(String title, String description, TaskState state, int estimate, int actual, User assignee, Project project) {
        this.title = title;
        this.description = description;
        this.state = state;
        this.estimate = estimate;
        this.actual = actual;
        this.assignee = assignee;
        this.project = project;
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

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public int getEstimate() {
        return estimate;
    }

    public void setEstimate(int estimate) {
        this.estimate = estimate;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }


}
