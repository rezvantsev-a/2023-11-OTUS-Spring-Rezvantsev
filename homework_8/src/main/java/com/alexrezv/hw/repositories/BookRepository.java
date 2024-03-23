package com.alexrezv.hw.repositories;

import com.alexrezv.hw.models.Book;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, ObjectId> {
}
