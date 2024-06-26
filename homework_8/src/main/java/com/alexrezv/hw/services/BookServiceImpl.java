package com.alexrezv.hw.services;

import com.alexrezv.hw.converters.BookConverter;
import com.alexrezv.hw.dto.BookDto;
import com.alexrezv.hw.exceptions.EntityNotFoundException;
import com.alexrezv.hw.models.Book;
import com.alexrezv.hw.repositories.AuthorRepository;
import com.alexrezv.hw.repositories.BookRepository;
import com.alexrezv.hw.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.util.CollectionUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final BookConverter bookConverter;

    @Transactional(readOnly = true)
    @Override
    public Optional<BookDto> findById(ObjectId id) {
        return bookRepository.findById(id)
                .map(bookConverter::bookToDto);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookConverter::bookToDto)
                .toList();
    }

    @Transactional
    @Override
    public Book insert(String title, ObjectId authorId, Set<ObjectId> genresIds) {
        return save(null, title, authorId, genresIds);
    }

    @Transactional
    @Override
    public Book update(ObjectId id, String title, ObjectId authorId, Set<ObjectId> genresIds) {
        return save(id, title, authorId, genresIds);
    }

    @Transactional
    @Override
    public void deleteById(ObjectId id) {
        bookRepository.deleteById(id);
    }

    private Book save(ObjectId id, String title, ObjectId authorId, Set<ObjectId> genresIds) {
        if (isEmpty(genresIds)) {
            throw new IllegalArgumentException("Genres ids must not be null");
        }

        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %s not found".formatted(authorId)));
        var genres = genreRepository.findAllById(genresIds);
        if (isEmpty(genres) || genresIds.size() != genres.size()) {
            throw new EntityNotFoundException("One or all genres with ids %s not found".formatted(genresIds));
        }

        var book = new Book(id, title, author, genres, List.of());
        return bookRepository.save(book);
    }

}
