package com.alexrezv.hw.repositories;

import com.alexrezv.hw.models.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaAuthorRepository implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    public JpaAuthorRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Author> findAll() {
        return em.createQuery("select a from Author a", Author.class)
                .getResultList();
    }

    @Override
    public Optional<Author> findById(long id) {
        Author result = em.createQuery("select a from Author a where a.id = :id", Author.class)
                .setParameter("id", id)
                .getSingleResult();
        return Optional.ofNullable(result);
    }

}
