package com.alexrezv.hw.repositories;

import com.alexrezv.hw.models.Author;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorRepository extends MongoRepository<Author, ObjectId> {
}
