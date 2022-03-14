package com.fathanleo.skripsi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;


import com.fathanleo.skripsi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class OpenActivity extends AppCompatActivity {
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent = new Intent(OpenActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(OpenActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                //Create a new Intent to go from Class B to Class C and start the new Activity.

            }
            //Here after the comma you specify the amount of time you want the screen to be delayed. 5000 is for 5 seconds.
        }, 1000);

    }
}