package com.alexrezv.hw.mongock.changelog;

import com.alexrezv.hw.models.Author;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.stream.Stream;

@RequiredArgsConstructor
@ChangeUnit(id = "insert-authors", order = "001", author = "alex")
public class InsertAuthorsUnit {

    private final MongoTemplate mongoTemplate;

    @Execution
    public void changeSet() {
        var authors = Stream.of("Author_1", "Author_2", "Author_3")
                .map(fullName -> new Author(null, fullName))
                .toList();
        mongoTemplate.insertAll(authors);
    }

    @RollbackExecution
    public void rollback() {
        mongoTemplate.dropCollection(Author.class);
    }

}
