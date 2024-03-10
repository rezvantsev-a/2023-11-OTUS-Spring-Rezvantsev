package com.alexrezv.hw.events;

import com.alexrezv.hw.models.Book;
import com.alexrezv.hw.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class BookCascadeDeleteMongoEventListener extends AbstractMongoEventListener<Object> {

    private final CommentRepository commentRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Object> event) {
        if (Objects.requireNonNull(event.getType()).isAssignableFrom(Book.class)) {
            var comments = commentRepository.findByBookId(event.getDocument().getObjectId("_id"));
            commentRepository.deleteAll(comments);
        }
    }

}
