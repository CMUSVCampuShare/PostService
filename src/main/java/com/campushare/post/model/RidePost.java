package com.campushare.post.model;

import com.campushare.post.utils.Status;
import com.campushare.post.utils.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RidePost implements Post {
    @Id
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
