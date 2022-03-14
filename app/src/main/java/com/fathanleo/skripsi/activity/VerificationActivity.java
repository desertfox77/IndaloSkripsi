package com.fathanleo.skripsi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.fathanleo.skripsi.R;
import com.fathanleo.skripsi.databinding.ActivityVerificationBinding;

public class VerificationActivity extends AppCompatActivity {
    private ActivityVerificationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        binding = ActivityVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListerners();

//        if (Build.VERSION.SDK_INT >= 21) {
//            Window window = this.getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setStatusBarColor(this.getResources().getColor(R.color.black));
//        }
    }

    private void setListerners() {
        binding.textLoginV.setOnClickListener(view -> backToLogin());
        binding.buttonEmailApp.setOnClickListener(view -> {
            openEmailApp();
        });
    }

    private void backToLogin(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    private void openEmailApp() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "some@email.address" });
        intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
        intent.putExtra(Intent.EXTRA_TEXT, "mail body");
        startActivity(Intent.createChooser(intent, ""));
    }

}