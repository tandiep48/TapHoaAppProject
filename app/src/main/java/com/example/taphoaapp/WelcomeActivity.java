package com.example.taphoaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.taphoaapp.profile.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {
    Timer timer;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firestore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        timer = new Timer();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String PassPassword= sharedPreferences.getString(mAuth.getCurrentUser().getUid(), "123456");//load it from SharedPref

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(firebaseUser != null)
                {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    intent.putExtra("PrevActive", "WelcomeActivity");
                    intent.putExtra("password", PassPassword);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    intent.putExtra("PrevActive", "WelcomeActivity");
                    startActivity(intent);
                    finish();
                }
            }
        },3000);
    }
}