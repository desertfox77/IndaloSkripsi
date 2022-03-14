package com.fathanleo.skripsi.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import android.widget.EditText;

import android.widget.Toast;

import com.fathanleo.skripsi.R;
import com.fathanleo.skripsi.databinding.ActivityLoginBinding;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        setListerners();
//        register =(TextView) findViewById(R.id.textCreateNewAccount);
//        register.setOnClickListener(this);
//
//        buttonLogin = (Button) findViewById(R.id.buttonLoginL);
//        buttonLogin.setOnClickListener(this);


    }

    private void setListerners() {
        binding.textCreateNewAccount.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),
                RegistrationActivity.class)));
        binding.buttonLoginL.setOnClickListener(view -> {
            if(isValidSignInDetails()){
                signIn();
            }
        });
        binding.textForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Forgot Password?");
                passwordResetDialog.setMessage("Enter Your Email To Received Reset Password Link");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //membuka dialog
                        String mail = resetMail.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                showToast("Reset Link has been sent to your email");
                            }
                        }) .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                showToast("Error, please try again");
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //closing dialog
                    }
                });
                passwordResetDialog.create().show();
            }
        });
    }

    private void signIn(){
        loadingLogin(true);

        mAuth.signInWithEmailAndPassword(binding.inputEmailL.getText().toString(), binding.inputPasswordL.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        if(task.isSuccessful() && firebaseUser!=null && firebaseUser.isEmailVerified() && task.getResult()!=null){

                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        }else if (firebaseUser!=null && !firebaseUser.isEmailVerified()){
                            loadingLogin(false);
                            showToast("Please verify your email");
                        }else{
                            loadingLogin(false);
                            showToast("Login failed, please try again");
                        }
                    }
                });


    }


    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private Boolean isValidSignInDetails(){
        if(binding.inputEmailL.getText().toString().trim().isEmpty()){
            showToast("Enter email");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.inputEmailL.getText().toString()).matches()){
            showToast("Enter valid email");
            return false;
        } else if (binding.inputPasswordL.getText().toString().trim().isEmpty()){
            showToast(("Enter password"));
            return false;
        } else {
            return true;
        }
    }

    private void loadingLogin(Boolean isLoading){
        if(isLoading){
            binding.buttonLoginL.setVisibility(View.INVISIBLE);
            binding.progressBarL.setVisibility(View.VISIBLE);
        }else {
            binding.progressBarL.setVisibility(View.INVISIBLE);
            binding.buttonLoginL.setVisibility(View.VISIBLE);
        }
    }
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.textCreateNewAccount:
//                startActivity(new Intent(this, RegistrationActivity.class));
//                break;
//            case R.id.buttonLoginL:
//                startActivity(new Intent(this, HomeActivity.class));
//                break;
//        }
//    }
}