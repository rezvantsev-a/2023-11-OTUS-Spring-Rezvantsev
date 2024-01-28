package com.alexrezv.hw.service;

import com.alexrezv.hw.domain.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = StudentServiceImpl.class)
@DisplayName("Сервис для работы со Student ")
class StudentServiceImplTest {

    @Autowired
    private StudentService service;

    @MockBean
    private LocalizedIOService ioService;

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
