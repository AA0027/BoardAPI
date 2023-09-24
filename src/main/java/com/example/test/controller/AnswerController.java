package com.example.test.controller;

import com.example.test.dao.Answer;
import com.example.test.dao.Member;
import com.example.test.dao.Question;
import com.example.test.dto.AnswerForm;
import com.example.test.dto.MemberForm;
import com.example.test.service.AnswerService;
import com.example.test.service.MemberService;
import com.example.test.service.QuestionService;
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
    private final MemberService memberService;
    @PostMapping("/questions/answer/{id}")
    public ResponseEntity<AnswerForm> create(@PathVariable("id") long id,
                                             @RequestBody AnswerForm answerForm,
                                             @RequestBody MemberForm memberForm)
    {
        Member member = memberService.findMember(memberForm.getEmail());
        Question question = questionService.findQuestion(id);

        Answer answer = answerService.create(member, question, answerForm);


        return ResponseEntity.status(HttpStatus.CREATED).body(answer.toForm());
    }

//답변 찾기
    @GetMapping("/answers/{id}")
    public ResponseEntity<AnswerForm> getAnswer(@PathVariable("id") long id)
    {
        Answer answer = answerService.findAnswer(id);
        return ResponseEntity.ok().body(answer.toForm());
    }
    //질문에 달린 모든 답변 찾기
    @GetMapping("/{id}/answers")
    public ResponseEntity<List<AnswerForm>> getAnswerList(@PathVariable("id") long id)
    {
        List<Answer> answerList = questionService.findAnswers(id);
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
