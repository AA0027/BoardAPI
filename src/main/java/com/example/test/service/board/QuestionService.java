package com.example.test.service.board;

import com.example.test.dao.board.Question;
import com.example.test.dto.QuestionForm;
import com.example.test.repository.board.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository repo;

    public Question create(QuestionForm q)
    {

        Question question = Question.builder().subject(q.getSubject()).content(q.getContent())
                .time(LocalDateTime.now()).build();

        repo.save(question);
        return question;
    }

    public Question findQuestion(long id)
    {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("not found: " + id));

    }

    public List<Question> findAll()
    {
        return repo.findAll();
    }

    public void delete(long id)
    {
        repo.deleteById(id);
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

        repo.save(q);

        return q;
    }
}
