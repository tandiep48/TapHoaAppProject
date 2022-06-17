package com.example.taphoaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.login.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ChangePasswordAtLoginActivity extends AppCompatActivity {
    private EditText editText;
    private Button btnResetPass;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_passord_at_login_page);
        editText = findViewById(R.id.etEmail);
        btnResetPass = findViewById(R.id.btnSubmit);
        auth = FirebaseAuth.getInstance();
        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetpassword();
            }
        });
    }

    private void resetpassword() {
        String email = editText.getText().toString().trim();
        if (email.isEmpty())
        {
            editText.setError("Email is required!");
            editText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editText.setError("Please provide valid email!");
            editText.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(ChangePasswordAtLoginActivity.this,"Check your email to reset your password", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ChangePasswordAtLoginActivity.this, LoginActivity.class));
                }else
                {
                    Toast.makeText(ChangePasswordAtLoginActivity.this, "Error!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
