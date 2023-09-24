package com.example.test.controller.board;

import com.example.test.dao.Answer;
import com.example.test.dao.Question;
import com.example.test.dto.AnswerForm;
import com.example.test.repository.AnswerRepository;
import com.example.test.repository.QuestionRepository;
import com.example.test.service.AnswerService;
import com.example.test.service.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class AnswerControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    QuestionRepository questionRepo;

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;

    @Autowired
    AnswerRepository answerRepository;


    @BeforeEach
    public void mockMvcSetup()
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        questionRepo.deleteAll();



    }
    @Test
    void create() throws Exception{
        final String url = "/questions/answer/{id}";
        final String questionSubject = "questionSubject";
        final String questionContent = "questionContent";
        final String content = "My comment";

        final AnswerForm answerform = new AnswerForm(content,LocalDateTime.now());

        Question question = new Question(questionSubject, questionContent);
        question = questionRepo.save(question);

        final String request = objectMapper.writeValueAsString(answerform);

        ResultActions result = mockMvc.perform(post(url,question.getId()).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(request));


        result.andExpect(status().isCreated());
    }

    @Test
    void getAnswer() throws Exception{
        final String url = "/answers/{id}";


        Answer answer = answerRepository.findByContent("one").orElseThrow(() -> new IllegalArgumentException("not found: "));
        ResultActions result = mockMvc.perform(get(url,answer.getId()).contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").value("one"));

    }

    @Test
    void getAnswerList() throws Exception{
        final String url = "/{id}/answers";
        Question question1 = Question.builder()
                .time(LocalDateTime.now()).subject("question1").content("sdfefdf").build();
        Question question2 = Question.builder()
                .time(LocalDateTime.now()).subject("question1").content("sdfefdf").build();

        questionRepo.save(question1);questionRepo.save(question2);

        Answer answer1 = Answer.builder().content("one").q(question1).time(LocalDateTime.now()).build();
        Answer answer2 = Answer.builder().content("tow").q(question1).time(LocalDateTime.now()).build();
        Answer answer3 = Answer.builder().content("three").q(question1).time(LocalDateTime.now()).build();
        Answer answer4 = Answer.builder().content("four").q(question2).time(LocalDateTime.now()).build();
        Answer answer5 = Answer.builder().content("five").q(question2).time(LocalDateTime.now()).build();

        answerRepository.save(answer1);
        answerRepository.save(answer2);
        answerRepository.save(answer3);

        List<Answer> list = questionService.findAnswers(question1.getId());

        ResultActions result = mockMvc.perform(get(url, question1.getId()).contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        Assertions.assertThat(list.size()).isEqualTo(3);
    }

    @Test
    void updateAnswer() throws Exception{
        final String url = "/answers/{id}";
        final String newComment ="new_Comment";
        AnswerForm answerForm = new AnswerForm();
        answerForm.setContent(newComment);answerForm.setLocalDateTime(LocalDateTime.now());
        Answer answer = answerRepository.findByContent("one").orElseThrow(() -> new IllegalArgumentException("not found: "));

        final String request = objectMapper.writeValueAsString(answerForm);
        ResultActions result = mockMvc.perform(put(url,answer.getId()).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(request));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").value(newComment));
    }

    @Test
    void deleteAnswer() throws Exception{
        final String url = "/answers/{id}";
        Answer answer = answerRepository.findByContent("one").orElseThrow(() -> new IllegalArgumentException("not found: "));


        ResultActions result = mockMvc.perform(delete(url,answer.getId()));

        result.andExpect(status().isOk());



    }


}