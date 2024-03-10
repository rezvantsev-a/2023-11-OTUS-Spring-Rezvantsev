package com.alexrezv.hw.repositories;

import com.alexrezv.hw.models.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, ObjectId> {

    List<Comment> findByBookId(ObjectId bookId);

}
