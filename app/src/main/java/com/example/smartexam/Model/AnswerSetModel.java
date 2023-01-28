package com.example.smartexam.Model;

public class AnswerSetModel {
    private String examName;
    private int minId,maxId;

    public AnswerSetModel(){}

    public AnswerSetModel(String examName, int minId, int maxId) {
        this.examName = examName;
        this.minId = minId;
        this.maxId = maxId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public int getMinId() {
        return minId;
    }

    public void setMinId(int minId) {
        this.minId = minId;
    }

    public int getMaxId() {
        return maxId;
    }

    public void setMaxId(int maxId) {
        this.maxId = maxId;
    }
}
