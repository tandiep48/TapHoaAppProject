package com.example.taphoaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private FirebaseFirestore firebaseFirestore;
    String UserID;
    EditText Email, Pass, CPass, Name, Age, Phone;
    RadioGroup GenderGroup;
    RadioButton GenderButton;
    Button Back, Finish;
    Boolean isOkay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Name = findViewById(R.id.FullnameInput);
        Email = findViewById(R.id.EmailInput);
        Pass = findViewById(R.id.MatKhauInput);
        CPass = findViewById(R.id.MatKhauCInput);
        Phone = findViewById(R.id.SdtInput);
        Age = findViewById(R.id.AgeInput);
        GenderGroup = findViewById(R.id.radio);
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

        //Lấy giá trị của input chuyển thành chuỗi
        String email = Email.getText().toString();
        String password = Pass.getText().toString();
        String cpassword = CPass.getText().toString();
        String fullname = Name.getText().toString();
        String Aging = Age.getText().toString();
        String dienthoai = Phone.getText().toString();
        int radioid =GenderGroup.getCheckedRadioButtonId();
        GenderButton = findViewById(radioid);
        String gender = GenderButton.getText().toString();

        // Mở firebaseAuth
        mAuth = FirebaseAuth.getInstance();

        //Kiểm tra input
        if(TextUtils.isEmpty(fullname))
        {
            Name.setError("Xin nhập đầy đủ tên");
            Name.requestFocus();
            isOkay = false;
        }else { isOkay = true; }
        if (TextUtils.isEmpty(Aging))
        {
            Age.setError("Xin nhập số tuổi");
            Age.requestFocus();
            isOkay = false;
        }else { isOkay = true; }
        if(TextUtils.isEmpty(dienthoai))
        {
            Phone.setError("Xin nhập số điện thoại");
            Phone.requestFocus();
            isOkay = false;
        }else { isOkay = true; }
        if(TextUtils.isEmpty(email))
        {
            Email.setError("Xin nhập email");
            Email.requestFocus();
            isOkay = false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {

            Email.setError("Xin nhập đúng định dạng email");
            Email.requestFocus();
            isOkay = false;
        }else { isOkay = true; }
        if(password.isEmpty())
        {
            Pass.setError("Xin nhập mật khẩu");
            Pass.requestFocus();
            isOkay = false;
        }else if(password.length()<6)
        {

            Pass.setError("Độ dài tối thiểu là 6");
            Pass.requestFocus();
            isOkay = false;
        }else { isOkay = true; }
        if(!cpassword.equals(password))
        {
            CPass.setError("Xác nhận mật khẩu sai");
            CPass.requestFocus();
            isOkay = false;
        }else { isOkay = true; }
        if(isOkay == true)
        {
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        //User user = new  User(fullname, Aging, email, dienthoai,gender);
                        UserID = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = firebaseFirestore.collection("User").document(UserID);
                        Map<String,Object> user = new HashMap<>();
                        user.put("fullName", fullname);
                        user.put("age", Aging);
                        user.put("email", email);
                        user.put("phone", dienthoai);
                        user.put("gender", gender);
                        documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        });
                    } else {Toast.makeText(SignUpActivity.this, "Đăng ký thất bại", Toast.LENGTH_LONG).show();}
                }
            });
        }else {Toast.makeText(SignUpActivity.this, "Input sai", Toast.LENGTH_LONG).show();}
    }
}