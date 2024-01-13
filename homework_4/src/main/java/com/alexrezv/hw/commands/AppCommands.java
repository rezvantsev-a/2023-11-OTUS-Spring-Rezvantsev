package com.alexrezv.hw.commands;

import com.alexrezv.hw.service.TestRunnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellComponent
public class AppCommands {

    private final TestRunnerService testRunnerService;

    @ShellMethod(value = "Start testing", key = "st")
    public void startTesting() {
        testRunnerService.run();
    }

}
