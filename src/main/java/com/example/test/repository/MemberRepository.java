package com.example.test.repository;


import com.example.test.dao.Answer;
import com.example.test.dao.Member;
import com.example.test.dao.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    public Optional<Member> findByEmail(String email);
    public Optional<Member> findByName(String name);
    //사용자가 작선한 모든 질문 보여줌
    @Query("select q from question q join Member on Member.id = q.member.id where Member.email = ?1")
    public Optional<List<Question>> findQuestions(String email);

    //사용자가 작성한 모든 답변 보여줌
    @Query("select a from Answer a join Member on a.member.id = Member.id where Member.email = ?1")
    public Optional<List<Answer>> findAnswers(String email);
}
