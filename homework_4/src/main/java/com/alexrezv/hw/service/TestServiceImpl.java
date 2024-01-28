package com.alexrezv.hw.service;

import com.alexrezv.hw.dao.QuestionDao;
import com.alexrezv.hw.domain.Question;
import com.alexrezv.hw.domain.Student;
import com.alexrezv.hw.domain.TestResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        ioService.printLine("");

        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question : questions) {
            var isAnswerValid = askQuestion(question);
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

    private boolean askQuestion(Question question) {
        ioService.printLine(question.text());

        int answersSize = question.answers().size();
        IntStream.range(0, answersSize)
                .forEach(i -> ioService.printFormattedLine("%d. %s", i + 1, question.answers().get(i).text()));

        var answer = ioService.readIntForRangeWithPromptLocalized(1, answersSize,
                "TestService.input.answer.number", "TestService.wrong.input");
        return question.answers().get(answer - 1).isCorrect();
    }

}
