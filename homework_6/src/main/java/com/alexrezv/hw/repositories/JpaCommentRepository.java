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
        var query = """
                update Comment c
                set c.content = :content
                where c.id = :id
                """;
        em.createQuery(query)
                .setParameter("content", content)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void deleteById(long id) {
        var query = """
                delete from Comment c
                where c.id = :id
                """;
        em.createQuery(query)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public Optional<Comment> findById(long id) {
        var query = """
                select c from Comment c
                join fetch c.book
                where c.id = :id
                """;
        Comment result = em.createQuery(query, Comment.class)
                .setParameter("id", id)
                .getSingleResult();
        return Optional.ofNullable(result);
    }

    @Override
    public List<Comment> findByBookId(long bookId) {
        var query = """
                select c from Comment c
                join fetch c.book
                where c.book.id = :bookId
                """;
        return em.createQuery(query, Comment.class)
                .setParameter("bookId", bookId)
                .getResultList();
    }

}
