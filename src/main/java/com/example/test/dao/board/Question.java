package com.example.test.dao.board;


import com.example.test.dto.QuestionForm;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Entity(name = "question")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @Builder
    public Question(String subject, String content,LocalDateTime time,List<Answer> answers)
    {
        this.subject = subject;
        this.content = content;
        this.createDate = time;

    }

    public QuestionForm change()
    {
        QuestionForm questionForm = new QuestionForm(subject, content);
        return questionForm;
    }
}
