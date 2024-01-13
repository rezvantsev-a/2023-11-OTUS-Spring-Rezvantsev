package com.alexrezv.hw.domain;

import java.util.List;

public record Question(String text, List<Answer> answers) {
}
