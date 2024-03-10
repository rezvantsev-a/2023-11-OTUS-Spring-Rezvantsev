package com.alexrezv.hw.mongock.changelog;

import com.alexrezv.hw.models.Book;
import com.alexrezv.hw.models.Comment;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@RequiredArgsConstructor
@ChangeUnit(id = "insert-comments", order = "004", author = "alex")
public class InsertCommentsUnit {

    private final MongoTemplate mongoTemplate;

    @Execution
    public void changeSet() {
        var books = mongoTemplate.findAll(Book.class);
        var book1Id = books.get(0).getId();
        var comment1 = new Comment(null, "reader1", "Good book!", book1Id);
        var comment2 = new Comment(null, "reader2", "Must read!", book1Id);
        var comment3 = new Comment(null, "reader1", "Very captivating!", books.get(1).getId());
        var comment4 = new Comment(null, "reader3", "Waste fo paper!", books.get(2).getId());
        mongoTemplate.insertAll(List.of(comment1, comment2, comment3, comment4));
    }

    @RollbackExecution
    public void rollback() {
        mongoTemplate.dropCollection(Comment.class);
    }

}
