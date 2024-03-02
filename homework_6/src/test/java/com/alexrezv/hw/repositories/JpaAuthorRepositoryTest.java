package com.alexrezv.hw.repositories;

import com.alexrezv.hw.models.Author;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий JpaAuthorRepository должен ")
@DataJpaTest
@Import(JpaAuthorRepository.class)
class JpaAuthorRepositoryTest {

    @Autowired
    private JpaAuthorRepository repository;

    @DisplayName("возвращать список всех авторов")
    @Test
    void shouldFindAllAuthors() {
        List<Author> authors = repository.findAll();

        assertThat(authors)
                .hasSize(3);
    }

    @DisplayName("возвращать автора по его id")
    @Test
    void shouldFindAuthorById() {
        Optional<Author> author = repository.findById(1L);

        assertThat(author)
                .isNotEmpty().get()
                .hasFieldOrPropertyWithValue("fullName", "Author_1");
    }

}
