package com.example.quizapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class TimesUpDialog extends DialogFragment {

    OnRestartListener callback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (OnRestartListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.times_up_dialog, null, false);
        Button exitBtn = view.findViewById(R.id.exitButton);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
        });
        Button restartBtn = view.findViewById(R.id.restartButton);
        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.OnRestart(5, 0);
                dismiss();
            }
        });
        builder.setView(view);
        return builder.create();
    }
}

interface OnRestartListener {
    void OnRestart(int health, int index);
}