package com.fathanleo.skripsi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.fathanleo.skripsi.R;
import com.fathanleo.skripsi.databinding.ActivityHistoryBinding;


public class HistoryActivity extends AppCompatActivity {
    private ActivityHistoryBinding activityHistoryBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        activityHistoryBinding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(activityHistoryBinding.getRoot());

        activityHistoryBinding.historyBack.setOnClickListener(
                view -> { Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                }
        );
    }
}