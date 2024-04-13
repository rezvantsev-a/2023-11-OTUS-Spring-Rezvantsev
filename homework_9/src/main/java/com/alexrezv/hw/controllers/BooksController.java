package com.alexrezv.hw.controllers;

import com.alexrezv.hw.dto.BookPostDto;
import com.alexrezv.hw.exceptions.EntityNotFoundException;
import com.alexrezv.hw.services.AuthorService;
import com.alexrezv.hw.services.BookService;
import com.alexrezv.hw.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class BooksController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping("/books")
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/books/update/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        var book = bookService.findById(id)
                .map(BookPostDto::new)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(id)));
        model.addAttribute("book", book);

        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());

        return "book_edit";
    }

    @PostMapping("/books/update/{id}")
    public String updateBook(@PathVariable("id") long id, @ModelAttribute("book") BookPostDto book) {
        bookService.update(id, book.getTitle(), book.getAuthorId(), book.getGenreIds());

        return "redirect:/books";
    }

    @GetMapping("/books/new")
    public String bookCreationPage(Model model) {
        model.addAttribute("book", new BookPostDto());
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());

        return "book_create";
    }

    @PostMapping("/books/new")
    public String createBook(BookPostDto book) {
        bookService.insert(book.getTitle(), book.getAuthorId(), book.getGenreIds());

        return "redirect:/books";
    }

}
