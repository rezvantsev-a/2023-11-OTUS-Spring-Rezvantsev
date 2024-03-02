package com.alexrezv.hw.repositories;

import com.alexrezv.hw.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBookId(long bookId);

    @Modifying
    @Query("update Comment c set c.content = :content where c.id = :id")
    void updateContentById(long id, String content);

}
