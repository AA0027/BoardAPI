package com.example.test.dto;

import lombok.Data;

@Data
public class AnswerForm {
    private String content;

    public AnswerForm(String content)
    {
        this.content = content;
    }
}
