package com.alexrezv.hw.mongock.changelog;

import com.alexrezv.hw.models.Genre;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.stream.Stream;

@RequiredArgsConstructor
@ChangeUnit(id = "insert-genres", order = "002", author = "alex")
public class InsertGenresUnit {

    private final MongoTemplate mongoTemplate;

    @Execution
    public void changeSet() {
        var authors = Stream.of("Genre_1", "Genre_2", "Genre_3",
                        "Genre_4", "Genre_5", "Genre_6")
                .map(name -> new Genre(null, name))
                .toList();
        mongoTemplate.insertAll(authors);
    }

    @RollbackExecution
    public void rollback() {
        mongoTemplate.dropCollection(Genre.class);
    }

}
