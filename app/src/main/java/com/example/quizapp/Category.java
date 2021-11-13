package com.example.quizapp;

import android.graphics.Bitmap;

public class Category {
    
    Bitmap image;
    String title;

    public Category(Bitmap image, String title) {
        this.image = image;
        this.title = title;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }
}
