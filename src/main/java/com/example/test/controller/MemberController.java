package com.example.test.controller;

import com.example.test.dao.Member;
import com.example.test.dto.AnswerForm;
import com.example.test.dto.MemberForm;
import com.example.test.dto.QuestionForm;
import com.example.test.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    //새로운 멤버 생성
    @PostMapping("/members")
    public ResponseEntity<MemberForm> createMember(@RequestBody MemberForm memberForm)
    {
        Member response = memberService.create(memberForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(response.toForm());
    }

    //멤버 삮제
    @DeleteMapping("/members/{email}")
    public ResponseEntity<Void> deleteMember(@PathVariable("email") String email)
    {
        Member member = memberService.findMember(email);
        memberService.deleteMember(member.getName());
        return ResponseEntity.ok().build();

    }

    //모든 멤버 검색
    @GetMapping
    public ResponseEntity<List<MemberForm>> getMembers()
    {
        List<MemberForm> response = memberService.findMembers();
        return ResponseEntity.ok().body(response);
    }

    //email로 멤버 검색
    @GetMapping("/members/{email}")
    public ResponseEntity<MemberForm> getMember(@PathVariable("email") String email)
    {
        Member response = memberService.findMember(email);
        return ResponseEntity.ok().body(response.toForm());
    }

    //멤버가작성한 모든 질문검색
    @GetMapping("/members/{email}/questions")
    public  ResponseEntity<List<QuestionForm>> getQuestions(@PathVariable("email") String email)
    {
        List<QuestionForm> response = memberService.findAllQuestion(email);
        return ResponseEntity.ok().body(response);
    }


    //멤버가 작성한 모든 답변검색
    @GetMapping("/members/{email}/answers")
    public  ResponseEntity<List<AnswerForm>> getAnswers(@PathVariable("email") String email)
    {
        List<AnswerForm> response = memberService.findAllAnswer(email);
        return ResponseEntity.ok().body(response);
    }

    //멤버 정보 변경
    @PutMapping("/members/{email}")
    public ResponseEntity<MemberForm> updateMember(@RequestBody MemberForm memberForm, @PathVariable("email") String email)
    {
        Member response = memberService.findMember(email);
        memberService.updateMember(response.getName(), memberForm);
        return ResponseEntity.ok().body(response.toForm());
    }


}
