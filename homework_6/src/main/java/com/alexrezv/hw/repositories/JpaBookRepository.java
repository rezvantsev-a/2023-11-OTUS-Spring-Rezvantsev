package com.alexrezv.hw.repositories;

import com.alexrezv.hw.models.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.graph.GraphSemantic;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaBookRepository implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    public JpaBookRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Book> findById(long id) {
        var query = """
                select b from Book b
                where b.id = :id
                """;
        var result = em.createQuery(query, Book.class)
                .setHint(GraphSemantic.FETCH.getJakartaHintName(), em.getEntityGraph(Book.ENTITY_GRAPH_AUTHOR))
                .setParameter("id", id)
                .getResultList().stream()
                .findFirst();

        return result;
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("select b from Book b", Book.class)
                .setHint(GraphSemantic.FETCH.getJakartaHintName(), em.getEntityGraph(Book.ENTITY_GRAPH_AUTHOR))
                .getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public void deleteById(long id) {
        var book = em.find(Book.class, id);
        em.remove(book);
        em.flush();
    }

}
