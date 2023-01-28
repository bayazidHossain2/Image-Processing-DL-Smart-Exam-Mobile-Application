package com.example.smartexam.Model;

public class ResultModel {
    private int id;
    private String uid;
    private int roll;
    private int min_ans_id;
    private int max_ans_id;
    private int totalMatch;

    public ResultModel(int id, String uid, int roll, int min_ans_id, int max_ans_id, int totalMatch) {
        this.id = id;
        this.uid = uid;
        this.roll = roll;
        this.min_ans_id = min_ans_id;
        this.max_ans_id = max_ans_id;
        this.totalMatch = totalMatch;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public int getMin_ans_id() {
        return min_ans_id;
    }

    public void setMin_ans_id(int min_ans_id) {
        this.min_ans_id = min_ans_id;
    }

    public int getMax_ans_id() {
        return max_ans_id;
    }

    public void setMax_ans_id(int max_ans_id) {
        this.max_ans_id = max_ans_id;
    }

    public int getTotalMatch() {
        return totalMatch;
    }

    public void setTotalMatch(int totalMatch) {
        this.totalMatch = totalMatch;
    }
}
