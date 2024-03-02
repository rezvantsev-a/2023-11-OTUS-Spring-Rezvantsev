package com.alexrezv.hw.repositories;

import com.alexrezv.hw.models.Book;
import com.alexrezv.hw.models.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий JpaCommentRepository должен ")
@DataJpaTest
@Import(JpaCommentRepository.class)
class JpaCommentRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private JpaCommentRepository repository;

    @DisplayName("сохранить новый комментарий в БД")
    @Test
    void shouldSaveNewComment() {
        Book book1 = em.find(Book.class, 1L);
        assertThat(book1).isNotNull();

        Comment comment = new Comment(0, "reader12", "Very nice book.", book1);
        repository.save(comment);

        assertThat(comment.getId())
                .isEqualTo(5L);
    }

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

    @DisplayName("удалять комментарий по id")
    @Test
    void shouldDeleteCommentById() {
        Comment comment2 = em.find(Comment.class, 2L);
        assertThat(comment2).isNotNull();

        repository.deleteById(2L);
        em.flush();
        em.clear();

        comment2 = em.find(Comment.class, 2L);
        assertThat(comment2).isNull();
    }

    @DisplayName("возвращать комментарий по его id")
    @Test
    void shouldFindCommentById() {
        Optional<Comment> comment = repository.findById(1L);

        assertThat(comment)
                .isNotEmpty().get()
                .hasFieldOrPropertyWithValue("userName", "reader1")
                .hasFieldOrPropertyWithValue("content", "Good book!");
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
