package com.fathanleo.skripsi.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.fathanleo.skripsi.R;
import com.fathanleo.skripsi.databinding.ActivityRegistrationBinding;
import com.fathanleo.skripsi.utilities.Constants;
import com.fathanleo.skripsi.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding binding;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setListeners();
        mAuth = FirebaseAuth.getInstance();


    }

    private void setListeners(){
        binding.textLogin.setOnClickListener(view -> onBackPressed());
        binding.buttonSignup.setOnClickListener(view -> {
            if (isValidSignUpDetails()){
                if(!binding.inputEmailR.getText().toString().trim().isEmpty()){
                    mAuth.fetchSignInMethodsForEmail(binding.inputEmailR.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                @Override
                                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                    if(task.isSuccessful() && task.getResult()!=null && task.getResult().getSignInMethods()!= null && !task.getResult().getSignInMethods().isEmpty()){
                                        showToast("Email already created");
                                    }else{
                                        signUp();
                                    }

                                }
                            });

                }

            }
        });
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void signUp() {
        loadingRegistration(true);
        FirebaseFirestore databaseUser = FirebaseFirestore.getInstance();
        HashMap<String,Object> user = new HashMap<>();
        mAuth.createUserWithEmailAndPassword(binding.inputEmailR.getText().toString(), binding.inputPasswordR.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        if (task.isSuccessful() && firebaseUser!=null){
                            user.put(Constants.KEY_USER_ID,firebaseUser.getUid());
                            user.put(Constants.KEY_NAME, binding.inputNameR.getText().toString());
                            user.put(Constants.KEY_EMAIL, binding.inputEmailR.getText().toString());
                            user.put(Constants.KEY_PASSWORD, binding.inputPasswordR.getText().toString());
                            databaseUser.collection(Constants.KEY_COLLECTION_USERS)
                                    .add(user);
                            loadingRegistration(false);

                        firebaseUser.sendEmailVerification();
                            showToast("Verification Email has been sent");
                            Intent intent = new Intent(getApplicationContext(), VerificationActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);


                    }else{
                        loadingRegistration(false);
                        showToast("Registration failed, please try again");
                    }
                    }
                });





    }

    private Boolean isValidSignUpDetails(){

        if (binding.inputNameR.getText().toString().trim().isEmpty()) {
            showToast("Enter Name");
            return false;
        } else if (binding.inputEmailR.getText().toString().trim().isEmpty()) {
            showToast("Enter email");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.inputEmailR.getText().toString()).matches()) {
            showToast("Enter valid email!");
            return false;
        } else if (binding.inputPasswordR.getText().toString().trim().isEmpty()) {
            showToast("Enter password");
            return false;
        } else if (binding.inputPasswordR.getText().toString().trim().length()<8) {
            showToast("Password at least 8 character");
            return false;
        } else if (!binding.inputPasswordR.getText().toString().trim().matches(".*[A-Z].*")) {
            showToast("Password at least have 1 capital letter");
            return false;
        } else if (!binding.inputPasswordR.getText().toString().trim().matches(".*[0-9].*")) {
            showToast("Password at least have  1 number");
            return false;
        }   else if (binding.inputConfirmPasswordR.getText().toString().trim().isEmpty()) {
            showToast("Confirm your password");
            return false;
        } else if (!binding.inputPasswordR.getText().toString().equals(binding.inputConfirmPasswordR.getText().toString())) {
            showToast("Password & confirm password must be same");
            return false;
        } else if (!binding.inputPasswordR.getText().toString().equals(binding.inputConfirmPasswordR.getText().toString())) {
            showToast("Password & confirm password must be same");
            return false;
        } else {
            return true;
        }
    }

    private void loadingRegistration(Boolean isLoading){
        if(isLoading){
            binding.buttonSignup.setVisibility(View.INVISIBLE);
            binding.progressBarR.setVisibility(View.VISIBLE);
        }else {
            binding.progressBarR.setVisibility(View.INVISIBLE);
            binding.buttonSignup.setVisibility(View.VISIBLE);
        }
    }
}