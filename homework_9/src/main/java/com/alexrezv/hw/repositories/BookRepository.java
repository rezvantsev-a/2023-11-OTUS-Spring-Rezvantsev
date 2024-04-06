package com.alexrezv.hw.repositories;

import com.alexrezv.hw.models.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Override
    @EntityGraph(attributePaths = "author")
    Optional<Book> findById(Long id);

    @Override
    @EntityGraph(attributePaths = "author")
    List<Book> findAll();

}
