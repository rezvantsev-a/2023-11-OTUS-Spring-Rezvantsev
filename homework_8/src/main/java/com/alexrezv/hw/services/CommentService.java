package com.alexrezv.hw.services;

import com.alexrezv.hw.models.Comment;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Comment insert(ObjectId bookId, String userName, String content);

    Comment updateContentById(ObjectId id, String newContent);

    void deleteById(ObjectId id);

    Optional<Comment> findById(ObjectId id);

    List<Comment> findByBookId(ObjectId bookId);

}
