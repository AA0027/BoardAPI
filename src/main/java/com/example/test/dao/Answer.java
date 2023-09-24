package com.example.test.dao;

import com.example.test.dto.AnswerForm;
import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "answer")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
public class Answer {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne()
    private Question question;

    @ManyToOne()
    private Member member;
    @Builder
    public Answer(String content,LocalDateTime time,Question q,Member member)
    {
        this.content = content;
        this.createDate = time;
        this.question = q;
        this.member = member;
    }

    public AnswerForm toForm()
    {
        AnswerForm answerForm = new AnswerForm();
        answerForm.setContent(content);
        answerForm.setLocalDateTime(createDate);
        return answerForm;
    }
}
