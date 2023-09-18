package com.example.test.service.board;

import com.example.test.dao.board.Answer;
import com.example.test.dto.AnswerForm;
import com.example.test.repository.board.AnswerRepository;
import com.example.test.repository.board.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AnswerService {
    private final QuestionRepository questionRepo;
    private final AnswerRepository answerRepo;

    public Integer create(AnswerForm answerForm,long id)
    {
        Answer answer = Answer.builder().
                content(answerForm.getContent())
                .time(LocalDateTime.now())
                .q(questionRepo.findById(id).orElseThrow())
                .build();

        answerRepo.save(answer);

        return answer.getId();
    }
}
