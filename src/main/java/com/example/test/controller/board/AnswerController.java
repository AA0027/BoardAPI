package com.example.test.controller.board;

import com.example.test.dao.board.Answer;
import com.example.test.dao.board.Question;
import com.example.test.dto.AnswerForm;
import com.example.test.service.board.AnswerService;
import com.example.test.service.board.QuestionService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;
    @PostMapping("/questions/answer/{id}")
    public ResponseEntity<AnswerForm> create(@PathVariable("id") long id,
                                             @RequestBody AnswerForm answerForm)
    {
        Question question = questionService.findQuestion(id);

        Answer answer = answerService.create(question, answerForm);


        return ResponseEntity.status(HttpStatus.CREATED).body(answer.toForm());
    }


    @GetMapping("/answers/{id}")
    public ResponseEntity<AnswerForm> getAnswer(@PathVariable("id") long id)
    {
        Answer answer = answerService.findAnswer(id);
        return ResponseEntity.ok().body(answer.toForm());
    }

    @GetMapping("/answers")
    public ResponseEntity<List<AnswerForm>> getAnswerList()
    {
        List<Answer> answerList = answerService.findAll();
        List<AnswerForm> response = answerList.stream().map(Answer::toForm).toList();
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/answers/{id}")
    public ResponseEntity<AnswerForm> updateAnswer(@RequestBody() AnswerForm newForm,
                                                   @PathVariable("id") long id)
    {
        Answer answer = answerService.findAnswer(id);
        answerService.update(answer, newForm);
        return ResponseEntity.ok().body(answer.toForm());
    }

    @DeleteMapping("/answers/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable("id") long id)
    {
        questionService.delete(id);
        return ResponseEntity.ok().build();
    }
}
