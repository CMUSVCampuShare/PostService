package com.campushare.post.dto;

import com.campushare.post.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostEvent {
    private String message;
    private String status;
    private Post post;
}
