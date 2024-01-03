package com.alexrezv.hw.service;

import com.alexrezv.hw.domain.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final IOService ioService;

    @Override
    public Student determineCurrentStudent() {
        var firstName = ioService.readStringWithPrompt("Please input your first name");
        var lastName = ioService.readStringWithPrompt("Please input your last name");
        return new Student(firstName, lastName);
    }
}
