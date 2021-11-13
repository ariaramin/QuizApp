package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Category> categoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoryList.add(new Category(BitmapFactory.decodeResource(getResources(), R.drawable.food), "Food and Drink"));
        categoryList.add(new Category(BitmapFactory.decodeResource(getResources(), R.drawable.music), "Music"));
        categoryList.add(new Category(BitmapFactory.decodeResource(getResources(), R.drawable.logos), "Logo"));
        categoryList.add(new Category(BitmapFactory.decodeResource(getResources(), R.drawable.science), "Science"));
        categoryList.add(new Category(BitmapFactory.decodeResource(getResources(), R.drawable.sport), "Athletic"));
        categoryList.add(new Category(BitmapFactory.decodeResource(getResources(), R.drawable.art), "Art"));
        categoryList.add(new Category(BitmapFactory.decodeResource(getResources(), R.drawable.tech), "Technology"));

        RecyclerView categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categoryList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}