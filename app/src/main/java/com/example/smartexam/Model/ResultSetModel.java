package com.example.smartexam.Model;

public class ResultSetModel {
    private String examName;
    private int minResultId;
    private int maxResultId;

    public ResultSetModel(String examName, int minResultId, int maxResultId) {
        this.examName = examName;
        this.minResultId = minResultId;
        this.maxResultId = maxResultId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public int getMinResultId() {
        return minResultId;
    }

    public void setMinResultId(int minResultId) {
        this.minResultId = minResultId;
    }

    public int getMaxResultId() {
        return maxResultId;
    }

    public void setMaxResultId(int maxResultId) {
        this.maxResultId = maxResultId;
    }
}
