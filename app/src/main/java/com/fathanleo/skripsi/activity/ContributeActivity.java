package com.fathanleo.skripsi.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fathanleo.skripsi.R;
import com.fathanleo.skripsi.databinding.ActivityContributeBinding;
import com.fathanleo.skripsi.utilities.MyAdapter;
import com.fathanleo.skripsi.utilities.MyModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class ContributeActivity extends AppCompatActivity {
    private String userID;
    private ViewPager viewPager;
    private ActivityContributeBinding binding;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser user;
    private ArrayList modelArrayList;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribute);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                ContributeActivity.this, R.style.BottomSheetDialogTheme
        );
        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.layout_bottom_sheet,
                (LinearLayout)findViewById(R.id.bottomSheetContainer)
        );

        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        binding = ActivityContributeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userID = user.getUid();
        firebaseFirestore.collection("users")
                .whereEqualTo("userID", userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && task.getResult()!=null){
                            for(QueryDocumentSnapshot doc : task.getResult() ){
                                binding.textLoginC.setText(getString(R.string.opener) + " "+ doc.getString("name"));
                            }
                        }
                    }
                });

//        viewPager = findViewById(R.id.locationC);
//        loadCards();
        ViewPager2 locationsViewPager = findViewById(R.id.locationC);
        List<ImageLocation> imageLocationList = new ArrayList<>();

        ImageLocation imageLocationSatu = new ImageLocation();
        imageLocationSatu.imageUrl = "https://upload.wikimedia.org/wikipedia/commons/d/d0/Sonneratia_alba_-_Manado_%282%29.JPG";
        imageLocationSatu.title = "1 bulan";
        imageLocationSatu.harga = "Rp. 30.000";
        imageLocationList.add(imageLocationSatu);

        ImageLocation imageLocationDua = new ImageLocation();
        imageLocationDua.imageUrl = "https://www.natamagazine.co/wp-content/uploads/2020/01/25838_r646x485.jpg";
        imageLocationDua.title = "6 bulan";
        imageLocationDua.harga = "Rp. 150.000";
        imageLocationList.add(imageLocationDua);

        ImageLocation imageLocationTiga = new ImageLocation();
        imageLocationTiga.imageUrl = "https://www.harianatjeh.com/files/images/20201106-94519a0d5ef568767e4600cfad3b735a1bab34aa.jpeg";
        imageLocationTiga.title = "1 tahun";
        imageLocationTiga.harga = "Rp. 300.000";
        imageLocationList.add(imageLocationTiga);

        locationsViewPager.setAdapter(new ImageLocationAdapter(imageLocationList));


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.menuHome:
                        Intent intent = new Intent(ContributeActivity.this, HomeActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.menuContribute:

                        break;

                    case R.id.menuNews:

                        break;

                    case R.id.menuUser:

                        bottomSheetView.findViewById(R.id.buttonSignOutB).setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View view){
                                showToast("Sign out successful");
                                bottomSheetDialog.dismiss();
                                Intent intent = new Intent(ContributeActivity.this, OpenActivity.class);
                                startActivity(intent);

                            }
                        });

                        bottomSheetView.findViewById(R.id.history).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bottomSheetDialog.dismiss();
                                Intent intent = new Intent(ContributeActivity.this, HistoryActivity.class);
                                startActivity(intent);
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

    private void loadCards(){
        modelArrayList = new ArrayList<>();

        modelArrayList.add(new MyModel(
           "1 Bulan",
           "Rp. 30.000",
                R.drawable.img
        ));
        modelArrayList.add(new MyModel(
                "6 Bulan",
                "Rp. 150.000",
                R.drawable.img
        ));
        modelArrayList.add(new MyModel(
                "1 Tahun",
                "Rp. 300.000",
                R.drawable.img
        ));


        myAdapter = new MyAdapter(this, modelArrayList);
        viewPager.setAdapter(myAdapter);
        viewPager.setPadding(100, 0, 0, 0);

    }
}



//
