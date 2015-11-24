package edu.sjsu.cmpe275.project.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "INVITATION")
public class Invitation implements Serializable {

    /**
     * Id of an Invitation. It is an auto generated id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;


    /** Participant of the project. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARTICIPANTID")
    private User participant;


    /** Project. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECTID")
    private Project project;

    /**
     * Request Status of an invitation
     */
    @Column(name = "REQUESTSTATUS", nullable = false)
    private Boolean requestStatus;

    public Invitation() {
    }

    public Invitation(User participant, Project project, Boolean requestStatus) {
        this.participant = participant;
        this.project = project;
        this.requestStatus = requestStatus;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getParticipant() {
        return participant;
    }

    public void setParticipant(User participant) {
        this.participant = participant;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Boolean getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Boolean requestStatus) {
        this.requestStatus = requestStatus;
    }
}