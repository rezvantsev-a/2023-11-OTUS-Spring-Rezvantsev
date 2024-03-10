package com.alexrezv.hw.converters;

import com.alexrezv.hw.models.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreConverter {

    public String genreToString(Genre genre) {
        return "Id: %s, Name: %s".formatted(genre.getId(), genre.getName());
    }

}
