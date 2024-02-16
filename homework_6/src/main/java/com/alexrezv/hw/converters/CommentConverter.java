package com.alexrezv.hw.converters;

import com.alexrezv.hw.dto.CommentDto;
import com.alexrezv.hw.models.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {

    public String commentToString(Comment comment) {
        return commentToString(commentToDto(comment));
    }

    public String commentToString(CommentDto comment) {
        return "Id: %d, UserName: %s, Content: %s".formatted(
                comment.getId(), comment.getUserName(), comment.getContent());
    }

    public CommentDto commentToDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getUserName(), comment.getContent());
    }

}
