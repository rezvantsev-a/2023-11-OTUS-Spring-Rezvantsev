package com.alexrezv.hw.services;

import com.alexrezv.hw.dto.BookDto;
import com.alexrezv.hw.models.Book;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookService {
    Optional<BookDto> findById(long id);

    List<BookDto> findAll();

    Book insert(String title, long authorId, Set<Long> genresIds);

    Book update(long id, String title, long authorId, Set<Long> genresIds);

    void deleteById(long id);
}
