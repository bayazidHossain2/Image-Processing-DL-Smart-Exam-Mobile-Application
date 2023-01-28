package com.example.smartexam.Model;

import android.graphics.Bitmap;

public class Option_Image {
    String number;
    Bitmap bitmap;
    String prediction;

    public Option_Image(String number, Bitmap bitmap, String prediction) {
        this.number = number;
        this.bitmap = bitmap;
        this.prediction = prediction;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }
}
