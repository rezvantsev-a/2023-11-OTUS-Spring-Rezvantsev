package com.alexrezv.hw.dao.dto;

import com.alexrezv.hw.domain.Answer;
import com.opencsv.bean.AbstractCsvConverter;

public class AnswerCsvConverter extends AbstractCsvConverter {

    @Override
    public Object convertToRead(String value) {
        var valueArr = value.split("%");
        return new Answer(valueArr[0], Boolean.parseBoolean(valueArr[1]));
    }

}
