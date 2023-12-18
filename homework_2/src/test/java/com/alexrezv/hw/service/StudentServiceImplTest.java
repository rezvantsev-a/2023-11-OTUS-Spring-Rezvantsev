package com.alexrezv.hw.service;

import com.alexrezv.hw.domain.Student;
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
    private IOService ioService;

    @BeforeEach
    void setUp() {
        service = new StudentServiceImpl(ioService);
    }

    @Test
    @DisplayName("должен определить текущего студента")
    void determineCurrentStudent() {
        when(ioService.readStringWithPrompt(anyString()))
                .thenReturn("John")
                .thenReturn("Doe");

        Student actual = service.determineCurrentStudent();

        assertThat(actual)
                .isEqualTo(new Student("John", "Doe"));

        var order = inOrder(ioService);
        order.verify(ioService).readStringWithPrompt("Please input your first name");
        order.verify(ioService).readStringWithPrompt("Please input your last name");
        order.verifyNoMoreInteractions();
    }

}
