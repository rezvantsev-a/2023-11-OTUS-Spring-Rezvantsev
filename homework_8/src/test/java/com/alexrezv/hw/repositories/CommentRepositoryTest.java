package com.alexrezv.hw.repositories;

import com.alexrezv.hw.models.Author;
import com.alexrezv.hw.models.Book;
import com.alexrezv.hw.models.Comment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий CommentRepository должен ")
@DataMongoTest
//@Import(CommentRepository.class)
class CommentRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CommentRepository repository;

    @BeforeEach
    void init() {
        var author = new Author(null, "tAuthor");
        mongoTemplate.save(author);

        var book = new Book(null, "tBook", author, List.of(), List.of());
        mongoTemplate.save(book);

        var comment1 = new Comment(null, "u1", "text1", book.getId());
        var comment2 = new Comment(null, "u2", "text2", book.getId());
        mongoTemplate.insertAll(List.of(comment1, comment2));
    }

    @DisplayName("возвращать комментарии по id книги")
    @Test
    void shouldFindCommentsByBookId() {
        var book = mongoTemplate.findOne(new Query(Criteria.where("title").is("tBook")), Book.class);

        List<Comment> comments = repository.findByBookId(book.getId());

        assertThat(comments)
                .hasSize(2)
                .allMatch(comment -> comment.getBookId().equals(book.getId()));
    }

    @AfterEach
    void tearDown() {
        mongoTemplate.dropCollection(Comment.class);
        mongoTemplate.dropCollection(Book.class);
        mongoTemplate.dropCollection(Author.class);
    }

}
