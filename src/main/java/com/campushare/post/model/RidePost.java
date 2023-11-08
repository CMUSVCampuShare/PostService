package com.campushare.post.model;

import com.campushare.post.utils.Status;
import com.campushare.post.utils.Type;

import java.util.Date;
import java.util.List;

public class RidePost extends Post {
    public RidePost(String userId, String title, String details, Type type, Integer noOfSeats, Status status, Date timestamp, List<Comment> comments) {
        super(userId, title, details, type, noOfSeats, status, timestamp, comments);
    }
}
