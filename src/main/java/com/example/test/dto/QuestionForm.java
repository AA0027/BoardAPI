package com.example.test.dto;

import com.example.test.dao.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuestionForm {
    private String subject;
    private String content;


    public static QuestionForm make(Question q)
    {
        QuestionForm question = new QuestionForm();
        question.setContent(q.getContent());
        question.setSubject(q.getSubject());
        return question;
    }
    public Question toEntity()
    {
        return Question.builder().subject(subject)
                .content(content).time(LocalDateTime.now()).build();
    }
}
