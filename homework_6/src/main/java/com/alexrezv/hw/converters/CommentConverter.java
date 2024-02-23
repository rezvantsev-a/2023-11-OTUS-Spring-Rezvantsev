package com.alexrezv.hw.converters;

import com.alexrezv.hw.models.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {

    public String commentToString(Comment comment) {
        return "Id: %d, UserName: %s, Content: %s".formatted(
                comment.getId(), comment.getUserName(), comment.getContent());
    }

}
