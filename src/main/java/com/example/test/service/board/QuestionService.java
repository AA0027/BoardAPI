package com.example.test.service.board;

import com.example.test.dao.board.Answer;
import com.example.test.dao.board.Question;
import com.example.test.dto.QuestionForm;


import com.example.test.repository.AnswerRepository;
import com.example.test.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    public Question create(QuestionForm q)
    {

        Question question = Question.builder().subject(q.getSubject()).content(q.getContent())
                .time(LocalDateTime.now()).build();

        questionRepository.save(question);
        return question;
    }

    public Question findQuestion(long id)
    {
        return questionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found: " + id));

    }

    public List<Question> findAll()
    {
        return questionRepository.findAll();
    }

    //질문관련되 답변조회
    public List<Answer> findAnswers(long id)
    {
        List<Answer> answers = answerRepository.findAnswers(id).orElseThrow(
                () -> new IllegalArgumentException("not found: " + id));
        return answers;
    }

    public void delete(long id)
    {
        questionRepository.deleteById(id);
    }

    @Transactional
    public Question update(QuestionForm form, long id)
    {
        Question q = this.findQuestion(id);
        q.setId(q.getId());
        q.setCreateDate(q.getCreateDate());
        q.setAnswerList(q.getAnswerList());
        q.setSubject(form.getSubject());
        q.setContent(form.getContent());

        questionRepository.save(q);

        return q;
    }
}
