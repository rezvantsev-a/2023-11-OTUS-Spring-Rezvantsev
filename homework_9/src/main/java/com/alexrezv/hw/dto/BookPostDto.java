package com.alexrezv.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookPostDto {

    private Long id;

    private String title;

    private Long authorId;

    private Set<Long> genreIds;

    public BookPostDto(BookDto bookDto) {
        id = bookDto.getId();
        title = bookDto.getTitle();
        authorId = bookDto.getAuthor().getId();
        genreIds = bookDto.getGenres().stream()
                .map(GenreDto::getId)
                .collect(toSet());
    }

}
