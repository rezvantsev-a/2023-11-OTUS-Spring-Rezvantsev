package com.alexrezv.hw.dao;

import com.alexrezv.hw.config.TestFileNameProvider;
import com.alexrezv.hw.domain.Answer;
import com.alexrezv.hw.domain.Question;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CsvQuestionDao.class)
@DisplayName("Репо для получения вопросов из CSV ")
class CsvQuestionDaoTest {

    @Autowired
    private QuestionDao dao;

    @MockBean
    private TestFileNameProvider fileNameProvider;

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
