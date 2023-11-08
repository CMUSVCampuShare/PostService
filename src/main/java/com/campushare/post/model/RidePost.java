package com.campushare.post.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity(name = "ride_post")
@PrimaryKeyJoinColumn(name = "postId")
public class RidePost extends Post {
}
