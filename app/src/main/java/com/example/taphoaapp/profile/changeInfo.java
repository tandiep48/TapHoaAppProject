package com.example.taphoaapp.profile;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.taphoaapp.Basket.DataCommunication;
import com.example.taphoaapp.IOnBackPressed;
import com.example.taphoaapp.LoginActivity;
import com.example.taphoaapp.MainActivity;
import com.example.taphoaapp.MainViewPagerAdpater;
import com.example.taphoaapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class changeInfo extends AppCompatActivity  {


    private TabLayout mTabLayout;
    private ViewPager viewMain;
    private BottomNavigationView bottomnavigation;
    private String passName,passCategory,passcolor,passsize  , order,active ,PrevActive, ActiPrev,userID , thongBao;
    private int passPrice,passquantity,passSoluong;
    Bundle extras;
    Button btnChangeInfo;
    Context mContext;
    DataCommunication mCallback;
    EditText Fullname , Email,Phone,Gender,Age,ComfPass;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);
//        mCallback = (DataCommunication) changeInfo.this;
//        if(PrevActive!= null){
//        Log.e("PrevActive : " , PrevActive.toString());
//        }
//        extras = getIntent().getExtras();
//
//        if (extras != null) {
//            order = extras.getString("Order");
//            active = extras.getString("prevActive");
//            // and get whatever type user account id is
//        }
        Fullname = findViewById(R.id.tvFullname);
        Email = findViewById(R.id.tvEmail);
        Age = findViewById(R.id.tvAge);
        Gender = findViewById(R.id.tvGender);
        Phone = findViewById(R.id.tvPhone);
        ComfPass = findViewById(R.id.tvConfirmPassword);
        btnChangeInfo = findViewById(R.id.btnChangeInfo);



        Intent i = getIntent();
        Bundle extras = getIntent().getExtras();

        if ( i!= null &&extras != null) {
            order = extras.getString("Order");

            userID = i.getStringExtra("userID");

            ActiPrev = i.getStringExtra("PrevActive");
            if(ActiPrev !=null) {
                Log.e("PrevActive : ", ActiPrev.toString());
            }
            Fullname.setText(extras.getString("Info_fullname"));
            Email.setText(extras.getString("Info_email"));
            Age.setText(extras.getString("Info_age"));
            Gender.setText(extras.getString("Info_gender"));
            Phone.setText(extras.getString("Info_phone"));

            // and get whatever type user account id is
        }

//        if(savedInstanceState == null  ) {
//            Fragment newFragment = new BasketFragment();
//            this.getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.Main_root, new BasketFragment())
//                    .commit();
//        }

        Locale locales = new Locale("vi");
        Locale.setDefault(locales);
        Configuration config = new Configuration();
        config.locale = locales;
        this.getApplicationContext().getResources().updateConfiguration(config, null);

        mAuth = FirebaseAuth.getInstance();


        db.collection("User")
                .document(userID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        if (document.exists()) {
                            Fullname.setText(document.getString("fullName"));
                            Email.setText(document.getString("email"));
                            Age.setText(document.getString("age"));
                            Gender.setText(document.getString("gender"));
                            Phone.setText(document.getString("phone"));
                        }
                    }
                }
                else {
                }
            }
        });
        btnChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Password = ComfPass.getText().toString().trim();
                if(Password.isEmpty()){
                    thongBao = "mật khẩu không được để trống";
                }

                if (Password.length()<6) {
                   thongBao = "mật khẩu không được ít hơn 6 ký tự";
                }

                mAuth.signInWithEmailAndPassword(Email.getText().toString(), Password)
                        .addOnCompleteListener(changeInfo.this,
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {

                                            Map<String, Object> order = new HashMap<>();
                                            order.put("fullName", Fullname.getText().toString());
                                            order.put("email", Email.getText().toString());
                                            order.put("age", Age.getText().toString());
                                            order.put("gender", Gender.getText().toString());
                                            order.put("phone", Phone.getText().toString());

                                            db.collection("User").document(userID)
                                                    .set(order);

                                            AlertDialog.Builder builder = new AlertDialog.Builder(changeInfo.this);
                                            builder.setMessage("Thay đổi thông tin thành công!")
                                                    .setTitle("Thông báo");
                                            builder.show();
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Intent intent = new Intent(changeInfo.this, LoginActivity.class);
//                intent.putExtra("userID", UID);
                                                    startActivity(intent);
                                                }
                                            }, 2000);
                                        } else {
                                            Toast.makeText(changeInfo.this, "Đăng nhập thất bại",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

            }
        });

//        mTabLayout =findViewById(R.id.TopTabmain);

//        if(ActiPrev !=null) {
//            if (i != null && extras != null && ActiPrev.toString().equalsIgnoreCase("DetailProduct")) {
//                viewMain.setCurrentItem(1);
//            }
//        }


    }


    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_quanao);
      if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
          super.onBackPressed();

      }
        super.onBackPressed();
    }





}