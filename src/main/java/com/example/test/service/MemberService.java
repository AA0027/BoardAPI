package com.example.test.service;


import com.example.test.dao.Answer;
import com.example.test.dao.Member;
import com.example.test.dao.Question;
import com.example.test.dto.AnswerForm;
import com.example.test.dto.MemberForm;
import com.example.test.dto.QuestionForm;
import com.example.test.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService  {
    private final MemberRepository repo;

    public Member findMember(long id)
    {
        Member member = repo.findById(id).
                orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        return member;
    }

    public Member findMember(String email)
    {
        return repo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + email));
    }

    public Member create(MemberForm member)
    {
        Member m = Member.builder().name(member.getName())
                .email(member.getEmail())
                .addr(member.getAddr())
                .build();

        repo.save(m);
        return m;
    }

    public List<QuestionForm> findAllQuestion(String email)
    {
        List<Question> questions = repo.findQuestions(email).
                orElseThrow(() -> new IllegalArgumentException("not found: " + email));
        List<QuestionForm> list = questions.stream().map(Question::toForm).toList();
        return list;
    }

    public List<AnswerForm> findAllAnswer(String email)
    {
        List<Answer> answers = repo.findAnswers(email).
                orElseThrow(() -> new IllegalArgumentException("not found: " + email));
        List<AnswerForm> list = answers.stream().map(Answer::toForm).toList();
        return list;
    }


    public List<MemberForm> findMembers(){

        List<MemberForm> members = repo.findAll().stream().map(Member::toForm).toList();
        return members;
    }
    @Transactional
    public void updateMember(String name, MemberForm memberForm)
    {
        Member member = repo.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + name));
        member.setEmail(memberForm.getEmail());
        member.setAddr(member.getAddr());
        repo.save(member);
    }

    public void deleteMember(String name)
    {
        Member member = repo.findByName(name)
                        . orElseThrow(() -> new IllegalArgumentException("not found: " + name));
        repo.delete(member);
    }
}
