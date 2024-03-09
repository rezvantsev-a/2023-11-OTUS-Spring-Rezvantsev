package com.alexrezv.hw.converters;

import com.alexrezv.hw.dto.GenreDto;
import com.alexrezv.hw.models.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreConverter {
    public String genreToString(Genre genre) {
        return genreToString(genreToDto(genre));
    }

    public String genreToString(GenreDto genre) {
        return "Id: %d, Name: %s".formatted(genre.getId(), genre.getName());
    }

    public GenreDto genreToDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }

}
