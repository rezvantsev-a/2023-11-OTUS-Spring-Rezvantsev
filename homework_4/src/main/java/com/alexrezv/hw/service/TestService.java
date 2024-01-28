package com.alexrezv.hw.service;

import com.alexrezv.hw.domain.Student;
import com.alexrezv.hw.domain.TestResult;

public interface TestService {
    TestResult executeTestFor(Student student);
}
