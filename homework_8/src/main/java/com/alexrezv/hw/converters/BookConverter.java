package com.alexrezv.hw.converters;

import com.alexrezv.hw.dto.BookDto;
import com.alexrezv.hw.models.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.function.Function;

import static java.util.stream.Collectors.joining;

@RequiredArgsConstructor
@Component
public class BookConverter {
    private final AuthorConverter authorConverter;

    private final GenreConverter genreConverter;

    public String bookToString(Book book) {
        return bookToString(bookToDto(book));
    }

    public String bookToString(BookDto book) {
        return "Id: %s, title: %s, author: {%s}, genres: [%s]".formatted(
                book.getId(),
                book.getTitle(),
                authorConverter.authorToString(book.getAuthor()),
                collectionToString(book.getGenres(), genreConverter::genreToString));
    }

    public BookDto bookToDto(Book book) {
        var author = authorConverter.authorToDto(book.getAuthor());
        var genres = book.getGenres().stream()
                .map(genreConverter::genreToDto)
                .toList();

        return new BookDto(book.getId().toString(), book.getTitle(), author, genres);
    }

    private static <T> String collectionToString(Collection<? extends T> collection, Function<T, String> toString) {
        return collection.stream()
                .map(toString)
                .map("{%s}"::formatted)
                .collect(joining(", "));
    }

}
