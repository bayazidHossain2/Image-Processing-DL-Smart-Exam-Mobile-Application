package com.example.smartexam.Model;

public class AnswerModel {
    private int Id,question_no;
    private String uid;
    private String option;

    public AnswerModel(int id, String option) {
        Id = id;
        this.option = option;
    }

    public AnswerModel(int id, String uid, int question_no, String option) {
        Id = id;
        this.question_no = question_no;
        this.uid = uid;
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

    public int getQuestion_no() {
        return question_no;
    }

    public void setQuestion_no(int question_no) {
        this.question_no = question_no;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
