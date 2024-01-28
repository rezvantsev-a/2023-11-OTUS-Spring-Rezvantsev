package com.alexrezv.hw.dao;

import com.alexrezv.hw.config.TestFileNameProvider;
import com.alexrezv.hw.dao.dto.QuestionDto;
import com.alexrezv.hw.domain.Question;
import com.alexrezv.hw.exceptions.QuestionReadException;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Component
public class CsvQuestionDao implements QuestionDao {

    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        try (var inputStream = new ClassPathResource(fileNameProvider.getTestFileName()).getInputStream()) {
            List<QuestionDto> questionDtos = new CsvToBeanBuilder<QuestionDto>(new InputStreamReader(inputStream))
                    .withType(QuestionDto.class)
                    .withSkipLines(1)
                    .withSeparator(';')
                    .build()
                    .parse();

            return questionDtos.stream()
                    .map(QuestionDto::toDomainObject)
                    .collect(toList());
        } catch (IOException e) {
            throw new QuestionReadException(e.getMessage(), e);
        }
    }

}
