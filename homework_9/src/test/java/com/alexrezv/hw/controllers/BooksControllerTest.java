package com.alexrezv.hw.controllers;

import com.alexrezv.hw.dto.AuthorDto;
import com.alexrezv.hw.dto.BookDto;
import com.alexrezv.hw.dto.BookPostDto;
import com.alexrezv.hw.dto.GenreDto;
import com.alexrezv.hw.models.Author;
import com.alexrezv.hw.models.Genre;
import com.alexrezv.hw.services.AuthorService;
import com.alexrezv.hw.services.BookService;
import com.alexrezv.hw.services.GenreService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BooksController.class)
public class BooksControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @Test
    void shouldReturnAllBooksToTheBooksView() throws Exception {
        var author = new AuthorDto(1L, "John Doe");
        var genre = new GenreDto(1L, "Genre_1");
        var book = new BookDto(1L, "Book_1", author, List.of(genre));
        given(bookService.findAll())
                .willReturn(List.of(book));

        this.mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("books"))
                .andExpect(model().attribute("books",
                        Matchers.<List<Author>>allOf(
                                hasSize(1),
                                contains(book)
                        )));

        verify(bookService, only()).findAll();
    }

    @Test
    void shouldDeleteBookById() throws Exception {
        this.mvc.perform(get("/books/delete/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/books"));

        verify(bookService, only()).deleteById(1L);
    }

    @Test
    void shouldShowBookEditForm() throws Exception {
        var authorDto = new AuthorDto(1L, "John Doe");
        var genreDto = new GenreDto(1L, "Genre_1");
        var book = new BookDto(1L, "Book_1", authorDto, List.of(genreDto));
        given(bookService.findById(1L))
                .willReturn(Optional.of(book));

        var author = new Author(1L, "John Doe");
        given(authorService.findAll())
                .willReturn(List.of(author));

        var genre = new Genre(1L, "Genre_1");
        given(genreService.findAll())
                .willReturn(List.of(genre));

        this.mvc.perform(get("/books/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("book_edit"))
                .andExpect(model().attribute("book",
                        equalTo(new BookPostDto(1L, "Book_1", 1L, Set.of(1L))))
                )
                .andExpect(model().attribute("authors",
                        Matchers.<List<Author>>allOf(
                                hasSize(1),
                                contains(author)
                        )))
                .andExpect(model().attribute("genres",
                        Matchers.<List<Author>>allOf(
                                hasSize(1),
                                contains(genre)
                        )));

        verify(bookService, only()).findById(1L);
        verify(authorService, only()).findAll();
        verify(genreService, only()).findAll();
    }

    @Test
    void shouldUpdateBookById() throws Exception {
        this.mvc.perform(post("/books/update/1")
                        .param("title", "Book_1")
                        .param("authorId", "2")
                        .param("genreIds", "3"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/books"));

        verify(bookService, only()).update(1L, "Book_1", 2L, Set.of(3L));
    }

    @Test
    void shouldShowBookCreationForm() throws Exception {
        var author = new Author(1L, "John Doe");
        given(authorService.findAll())
                .willReturn(List.of(author));

        var genre = new Genre(1L, "Genre_1");
        given(genreService.findAll())
                .willReturn(List.of(genre));

        this.mvc.perform(get("/books/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("book_create"))
                .andExpect(model().attribute("book", equalTo(new BookPostDto())))
                .andExpect(model().attribute("authors",
                        Matchers.<List<Author>>allOf(
                                hasSize(1),
                                contains(author)
                        )))
                .andExpect(model().attribute("genres",
                        Matchers.<List<Author>>allOf(
                                hasSize(1),
                                contains(genre)
                        )));

        verify(authorService, only()).findAll();
        verify(genreService, only()).findAll();
        verifyNoInteractions(bookService);
    }

    @Test
    void shouldCreateNewBook() throws Exception {
        this.mvc.perform(post("/books/new")
                        .param("title", "Book_1")
                        .param("authorId", "2")
                        .param("genreIds", "3"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/books"));

        verify(bookService, only()).insert("Book_1", 2L, Set.of(3L));
    }

}
