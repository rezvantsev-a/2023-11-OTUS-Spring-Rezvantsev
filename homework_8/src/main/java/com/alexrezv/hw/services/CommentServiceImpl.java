package com.alexrezv.hw.services;

import com.alexrezv.hw.exceptions.EntityNotFoundException;
import com.alexrezv.hw.models.Comment;
import com.alexrezv.hw.repositories.BookRepository;
import com.alexrezv.hw.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
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
    public Comment insert(ObjectId bookId, String userName, String content) {
        if (StringUtils.isBlank(userName)) {
            throw new IllegalArgumentException("Comment's author userName must not be null/empty");
        }
        if (StringUtils.isBlank(content)) {
            throw new IllegalArgumentException("Comment's content must not be null/empty");
        }

        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %s not found".formatted(bookId)));

        var comment = new Comment(null, userName, content, book.getId());
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public Comment updateContentById(ObjectId id, String newContent) {
        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id %s not found".formatted(id)));
        comment.setContent(newContent);
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public void deleteById(ObjectId id) {
        commentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> findById(ObjectId id) {
        return commentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findByBookId(ObjectId bookId) {
        return commentRepository.findByBookId(bookId);
    }

}
