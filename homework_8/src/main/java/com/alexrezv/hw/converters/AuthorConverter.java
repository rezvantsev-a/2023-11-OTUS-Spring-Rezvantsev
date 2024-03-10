package com.alexrezv.hw.converters;

import com.alexrezv.hw.models.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter {

    public String authorToString(Author author) {
        return "Id: %s, FullName: %s".formatted(author.getId(), author.getFullName());
    }

}
