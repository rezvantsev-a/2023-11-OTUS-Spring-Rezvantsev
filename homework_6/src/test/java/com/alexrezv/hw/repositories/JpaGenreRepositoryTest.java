package com.alexrezv.hw.repositories;

import com.alexrezv.hw.models.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий JpaGenreRepository должен ")
@DataJpaTest
@Import(JpaGenreRepository.class)
class JpaGenreRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private JpaGenreRepository repository;

    @DisplayName("возвращать список всех жанров")
    @Test
    void shouldFindAllGenres() {
        List<Genre> genres = repository.findAll();

        assertThat(genres)
                .hasSize(6);
    }

    @DisplayName("возвращать список жанров по переданным id")
    @Test
    void findAllByIds() {
        Genre genre1 = em.find(Genre.class, 1L);
        Genre genre6 = em.find(Genre.class, 6L);

        List<Genre> genres = repository.findAllByIds(Set.of(1L, 6L));

        assertThat(genres)
                .hasSize(2)
                .containsExactlyInAnyOrder(genre1, genre6);
    }

}
