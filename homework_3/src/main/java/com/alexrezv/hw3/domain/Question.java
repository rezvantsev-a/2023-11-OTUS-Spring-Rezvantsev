package com.alexrezv.hw3.domain;

import java.util.List;

public record Question(String text, List<Answer> answers) {
}
