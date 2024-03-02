package com.alexrezv.hw.repositories;

import com.alexrezv.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    void updateContentById(long id, String content);

    void deleteById(long id);

    Optional<Comment> findById(long id);

    List<Comment> findByBookId(long bookId);

}
