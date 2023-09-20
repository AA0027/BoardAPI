package com.example.test.controller.board;

import com.example.test.dao.board.Question;
import com.example.test.dto.QuestionForm;
import com.example.test.repository.QuestionRepository;
import com.example.test.service.board.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class QuestionControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    QuestionRepository repo;

    @Autowired
    QuestionService service;


    @BeforeEach
    public void mockMvcSetup()
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        repo.deleteAll();

    }
    @Test
    void questionList() throws Exception {
        final String url = "/board/questions";
        final String subject = "subject";
        final String content = "content";

        repo.save(Question.builder().subject(subject).content(content).time(LocalDateTime.now()).build());

        final ResultActions resultActions = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(content))
                .andExpect(jsonPath("$[0].subject").value(subject));
    }

    @DisplayName("Find one question")
    @Test
    void question() throws Exception{
        final String url = "/board/questions/{id}";
        final String subject = "subject";
        final String content = "content";

        Question question = repo.save(Question.builder().subject(subject).content(content)
                .time(LocalDateTime.now()).build());

        final ResultActions result = mockMvc.perform(get(url,question.getId()));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.subject").value(subject));
    }

    @Test
    @DisplayName("Change question!!")
    void updateQuestion() throws Exception{
        final String url = "/board/questions/{id}";
        final String subject = "subject";
        final String content = "content";
        final String newSubject = "newSubject";
        final String newContent = "newContent";


        Question question = repo.save(Question.builder().subject(subject).content(content)
                .time(LocalDateTime.now()).build());

        QuestionForm form = new QuestionForm(newSubject, newContent);
        question = service.update(form, question.getId());
        final String request = objectMapper.writeValueAsString(question.change());

        ResultActions result = mockMvc.perform(put(url,question.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(request));

        result
                .andExpect(status().isOk());
        assertThat(question.getSubject()).isEqualTo(newSubject);
        assertThat(question.getContent()).isEqualTo(newContent);
    }
    @DisplayName("Create Question!!!")
    @Test
    void addQuestion() throws Exception{
        final String url = "/board/new_question";
        final String subject = "subject";
        final String content = "content";
        final QuestionForm questionForm = new QuestionForm(subject, content);

        final String requestBody = objectMapper.writeValueAsString(questionForm);

        ResultActions result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        List<Question> questions = repo.findAll();
        result.andExpect(status().isCreated());
        assertThat(questions.size()).isEqualTo(1);
        assertThat(questions.get(0).getSubject()).isEqualTo(subject);
        assertThat(questions.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("Delete question")
    void deleteQuestion() throws Exception{

        final String url = "/board/questions/{id}";
        final String subject = "subject";
        final String content = "content";

        Question question = repo.save(Question.builder().subject(subject).content(content)
                .time(LocalDateTime.now()).build());
        ResultActions result = mockMvc.perform(delete(url,question.getId()));
        result.andExpect(status().isOk());

        List<Question> questions = repo.findAll();
        assertThat(questions).isEmpty();

    }
}