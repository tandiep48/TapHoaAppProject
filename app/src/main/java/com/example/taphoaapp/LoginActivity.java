package com.example.taphoaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RC_SIGN_IN = 1000;
    private FirebaseAuth mAuth;
    EditText email, pass;
    Button login, signup, forgot, Flogin, Glogin;
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        email = findViewById(R.id.TaiKhoan);
        pass = findViewById(R.id.MatKhau);
        login = findViewById(R.id.loginBtn);
        signup = findViewById(R.id.SignUpBtn);
        forgot = findViewById(R.id.ForgotBtn);
        Flogin = findViewById(R.id.LoginFBtn);
        Glogin = findViewById(R.id.LoginGBtn);
        signup.setOnClickListener(this);
        login.setOnClickListener(this);
        Glogin.setOnClickListener(this);
        Flogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.SignUpBtn:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
            case R.id.loginBtn:

                LoginUser();
                break;
            case R.id.LoginFBtn:
                Intent intent = new Intent(LoginActivity.this, FacebookAuthActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                break;
            case R.id.LoginGBtn:
                signIn();
                break;
            case R.id.ForgotBtn:
                break;
        }
    }

    private void LoginUser() {
        String Email = email.getText().toString().trim();
        String Password = pass.getText().toString().trim();

        if(Email.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
        {
            email.setError("Please enter a valid email");
            email.requestFocus();
            return;
        }

        if(Password.isEmpty()){
            pass.setError("Password is required");
            pass.requestFocus();
            return;
        }

        if (Password.length()<6) {
            pass.setError("Password not valid");
            pass.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user.isEmailVerified()){

                                Intent intent = new Intent(LoginActivity.this, User_EmailAndPass_Activity.class);
                                startActivity(intent);
                            }else {
                                user.sendEmailVerification();
                                Toast.makeText(LoginActivity.this,"Xác nhận mail của bạn",Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            if(account!=null){
                navigateToUserActivity();
            }
            try {
                task.getResult(ApiException.class);
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void navigateToUserActivity() {
        finish();
        Intent intent = new Intent(LoginActivity.this, UserActivity.class);
        startActivity(intent);
    }

}