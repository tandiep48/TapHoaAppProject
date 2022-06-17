package com.example.taphoaapp.profile;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.taphoaapp.Basket.DataCommunication;
import com.example.taphoaapp.IOnBackPressed;
import com.example.taphoaapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class changePassword extends AppCompatActivity  {


    private TabLayout mTabLayout;
    private ViewPager viewMain;
    private BottomNavigationView bottomnavigation;
    private String passName,passCategory,passcolor,passsize  , order,active ,PrevActive, ActiPrev,userID;
    private int passPrice,passquantity,passSoluong;
    Bundle extras;
    Context mContext;
    DataCommunication mCallback;
    String PassPassword;
    TextView tvPassword,tvNewPass,tvComfPass;
    Button btnChangePassword;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        mAuth = FirebaseAuth.getInstance();
//        mCallback = (DataCommunication) changePassword.this;
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

        tvPassword = findViewById(R.id.tvPassword);
        tvNewPass = findViewById(R.id.tvNewPassword);
        tvComfPass =  findViewById(R.id.tvConfirmPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tvNewPass.getText().toString().equalsIgnoreCase(tvComfPass.getText().toString()))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(changePassword.this);
                    builder.setMessage("Mật khẩu nhập lại chưa trùng với mật khẩu mới")
                            .setTitle("Thông báo");
                    builder.show();
                }
                else if(tvPassword.getText().toString().isEmpty()||tvNewPass.getText().toString().isEmpty()||tvComfPass.getText().toString().isEmpty())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(changePassword.this);
                    builder.setMessage("Không được để trống khung mật khẩu")
                            .setTitle("Thông báo");
                    builder.show();
                }
                else if(!tvNewPass.getText().toString().equalsIgnoreCase(PassPassword))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(changePassword.this);
                    builder.setMessage("Mật khẩu cũ nhập sai ")
                            .setTitle("Thông báo");
                    builder.show();
                }
                else{

                }
            }
        });

        Intent i = getIntent();
        Bundle extras = getIntent().getExtras();

        if ( i!= null &&extras != null) {
            order = extras.getString("Order");

            userID = i.getStringExtra("userID");

            ActiPrev = i.getStringExtra("PrevActive");

            PassPassword = i.getStringExtra("password");

            if(ActiPrev !=null) {
                Log.e("PrevActive : ", ActiPrev.toString());
            }

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

//        mTabLayout =findViewById(R.id.TopTabmain);

        if(ActiPrev !=null) {
            if (i != null && extras != null && ActiPrev.toString().equalsIgnoreCase("DetailProduct")) {
                viewMain.setCurrentItem(1);
            }
        }



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