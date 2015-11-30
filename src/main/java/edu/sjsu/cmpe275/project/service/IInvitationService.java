package edu.sjsu.cmpe275.project.service;

import edu.sjsu.cmpe275.project.model.Invitation;
import edu.sjsu.cmpe275.project.model.User;
import edu.sjsu.cmpe275.project.model.Project;

import java.util.List;


public interface IInvitationService {
    long sendInvitation(User participant, Project project);

    boolean rejectInvitation(long id);

    boolean acceptInvitation(long id);

    Invitation getInvitation(long id);

    List<Invitation> getInvitations(long id);

    List<User> getParticipantList(long projectId);

}
