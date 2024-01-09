package com.alexrezv.hw3.dao;

import com.alexrezv.hw3.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> findAll();
}
