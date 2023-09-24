package com.example.test.repository;

import com.example.test.dao.Answer;
import com.example.test.dao.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("select q from question q join Member m on m.id = q.member.id where m.email = ?1")
    public Optional<List<Question>> findQuestions(String email);


}
