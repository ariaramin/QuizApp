package com.example.quizapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.airbnb.lottie.LottieAnimationView;

public class AppDialog extends DialogFragment {

    OnRestartListener callback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (OnRestartListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        int STATUS_ID = 0;

        if (bundle != null) {
            STATUS_ID = bundle.getInt("status");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.quiz_dialog, null, false);
        LottieAnimationView animation = view.findViewById(R.id.lottieAnimationView);
        switch (STATUS_ID){
            case 1:
                animation.setAnimation(R.raw.lf30_editor_vy5spujr);
                break;
            case 2:
                animation.setAnimation(R.raw.game_over);
                animation.loop(false);
                break;
            case 3:
                animation.setAnimation(R.raw.trophy);
                animation.loop(false);
                break;
        }

        Button exitBtn = view.findViewById(R.id.exitButton);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        Button restartBtn = view.findViewById(R.id.restartButton);
        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.OnRestart(3, 0);
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