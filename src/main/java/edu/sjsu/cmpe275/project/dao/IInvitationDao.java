package edu.sjsu.cmpe275.project.dao;

import edu.sjsu.cmpe275.project.model.Invitation;

/**
 * @author Naks
 * Invitation Dao interface to perform database operation
 */
public interface IInvitationDao {
    boolean updateInvitation (Invitation invitation);
    Invitation getInvitation (long id);
}
