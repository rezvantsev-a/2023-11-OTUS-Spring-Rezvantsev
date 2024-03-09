package com.alexrezv.hw.services;

import com.alexrezv.hw.exceptions.EntityNotFoundException;
import com.alexrezv.hw.models.Comment;
import com.alexrezv.hw.repositories.BookRepository;
import com.alexrezv.hw.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Transactional
    @Override
    public Comment insert(long bookId, String userName, String content) {
        if (StringUtils.isBlank(userName)) {
            throw new IllegalArgumentException("Comment's author userName must not be null/empty");
        }
        if (StringUtils.isBlank(content)) {
            throw new IllegalArgumentException("Comment's content must not be null/empty");
        }

        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));

        var comment = new Comment(0, userName, content, book);
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public Comment updateContentById(long id, String newContent) {
        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id %d not found".formatted(id)));
        comment.setContent(newContent);
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> findById(long id) {
        return commentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findByBookId(long bookId) {
        return commentRepository.findByBookId(bookId);
    }

}
