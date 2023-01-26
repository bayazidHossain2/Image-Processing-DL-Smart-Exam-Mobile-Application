package com.example.smartexam.Model;

public class AnswerModel {
    private int Id;
    private String option;

    public AnswerModel(int id, String option) {
        Id = id;
        this.option = option;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
