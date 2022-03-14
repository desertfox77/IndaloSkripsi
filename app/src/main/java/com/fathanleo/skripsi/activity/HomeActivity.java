package com.fathanleo.skripsi.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fathanleo.skripsi.R;
import com.fathanleo.skripsi.databinding.ActivityHomeBinding;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;



public class HomeActivity extends AppCompatActivity {

    private String userID;
    private ActivityHomeBinding binding;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                HomeActivity.this, R.style.BottomSheetDialogTheme
        );
        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.layout_bottom_sheet,
                (LinearLayout)findViewById(R.id.bottomSheetContainer)
        );

        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userID = user.getUid();
        firebaseFirestore.collection("users")
                .whereEqualTo("userID", userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && task.getResult()!=null){
                            for(QueryDocumentSnapshot doc : task.getResult()){
                                binding.textLoginH.setText(getString(R.string.opener) + " "+ doc.getString("name"));
                            }
                        }
                    }
                });



        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.menuHome:
                        break;

                    case R.id.menuContribute:
                        Intent intent = new Intent(HomeActivity.this, ContributeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.menuNews:

                        break;

                    case R.id.menuUser:

                        bottomSheetView.findViewById(R.id.buttonSignOutB).setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View view){
                                showToast("Sign out successful");
                                FirebaseAuth.getInstance().signOut();
                                bottomSheetDialog.dismiss();
                                Intent intent = new Intent(HomeActivity.this, OpenActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        });

                        bottomSheetView.findViewById(R.id.history).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bottomSheetDialog.dismiss();
                                Intent intent = new Intent(HomeActivity.this, HistoryActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        bottomSheetDialog.setContentView(bottomSheetView);
                        bottomSheetDialog.show();

                        break;
                }


                return true;
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}


