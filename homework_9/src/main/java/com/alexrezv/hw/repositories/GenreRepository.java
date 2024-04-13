package com.alexrezv.hw.repositories;

import com.alexrezv.hw.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
