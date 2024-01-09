package com.alexrezv.hw3.dao;

import com.alexrezv.hw3.config.TestFileNameProvider;
import com.alexrezv.hw3.domain.Answer;
import com.alexrezv.hw3.domain.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Репо для получения вопросов из CSV ")
class CsvQuestionDaoTest {

    private QuestionDao dao;

    @Mock
    private TestFileNameProvider fileNameProvider;

    @BeforeEach
    void setUp() {
        dao = new CsvQuestionDao(fileNameProvider);
    }

    @Test
    @DisplayName("должен вернуть все вопросы из указанного файла")
    void findAll() {
        when(fileNameProvider.getTestFileName()).thenReturn("test_questions.csv");

        List<Question> actual = dao.findAll();

        var expected = new Question("Is Santa real?",
                List.of(new Answer("No", true), new Answer("Yes", false)));

        assertThat(actual)
                .isNotEmpty()
                .containsExactly(expected);

        verify(fileNameProvider, only()).getTestFileName();
    }

}
