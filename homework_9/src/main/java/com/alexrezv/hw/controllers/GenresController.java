package com.alexrezv.hw.controllers;

import com.alexrezv.hw.models.Genre;
import com.alexrezv.hw.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GenresController {

    private final GenreService genreService;

    @GetMapping("/genres")
    public String listGenres(Model model) {
        List<Genre> genres = genreService.findAll();
        model.addAttribute("genres", genres);
        return "genres";
    }

}
