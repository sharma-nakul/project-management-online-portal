package edu.sjsu.cmpe275.project.service;

import edu.sjsu.cmpe275.project.dao.IInvitationDao;
import edu.sjsu.cmpe275.project.model.Invitation;
import edu.sjsu.cmpe275.project.model.Project;
import edu.sjsu.cmpe275.project.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvitationServiceImpl implements IInvitationService {


    /**
     * Object to log the values on console.
     */
    private static final Logger logger = LoggerFactory.getLogger(InvitationServiceImpl.class);

    @Autowired
    private IInvitationDao invitationDao;

    @Transactional("transManager")
    @Override
    public long sendInvitation(User participant, Project project) {
        Invitation invitation = new Invitation(participant, project, false);
        long id = invitationDao.saveInvitation(invitation);
        return id;
    }

    @Transactional("transManager")
    @Override
    public boolean rejectInvitation(long id) {
        Invitation invitation = invitationDao.getInvitation(id);
        return invitationDao.removeInvitation(invitation);
    }

    @Transactional("transManager")
    @Override
    public boolean acceptInvitation(long id) {
        Invitation invitation = invitationDao.getInvitation(id);
        invitation.setRequestStatus(true);
        return invitationDao.updateInvitation(invitation);
    }

    @Transactional("transManager")
    @Override
    public Invitation getInvitation(long id) {
        Invitation invitation = invitationDao.getInvitation(id);
        return invitation;
    }

    @Transactional("transManager")
    @Override
    public List<Invitation> getInvitations(long id) {
        return invitationDao.getInvitations(id);
    }

    @Transactional("transManager")
    @Override
    public List<User> getNonParticipants(long id) {
        List<User> participants = getParticipantList(id);
        List<User> allUsers = invitationDao.getAllUser();
        allUsers.removeAll(participants);
        allUsers.remove(getProject(id).getOwner());
        return allUsers;
    }

    @Transactional(value = "transManager")
    @Override
    public Project getProject(long id) {
        return invitationDao.getProject(id);
    }

    @Transactional("transManager")
    @Override
    public List<User> getParticipantList(long projectId) {
        List<User> participantList=new ArrayList<>();
        List<Invitation> invitationList = invitationDao.getProjectParticipantList(projectId);
        for (Invitation invitation : invitationList) {
            participantList.add(invitation.getParticipant());
        }
        if(participantList.size()>0)
            logger.info("User list returned for a project id "+projectId);
        return participantList;
    }

}
