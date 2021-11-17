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

        categoryList.add(new Category(12, BitmapFactory.decodeResource(getResources(), R.drawable.music), "Music"));
        categoryList.add(new Category(21, BitmapFactory.decodeResource(getResources(), R.drawable.sport), "Sports"));
        categoryList.add(new Category(18, BitmapFactory.decodeResource(getResources(), R.drawable.tech), "Technology"));
        categoryList.add(new Category(15, BitmapFactory.decodeResource(getResources(), R.drawable.game), "Video Games"));
        categoryList.add(new Category(9, BitmapFactory.decodeResource(getResources(), R.drawable.science), "General Knowledge"));
        categoryList.add(new Category(25, BitmapFactory.decodeResource(getResources(), R.drawable.art), "Art"));
        categoryList.add(new Category(17, BitmapFactory.decodeResource(getResources(), R.drawable.nature), "Nature"));

        RecyclerView categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categoryList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}