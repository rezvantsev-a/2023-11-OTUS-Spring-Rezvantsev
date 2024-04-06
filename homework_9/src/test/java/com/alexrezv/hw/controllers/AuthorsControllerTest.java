package com.alexrezv.hw.controllers;

import com.alexrezv.hw.models.Author;
import com.alexrezv.hw.services.AuthorService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorsController.class)
public class AuthorsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @Test
    void shouldReturnAllAuthorsToTheAuthorsView() throws Exception {
        var author = new Author(1L, "John Doe");
        given(authorService.findAll())
                .willReturn(List.of(author));

        this.mvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(view().name("authors"))
                .andExpect(model().attribute("authors",
                        Matchers.<List<Author>>allOf(
                                hasSize(1),
                                contains(author)
                        )));

        verify(authorService, only()).findAll();
    }

}
