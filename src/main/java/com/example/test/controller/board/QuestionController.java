package com.example.test.controller.board;

import com.example.test.dao.board.Question;
import com.example.test.dto.QuestionForm;
import com.example.test.service.board.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class QuestionController {

    private final QuestionService service;

    @GetMapping("/questions")
    public ResponseEntity<List<QuestionForm>> questionList()
    {
        List<QuestionForm> questions = service.findAll().stream().map(QuestionForm :: make).toList();
        return ResponseEntity.ok().body(questions);
    }

    @GetMapping("/questions/{id}")
    public ResponseEntity<QuestionForm> question(@PathVariable("id") long id)
    {
        Question q = service.findQuestion(id);
        return ResponseEntity.ok().body(q.change());
    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<QuestionForm> updateQuestion(@RequestBody QuestionForm form, @PathVariable("id") long id)
    {
        Question q = service.update(form, id);
        return ResponseEntity.ok().body(q.change());
    }

    @PostMapping("/new_question")
    public ResponseEntity<Question> addQuestion(@RequestBody QuestionForm questionForm)
    {
        Question q = service.create(questionForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(q);
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable("id") long id)
    {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
