package com.alexrezv.hw3.service;

import com.alexrezv.hw3.domain.Student;
import com.alexrezv.hw3.domain.TestResult;

public interface TestService {
    TestResult executeTestFor(Student student);
}
