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

    @Query("select a from Answer a join question q on q.id = a.question.id where q.id = ?1")
    Optional<List<Answer>> findAnswers(long id);

    //사용자가 작성한 모든 답변 보여줌
    @Query("select a from Answer a join Member m on a.member.id = m.id where m.email = ?1")
    public Optional<List<Answer>> findAnswers(String email);
}
