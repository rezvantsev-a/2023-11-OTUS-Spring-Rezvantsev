package com.alexrezv.hw.controllers;

import com.alexrezv.hw.models.Author;
import com.alexrezv.hw.models.Genre;
import com.alexrezv.hw.services.GenreService;
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

@WebMvcTest(GenresController.class)
public class GenresControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService genreService;

    @Test
    void shouldReturnAllGenresToTheGenresView() throws Exception {
        var genre = new Genre(1L, "Genre_1");
        given(genreService.findAll())
                .willReturn(List.of(genre));

        this.mvc.perform(get("/genres"))
                .andExpect(status().isOk())
                .andExpect(view().name("genres"))
                .andExpect(model().attribute("genres",
                        Matchers.<List<Author>>allOf(
                                hasSize(1),
                                contains(genre)
                        )));

        verify(genreService, only()).findAll();
    }

}
