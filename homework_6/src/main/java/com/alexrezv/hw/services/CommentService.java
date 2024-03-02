package com.alexrezv.hw.services;

import com.alexrezv.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Comment insert(long bookId, String userName, String content);

    Comment updateContentById(long id, String newContent);

    void deleteById(long id);

    Optional<Comment> findById(long id);

    List<Comment> findByBookId(long bookId);

}
