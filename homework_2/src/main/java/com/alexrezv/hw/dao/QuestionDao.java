package com.alexrezv.hw.dao;

import com.alexrezv.hw.domain.Question;

import java.util.List;

public interface QuestionDao {

    List<Question> findAll();

}
