package com.example.test.service;

import com.example.test.dao.Answer;
import com.example.test.dao.Member;
import com.example.test.dao.Question;
import com.example.test.dto.AnswerForm;
import com.example.test.repository.AnswerRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepo;

    //답변 생성
    public Answer create(Member m, Question q, AnswerForm answerForm)
    {
        Answer answer = Answer.builder().
                content(answerForm.getContent())
                .time(LocalDateTime.now())
                .q(q)
                .member(m)
                .build();

        answerRepo.save(answer);
        return answer;
    }
    //답변 전체 조회
    public List<Answer> findAll(){return answerRepo.findAll();}



    //답변 조회
    public Answer findAnswer(long id){return answerRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("not found: " + id));}

    public void delete(long id){answerRepo.deleteById(id);}

    @Transactional
    public void update(Answer answer, AnswerForm answerForm)
    {
        answer.setContent(answerForm.getContent());
        answerRepo.save(answer);
    }
}
