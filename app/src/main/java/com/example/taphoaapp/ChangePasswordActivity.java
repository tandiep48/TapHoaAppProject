package com.example.taphoaapp;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.Tag;

public class ChangePasswordActivity extends AppCompatActivity {
    private String order, userID, confirmUserID, userPass, userMail;
    Button btnChangePass;
    EditText editTextNewPassword, editTextCNewPassword, editTextOldPassword;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    AuthCredential authCredential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        editTextOldPassword = findViewById(R.id.tvPassword);
        editTextNewPassword = findViewById(R.id.tvNewPassword);
        editTextCNewPassword = findViewById(R.id.tvConfirmPassword);
        btnChangePass = findViewById(R.id.btnChangePassword);

        Intent i = getIntent();
        Bundle extras = getIntent().getExtras();
        if ( i!= null &&extras != null) {
            order = extras.getString("Order");
            userID = i.getStringExtra("userID");
        }
        confirmUserID = firebaseAuth.getCurrentUser().getUid();

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userID == confirmUserID)
                {
                    Boolean Run = true;
                    userMail = firebaseUser.getEmail().trim();
                    userPass = "987654321";
                    authCredential = EmailAuthProvider.getCredential(userMail,userPass);
                    String newpassword = editTextNewPassword.getText().toString().trim();
                    String newcpassword = editTextCNewPassword.getText().toString().trim();
                    if(newpassword.isEmpty())
                    {
                        editTextNewPassword.setError("Password must not empty");
                        editTextNewPassword.requestFocus();
                        Run = false;
                        return;
                    }
                    if(newcpassword.isEmpty())
                    {
                        editTextCNewPassword.setError("Password must match");
                        editTextCNewPassword.requestFocus();
                        Run = false;
                        return;
                    }
                    if(newpassword!=newcpassword)
                    {
                        editTextNewPassword.setError("Password must not empty");
                        editTextNewPassword.requestFocus();
                        editTextCNewPassword.setError("Password must match");
                        editTextCNewPassword.requestFocus();
                        Run = false;
                        return;
                    }
                    if(Run==true) {
                        firebaseUser.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    firebaseUser.updatePassword(newpassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "Password updated");
                                            }else { Log.d(TAG, "Error password not updated"); }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}
