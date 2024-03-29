package com.alexrezv.hw3.dao.dto;

import com.alexrezv.hw3.domain.Answer;
import com.alexrezv.hw3.domain.Question;
import com.opencsv.bean.CsvBindAndSplitByPosition;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionDto {

    @CsvBindByPosition(position = 0)
    private String text;

    @CsvBindAndSplitByPosition(position = 1, collectionType = ArrayList.class, elementType = Answer.class,
            converter = AnswerCsvConverter.class, splitOn = "\\|")
    private List<Answer> answers;

    public Question toDomainObject() {
        return new Question(text, answers);
    }
}
