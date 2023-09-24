package com.example.test.controller.board;

import com.example.test.dao.Answer;
import com.example.test.dao.Member;
import com.example.test.dao.Question;
import com.example.test.dto.MemberForm;
import com.example.test.repository.AnswerRepository;
import com.example.test.repository.MemberRepository;
import com.example.test.repository.QuestionRepository;
import com.example.test.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    MemberService service;

    @Autowired
    MemberRepository repo;

    @Autowired
    QuestionRepository questionRepo;

    @Autowired
    AnswerRepository answerRepo;

    @BeforeEach
    public void mockMvcSetup()
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        repo.deleteAll();

    }

    @Test
    void createMember() throws Exception{
        String url = "/members";
        String name = "changsung";
        String addr = "Seoul";
        String email = "tmxk1266@naver.com";

        MemberForm memberForm = MemberForm.builder()
                .name(name)
                .email(email)
                .addr(addr)
                .build();

        String request = objectMapper.writeValueAsString(memberForm);
        ResultActions result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(request));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void deleteMember() throws Exception{
        String url = "/members/{email}";

        String name = "changsung";
        String addr = "Seoul";
        String email = "tmxk1266@naver.com";

        MemberForm memberForm = MemberForm.builder()
                .name(name)
                .email(email)
                .addr(addr)
                .build();

        Member member = service.create(memberForm);
        ResultActions result = mockMvc.perform(delete(url,member.getEmail()));

        List<Member> members = repo.findAll();
        result.andExpect(status().isOk());

        Assertions.assertThat(members).isEmpty();
    }

    @Test
    void getMembers() throws Exception{
        String url = "/members";
        String name = "changsung";
        String addr = "Seoul";
        String email = "tmxk1266@naver.com";

        MemberForm memberForm = MemberForm.builder()
                .name(name)
                .email(email)
                .addr(addr)
                .build();

        Member member = service.create(memberForm);

        ResultActions result = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        List<Member> members = repo.findAll();

        Assertions.assertThat(members.size()).isEqualTo(1);
    }

    @Test
    void getMember() throws Exception{
        String url = "/members/{email}";
        String name = "changsung";
        String addr = "Seoul";
        String email = "tmxk1266@naver.com";

        MemberForm memberForm = MemberForm.builder()
                .name(name)
                .email(email)
                .addr(addr)
                .build();
        Member member = service.create(memberForm);

        ResultActions result = mockMvc.perform(get(url,member.getEmail()).contentType(MediaType.APPLICATION_JSON_VALUE));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void getQuestions() throws Exception{
        String url = "/members/{email}/questions";
        String name = "changsung";
        String addr = "Seoul";
        String email = "tmxk1266@naver.com";

        MemberForm memberForm = MemberForm.builder()
                .name(name)
                .email(email)
                .addr(addr)
                .build();
        Member member = service.create(memberForm);

        for(int i=0;i<3;i++) {
            Question q = Question.builder().member(member).content("sdfdf" + i)
                    .subject("aaa" + i).time(LocalDateTime.now()).build();

            questionRepo.save(q);
        }

        ResultActions result = mockMvc.perform(get(url,member.getEmail()).contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$[1].subject").value("aaa1"));
    }

    @Test
    void getAnswers() throws Exception{
        String url = "/members/{email}/answers";
        String name = "changsung";
        String addr = "Seoul";
        String email = "tmxk1266@naver.com";

        MemberForm memberForm = MemberForm.builder()
                .name(name)
                .email(email)
                .addr(addr)
                .build();
        Member member = service.create(memberForm);

        for(int i=0;i<3;i++) {
            Answer q = Answer.builder().member(member).content("sdfdf" + i)
                    .time(LocalDateTime.now()).build();

            answerRepo.save(q);
        }

        ResultActions result = mockMvc.perform(get(url,member.getEmail()).contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$[1].content").value("sdfdf1"));
    }

    @Test
    void updateMember() throws Exception{
        String url = "/members/{email}";
        String name = "changsung";
        String addr = "Seoul";
        String email = "tmxk1266@naver.com";

        String new_addr = "Busan";
        String new_email = "whckdtjd456@naver.com";

        MemberForm memberForm = MemberForm.builder()
                .name(name)
                .email(email)
                .addr(addr)
                .build();
        Member member = service.create(memberForm);

        MemberForm newForm = MemberForm.builder()
                .name(name)
                .addr(new_addr).email(new_email).build();
        service.updateMember(member, newForm);

        String request = objectMapper.writeValueAsString(newForm);

        ResultActions result = mockMvc.perform(put(url,member.getName())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(request));

        result.andExpect(status().isOk()).andExpect(jsonPath("$.addr").value(new_addr));
    }
}