package com.alexrezv.hw.repositories;

import com.alexrezv.hw.models.Genre;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GenreRepository extends MongoRepository<Genre, ObjectId> {
}
