package com.alexrezv.hw.commands;

import com.alexrezv.hw.converters.CommentConverter;
import com.alexrezv.hw.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {

    private final CommentService commentService;

    private final CommentConverter commentConverter;

    @ShellMethod(value = "Find comment by id", key = "cbid")
    public String findCommentById(long id) {
        return commentService.findById(id)
                .map(commentConverter::commentToString)
                .orElse("Comment with id %d not found".formatted(id));
    }

    @ShellMethod(value = "Find comments by book id", key = "cbbid")
    public String findAllCommentsByBookId(long bookId) {
        return commentService.findByBookId(bookId).stream()
                .map(commentConverter::commentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    // cins 2 anonymous sample_text
    @ShellMethod(value = "Insert comment", key = "cins")
    public String insertComment(long bookId, String userName, String content) {
        var savedComment = commentService.insert(bookId, userName, content);
        return commentConverter.commentToString(savedComment);
    }

    // cupd 5 "new text"
    @ShellMethod(value = "Update content by comment id", key = "cupd")
    public String updateContentByCommentId(long id, String newContent) {
        var savedComment = commentService.updateContentById(id, newContent);
        return commentConverter.commentToString(savedComment);
    }

    // cdel 5
    @ShellMethod(value = "Delete comment by id", key = "cdel")
    public void deleteBook(long id) {
        commentService.deleteById(id);
    }

}
