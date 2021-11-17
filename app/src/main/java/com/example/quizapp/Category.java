package com.example.quizapp;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Category {

    int id;
    Bitmap image;
    String title;

    public Category(int id, Bitmap image, String title) {
        this.id = id;
        this.image = image;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

}
