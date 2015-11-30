package edu.sjsu.cmpe275.project.model;

import org.springframework.stereotype.Component;

/**
 * Created by Naks on 29-Nov-15.
 */
@Component
public class Report {
    private long count;
    private User user;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}