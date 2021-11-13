package com.example.quizapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    List<Category> data;

    public CategoryAdapter(Context context, List<Category> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bindData(data.get(position), position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        ConstraintLayout constraintLayout;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            constraintLayout = itemView.findViewById(R.id.cardConstraintLayout);
            image = itemView.findViewById(R.id.categoryImageView);
            title = itemView.findViewById(R.id.categoryTitleTextView);
        }

        public void bindData(Category category, int position) {
            List<Drawable> backgrounds = new ArrayList<>();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                backgrounds.add(context.getDrawable(R.drawable.gradient_1));
                backgrounds.add(context.getDrawable(R.drawable.gradient_2));
                backgrounds.add(context.getDrawable(R.drawable.gradient_3));
                backgrounds.add(context.getDrawable(R.drawable.gradient_4));
                backgrounds.add(context.getDrawable(R.drawable.gradient_5));
                backgrounds.add(context.getDrawable(R.drawable.gradient_6));
                backgrounds.add(context.getDrawable(R.drawable.gradient_7));
            }
            constraintLayout.setBackground(backgrounds.get(position));
            Glide.with(context)
                    .load(category.getImage())
                    .into(image);
            title.setText(category.getTitle());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, QuestionActivity.class);
                    intent.putExtra("category", category.getTitle());
                    context.startActivity(intent);
                }
            });
        }
    }
}
