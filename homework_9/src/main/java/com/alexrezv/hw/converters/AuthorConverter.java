package com.alexrezv.hw.converters;

import com.alexrezv.hw.dto.AuthorDto;
import com.alexrezv.hw.models.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter {

    public String authorToString(Author author) {
        return authorToString(authorToDto(author));
    }

    public String authorToString(AuthorDto author) {
        return "Id: %d, FullName: %s".formatted(author.getId(), author.getFullName());
    }

    public AuthorDto authorToDto(Author author) {
        return new AuthorDto(author.getId(), author.getFullName());
    }

}
