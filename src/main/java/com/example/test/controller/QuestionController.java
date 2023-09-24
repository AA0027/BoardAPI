package com.example.test.controller;

import com.example.test.dao.Member;
import com.example.test.dao.Question;
import com.example.test.dto.MemberForm;
import com.example.test.dto.QuestionForm;
import com.example.test.service.MemberService;
import com.example.test.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class QuestionController {

    private final QuestionService questionService;
    private final MemberService memberService;
    //모든 질문 찾기
    @GetMapping("/questions")
    public ResponseEntity<List<QuestionForm>> questionList()
    {
        List<QuestionForm> questions = questionService.findAll().stream().map(QuestionForm :: make).toList();
        return ResponseEntity.ok().body(questions);
    }
    //원하는 질문 찾기
    @GetMapping("/questions/{id}")
    public ResponseEntity<QuestionForm> question(@PathVariable("id") long id)
    {
        Question q = questionService.findQuestion(id);
        return ResponseEntity.ok().body(q.toForm());
    }

    //질문 수정하기
    @PutMapping("/questions/{id}")
    public ResponseEntity<QuestionForm> updateQuestion(@RequestBody QuestionForm form, @PathVariable("id") long id)
    {
        Question q = questionService.update(form, id);
        return ResponseEntity.ok().body(q.toForm());
    }

    //질문생성하기
    @PostMapping("/new_question")
    public ResponseEntity<Question> addQuestion(@RequestBody MemberForm memberForm, @RequestBody QuestionForm questionForm)
    {
        Member member = memberService.findMember(memberForm.getEmail());
        Question q = questionService.create(member, questionForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(q);
    }
   // 질문 삮제하기
    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable("id") long id)
    {
        questionService.delete(id);
        return ResponseEntity.ok().build();
    }
}
