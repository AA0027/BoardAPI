package com.example.test.repository;

import com.example.test.dao.Answer;
import com.example.test.dao.Member;
import com.example.test.dao.Question;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@SpringBootTest
@Transactional
@Rollback(value = true)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepo;

    @Autowired
    QuestionRepository questionRepo;

    @Autowired
    AnswerRepository answerRepo;

    @Test
    void findQuestions() {
        Member member = Member.builder().name("chang-sung")
                .email("tmxk1266@naver.com").addr("Seoul").build();
        memberRepo.save(member);
        for(int i=0;i<3;i++) {
            Question q = Question.builder().member(member).content("sdfdf" + i)
                    .subject("aaa" + i).time(LocalDateTime.now()).build();

            questionRepo.save(q);
        }
            List<Question> questions = questionRepo.findQuestions(member.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("not found: "));
            Assertions.assertThat(questions.size()).isEqualTo(3);



    }

    @Test
    void findAnswers() {
        Member member = Member.builder().name("chang-sung")
                .email("tmxk1266@naver.com").addr("Seoul").build();
        memberRepo.save(member);
        for(int i=0;i<3;i++) {
            Answer q = Answer.builder().member(member).content("sdfdf" + i)
                    .time(LocalDateTime.now()).build();

            answerRepo.save(q);
        }
        List<Answer> answers = answerRepo.findAnswers(member.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("not found: "));
        Assertions.assertThat(answers.size()).isEqualTo(3);

    }
}