package edu.sjsu.cmpe275.project.model;

import org.springframework.stereotype.Component;

/**
 * Created by Naks on 29-Nov-15.
 */
@Component
public class Report {
    private long count;
    private long userId;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
