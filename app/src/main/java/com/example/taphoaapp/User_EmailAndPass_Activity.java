package com.example.taphoaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.taphoaapp.DetailProduct.DetailProductActivity;

public class User_EmailAndPass_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_email_and_pass);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(User_EmailAndPass_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        },3000);
    }
}