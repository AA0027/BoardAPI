package com.example.test.dto;

public enum Region {
    KR("korea"),
    JP("japan"),
    US("usa");

    private final String value;

    Region(String value){
        this.value = value;

    }

    public String getValue(){
        return value;
    }
}
