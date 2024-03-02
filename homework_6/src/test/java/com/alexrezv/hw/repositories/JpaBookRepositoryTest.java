package com.alexrezv.hw.repositories;

import com.alexrezv.hw.models.Author;
import com.alexrezv.hw.models.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий JpaBookRepository должен ")
@DataJpaTest
@Import(JpaBookRepository.class)
class JpaBookRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private JpaBookRepository repository;

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

    @DisplayName("сохранить новую книгу в БД")
    @Test
    void shouldSaveBook() {
        Author author = em.find(Author.class, 1L);

        Book book = new Book(0, "New Book", author, List.of(), List.of());
        repository.save(book);

        assertThat(book.getId()).isEqualTo(4L);
    }

    @DisplayName("удалять книгу по id")
    @Test
    void shouldDeleteBookById() {
        Book book3 = em.find(Book.class, 3L);
        assertThat(book3).isNotNull();

        repository.deleteById(3L);
        em.flush();
        em.clear();

        book3 = em.find(Book.class, 3L);
        assertThat(book3).isNull();
    }

}
