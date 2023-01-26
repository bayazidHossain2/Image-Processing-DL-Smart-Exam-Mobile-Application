package com.example.smartexam.Model;

import android.graphics.Bitmap;

public class Option_Image {
    String number;
    Bitmap bitmap;

    public Option_Image(String number, Bitmap bitmap) {
        this.number = number;
        this.bitmap = bitmap;
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
}
