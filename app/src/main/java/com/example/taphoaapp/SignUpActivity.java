package com.example.taphoaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    EditText Email, Pass, Name, Age;
    Button Back, Finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Email = findViewById(R.id.EmailInput);
        Pass = findViewById(R.id.MatKhauInput);
        Name = findViewById(R.id.TenInput);
        Age = findViewById(R.id.TuoiInput);
        Finish = findViewById(R.id.SignBtn);
        Back = findViewById(R.id.BackBtn);
        Back.setOnClickListener(this);
        Finish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.BackBtn:
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.SignBtn:
                SignInUser();
                break;
        }
    }

    private void SignInUser() {
        String email = Email.getText().toString();
        String password = Pass.getText().toString();
        String fullname = Name.getText().toString();
        String Aging = Age.getText().toString();
        mAuth = FirebaseAuth.getInstance();
        if(email.isEmpty())
        {
            Email.setError("Xin nhập email");
            Email.requestFocus();
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Email.setError("Xin nhập đúng định dạng email");
            Email.requestFocus();
        }
        if(password.isEmpty())
        {
            Pass.setError("Xin nhập mật khẩu");
            Pass.requestFocus();
        }
        if(password.length()<6)
        {
            Pass.setError("Độ dài tối thiểu là 6");
            Pass.requestFocus();
        }
        if(fullname.isEmpty())
        {
            Name.setError("Xin nhập đầy đủ tên");
            Name.requestFocus();
        }
        if (Aging.isEmpty())
        {
            Age.setError("Xin nhập số tuổi");
            Age.requestFocus();
        }
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    User user = new User(fullname, Aging, email);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(SignUpActivity.this, "Đăng ký thất bại", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {Toast.makeText(SignUpActivity.this, "Đăng ký thất bại", Toast.LENGTH_LONG).show();}
            }
        });
    }
}