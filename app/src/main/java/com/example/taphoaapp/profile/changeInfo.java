package com.example.taphoaapp.profile;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.taphoaapp.Basket.DataCommunication;
import com.example.taphoaapp.BasketFragment;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class changeInfo extends AppCompatActivity  {


    private TabLayout mTabLayout;
    private ViewPager viewMain;
    private BottomNavigationView bottomnavigation;
    private String passName,passCategory,passcolor,passsize  , order,active ,PrevActive, ActiPrev,userID , thongBao,IGender;
    private int passPrice,passquantity,passSoluong;
    Bundle extras;
    Button btnChangeInfo;
    Context mContext;
    DataCommunication mCallback;
    Spinner Gender;
    EditText Fullname , Email,Phone,Age,ComfPass;
//    Gender
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String PassPassword;



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
            PassPassword = i.getStringExtra("password");
            ActiPrev = i.getStringExtra("PrevActive");
            if(ActiPrev !=null) {
                Log.e("PrevActive : ", ActiPrev.toString());
            }
            Fullname.setText(extras.getString("Info_fullname"));
            Email.setText(extras.getString("Info_email"));
            Age.setText(extras.getString("Info_age"));
            IGender = extras.getString("Info_gender");
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thay đổi thông tin tài khoản");

        Locale locales = new Locale("vi");
        Locale.setDefault(locales);
        Configuration config = new Configuration();
        config.locale = locales;
        this.getApplicationContext().getResources().updateConfiguration(config, null);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(changeInfo.this,
                android.R.layout.simple_spinner_item, getListGender());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Gender.setAdapter(adapter);
        Gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                IGender =  Gender.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
                            IGender = document.getString("gender");
                            if(IGender.equalsIgnoreCase("Male")) {
                                Gender.setSelection(0);
                            }
                        else{
                                Gender.setSelection(1);
                            }
//                            Gender.setText(document.getString("gender"));
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
                    ComfPass.setError(thongBao);
                    ComfPass.requestFocus();
//                    AlertDialog.Builder builder = new AlertDialog.Builder(changeInfo.this);
//                    builder.setMessage(thongBao)
//                            .setTitle("Thông báo");
//                    builder.show();
                }
                else if (Password.length()<6) {
                   thongBao = "mật khẩu không được ít hơn 6 ký tự";
                    ComfPass.setError(thongBao);
                    ComfPass.requestFocus();
//                    AlertDialog.Builder builder = new AlertDialog.Builder(changeInfo.this);
//                    builder.setMessage(thongBao)
//                            .setTitle("Thông báo");
//                    builder.show();
                }
                else {

//                    mAuth.signInWithEmailAndPassword(Email.getText().toString(), Password)
//                            .addOnCompleteListener(changeInfo.this,
//                                    new OnCompleteListener<AuthResult>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<AuthResult> task) {
//                                            if (task.isSuccessful()) {
                                                if(Password.equalsIgnoreCase(PassPassword)){
                                                Map<String, Object> order = new HashMap<>();
                                                order.put("fullName", Fullname.getText().toString());
                                                order.put("email", Email.getText().toString());
                                                order.put("age", Age.getText().toString());
                                                    if(Gender.getSelectedItem().toString().equalsIgnoreCase("Nam")) {
                                                        order.put("gender","Male");
                                                    }
                                                    else{
                                                        order.put("gender","Female");
                                                    }
//                                                order.put("gender", Gender.getText().toString());
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
                                                        Intent intent = new Intent(changeInfo.this, MainActivity.class);
//                intent.putExtra("userID", UID);
                                                        intent.putExtra("prevActive", "changeInfo");
                                                        intent.putExtra("password", Password);
                                                        startActivity(intent);
                                                    }
                                                }, 2000);
                                            } else {
                                                Toast.makeText(changeInfo.this, "Đăng nhập thất bại",
                                                        Toast.LENGTH_SHORT).show();
                                            }
//                                        }
//                                    });
                }

            }
        });

//        mTabLayout =findViewById(R.id.TopTabmain);

//        if(ActiPrev !=null) {
//            if (i != null && extras != null && ActiPrev.toString().equalsIgnoreCase("DetailProduct")) {
//                viewMain.setCurrentItem(1);
//            }
//        }


    }

    private List<CharSequence> getListGender(){
        List<CharSequence> listGender = new ArrayList<>();
        listGender.add(new String("Nam"));
        listGender.add(new String("Nữ"));

        return listGender;
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_quanao);
      if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
          super.onBackPressed();

      }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        return getParentActivityIntentImpl();
    }

    @Override
    public Intent getParentActivityIntent() {
        return getParentActivityIntentImpl();
    }

    private Intent getParentActivityIntentImpl() {
        Intent i = null;
        if (extras != null) {
            PrevActive = extras.getString("prevActive");
            //The key argument here must match that used in the other activity
        }

        // Here you need to do some logic to determine from which Activity you came.
        // example: you could pass a variable through your Intent extras and check that.
        if (PrevActive == "MainActivity") {
            i = new Intent(this, MainActivity.class);
            // set any flags or extras that you need.
            // If you are reusing the previous Activity (i.e. bringing it to the top
            // without re-creating a new instance) set these flags:
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            // if you are re-using the parent Activity you may not need to set any extras
            i.putExtra("prevActive", "DetailProduct");
        } else {
            i = new Intent(this, BasketFragment.class);
            // same comments as above
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            i.putExtra("prevActive", "DetailProduct");
        }

        return i;
    }





}