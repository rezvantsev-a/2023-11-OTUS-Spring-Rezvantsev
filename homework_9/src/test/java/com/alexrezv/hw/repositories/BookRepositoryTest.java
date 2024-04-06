package com.alexrezv.hw.repositories;

import com.alexrezv.hw.models.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий JpaBookRepository должен ")
@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @DisplayName("возвращать книгу по её id")
    @Test
    void shouldFindBookById() {
        Optional<Book> book = repository.findById(1L);

        assertThat(book)
                .isNotEmpty().get()
                .hasFieldOrPropertyWithValue("title", "BookTitle_1");
    }

    @DisplayName("возвращать список всех книг")
    @Test
    void findAllBooks() {
        List<Book> books = repository.findAll();

        assertThat(books)
                .hasSize(3);
    }

}
