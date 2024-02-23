package com.alexrezv.hw.repositories;

import com.alexrezv.hw.models.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaCommentRepository implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    public JpaCommentRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            em.persist(comment);
            return comment;
        }
        return em.merge(comment);
    }

    @Override
    public void updateContentById(long id, String content) {
        var comment = em.find(Comment.class, id);
        comment.setContent(content);
        em.persist(comment);
    }

    @Override
    public void deleteById(long id) {
        var comment = em.find(Comment.class, id);
        em.remove(comment);
        em.flush();
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> findByBookId(long bookId) {
        var query = """
                select c from Comment c
                where c.book.id = :bookId
                """;
        return em.createQuery(query, Comment.class)
                .setParameter("bookId", bookId)
                .getResultList();
    }

}
