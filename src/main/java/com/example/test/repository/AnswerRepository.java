package com.example.test.repository;

import com.example.test.dao.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByContent(String content);

    @Query("select a from Answer a join question on question.id = a.question.id where question.id = ?1")
    Optional<List<Answer>> findAnswers(long id);


}
