package com.example.test.dao;


import com.example.test.dto.QuestionForm;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Entity(name = "question")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Member member;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    public Question(String subject, String content, Member member)
    {
        this.subject = subject;
        this.content = content;
        this.member = member;
        this.createDate = LocalDateTime.now();
    }
    @Builder
    public Question(String subject, String content,LocalDateTime time,Member member)
    {
        this.subject = subject;
        this.content = content;
        this.createDate = time;
        this.member = member;

    }

    public QuestionForm toForm()
    {
        QuestionForm questionForm = new QuestionForm(subject, content);
        return questionForm;
    }
}
