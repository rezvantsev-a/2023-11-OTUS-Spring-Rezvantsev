package com.alexrezv.hw.mongock.changelog;

import com.alexrezv.hw.models.Author;
import com.alexrezv.hw.models.Book;
import com.alexrezv.hw.models.Genre;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@ChangeUnit(id = "insert-books", order = "003", author = "alex")
public class InsertBooksUnit {

    private final MongoTemplate mongoTemplate;

    @Execution
    public void changeSet() {
        var authors = mongoTemplate.findAll(Author.class);
        var genres = mongoTemplate.findAll(Genre.class);
        var books = IntStream.range(0, authors.size())
                .mapToObj(i -> new Book(null, "BookTitle_" + (i + 1), authors.get(i),
                        List.of(genres.get(i * 2), genres.get(i * 2 + 1)),
                        List.of()))
                .toList();
        mongoTemplate.insertAll(books);
    }

    @RollbackExecution
    public void rollback() {
        mongoTemplate.dropCollection(Book.class);
    }

}
