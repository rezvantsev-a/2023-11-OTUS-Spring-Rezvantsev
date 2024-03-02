package com.alexrezv.hw.repositories;

import com.alexrezv.hw.models.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий JpaCommentRepository должен ")
@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CommentRepository repository;

    @DisplayName("обновить содержимое комментария по id")
    @Test
    void shouldUpdateContentById() {
        Comment comment3 = em.find(Comment.class, 3L);
        String oldContent = comment3.getContent();
        em.detach(comment3);

        repository.updateContentById(3L, "New content!");

        comment3 = em.find(Comment.class, 3L);
        assertThat(comment3.getContent())
                .isNotEqualTo(oldContent)
                .isEqualTo("New content!");
    }

    @DisplayName("возвращать комментарии по id книги")
    @Test
    void shouldFindCommentsByBookId() {
        List<Comment> comments = repository.findByBookId(1L);

        assertThat(comments)
                .hasSize(2)
                .allMatch(comment -> comment.getBook().getId() == 1L);
    }

}
