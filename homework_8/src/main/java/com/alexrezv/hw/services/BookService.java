package com.alexrezv.hw.services;

import com.alexrezv.hw.models.Book;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookService {

    Optional<Book> findById(ObjectId id);

    List<Book> findAll();

    Book insert(String title, ObjectId authorId, Set<ObjectId> genresIds);

    Book update(ObjectId id, String title, ObjectId authorId, Set<ObjectId> genresIds);

    void deleteById(ObjectId id);

}
