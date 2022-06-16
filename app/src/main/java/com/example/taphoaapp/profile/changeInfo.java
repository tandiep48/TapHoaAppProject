package com.example.taphoaapp.profile;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.taphoaapp.Basket.DataCommunication;
import com.example.taphoaapp.IOnBackPressed;
import com.example.taphoaapp.MainViewPagerAdpater;
import com.example.taphoaapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.Locale;

public class changeInfo extends AppCompatActivity  {


    private TabLayout mTabLayout;
    private ViewPager viewMain;
    private BottomNavigationView bottomnavigation;
    private String passName,passCategory,passcolor,passsize  , order,active ,PrevActive, ActiPrev,userID;
    private int passPrice,passquantity,passSoluong;
    Bundle extras;
    Context mContext;
    DataCommunication mCallback;
    EditText Fullname , Email,Phone,Gender,Age,ComfPass;



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