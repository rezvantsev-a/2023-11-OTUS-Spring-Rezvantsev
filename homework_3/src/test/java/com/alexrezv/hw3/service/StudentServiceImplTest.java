package com.alexrezv.hw3.service;

import com.alexrezv.hw3.domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис для работы со Student ")
class StudentServiceImplTest {

    private StudentService service;

    @Mock
    private LocalizedIOService ioService;

    @BeforeEach
    void setUp() {
        service = new StudentServiceImpl(ioService);
    }

    @Test
    @DisplayName("должен определить текущего студента")
    void determineCurrentStudent() {
        when(ioService.readStringWithPromptLocalized(anyString()))
                .thenReturn("John")
                .thenReturn("Doe");

        Student actual = service.determineCurrentStudent();

        assertThat(actual)
                .isEqualTo(new Student("John", "Doe"));

        var order = inOrder(ioService);
        order.verify(ioService).readStringWithPromptLocalized("StudentService.input.first.name");
        order.verify(ioService).readStringWithPromptLocalized("StudentService.input.last.name");
        order.verifyNoMoreInteractions();
    }

}
