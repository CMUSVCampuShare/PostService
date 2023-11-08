package com.campushare.post.request;

import com.campushare.post.model.Comment;
import com.campushare.post.utils.Status;
import com.campushare.post.utils.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private String postId;
    private String userId;
    private String title;
    private String details;
    private Type type;
    private Integer noOfSeats;
    private Status status;
    private Date timestamp;
    private List<Comment> comments;
}
