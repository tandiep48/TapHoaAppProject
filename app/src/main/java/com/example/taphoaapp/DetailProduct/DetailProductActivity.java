package com.example.taphoaapp.DetailProduct;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.taphoaapp.IOnBackPressed;
import com.example.taphoaapp.MainViewPagerAdpater;
import com.example.taphoaapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailProductActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager viewMain;
    private BottomNavigationView bottomnavigation;
    private ImageView main , one , two , three;
    private Spinner spinColor , spinSize;
    SpinnerColorAdapter spinnerColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_product);

        Locale locales = new Locale("vi");
        Locale.setDefault(locales);
        Configuration config = new Configuration();
        config.locale = locales;
        this.getApplicationContext().getResources().updateConfiguration(config, null);

        spinColor = findViewById(R.id.detail_Color_choose);
        spinSize = findViewById(R.id.detail_Size_choose);
        spinnerColor = new SpinnerColorAdapter(this,android.R.layout.simple_spinner_item,getListColor());
        spinColor.setAdapter(spinnerColor);
        spinColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(DetailProductActivity.this, spinnerColor.getItem(i).getName(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this,
                 android.R.layout.simple_spinner_item, getListsize());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSize.setAdapter(adapter);
        spinSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DetailProductActivity.this, adapter.getItem(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        main = (ImageView) findViewById(R.id.head_image_product);
        one = (ImageView) findViewById(R.id.one_image_product);
        two = (ImageView) findViewById(R.id.two_image_product);
        three = (ImageView) findViewById(R.id.three_image_product);

        Glide
                .with(this)
                .load("https://cf.shopee.vn/file/34acd5e930c8a21e1c3a70d3cf2a70c5")
                .into(main);
        Glide
                .with(this)
                .load("https://cf.shopee.vn/file/1657b14b218fd5962fc3508d367379fc")
                .into(one);
        Glide
                .with(this)
                .load("https://cf.shopee.vn/file/e86689e29f6f3d7131d0a0948ef254c1")
                .into(two);
        Glide
                .with(this)
                .load("https://cf.shopee.vn/file/afe17b6984db098f4e39e2f2c66a0d65")
                .into(three);


        ConstraintLayout ml = findViewById(R.id.detail_Parrent_Constraint);
        ml.invalidate();

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Quần Áo");
//        getSupportActionBar().setDisplayShowTitleEnabled(false);


        NestedScrollView scrollView = findViewById(R.id.myScrollView);
        scrollView.invalidate();
        scrollView.requestLayout();

//        mTabLayout =findViewById(R.id.TopTabmain);
//        viewMain = findViewById(R.id.ViewPagerMain);
//        bottomnavigation = findViewById(R.id.BotomNavMain);
//
//        MainViewPagerAdpater view_pager_adpater = new MainViewPagerAdpater(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//
//        viewMain.setAdapter(view_pager_adpater);
////        mTabLayout.setupWithViewPager(viewMain);
//
//
//
//        viewMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                switch (position) {
//                    case 0: bottomnavigation.getMenu().findItem(R.id.Menu_Shop).setChecked(true); break;
//                    case 1: bottomnavigation.getMenu().findItem(R.id.Menu_Basket).setChecked(true); break;
//                    case 2: bottomnavigation.getMenu().findItem(R.id.Menu_Profile).setChecked(true); break;
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//        bottomnavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.Menu_Shop: viewMain.setCurrentItem(0); break;
//                    case R.id.Menu_Basket: viewMain.setCurrentItem(1); break;
//                    case R.id.Menu_Profile: viewMain.setCurrentItem(2); break;
//                }
//                return true;
//            }
//        });
//
//    }
//    //        String[] Fragement =  {"QuanaoFragment","DientuFragment","VanphongFragment","SachFragment" };
////        String[] Title =  {App.getAppResources().getString(R.string.clothes),App.getAppResources().getString(R.string.Electron),App.getAppResources().getString(R.string.Office),App.getAppResources().getString(R.string.books) };
////        List<String> FragList = Arrays.asList(Fragement);
////        List<String> TiList = Arrays.asList(Title);
//
//
//    @Override
//    public void onBackPressed() {
//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_quanao);
//      if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
//          super.onBackPressed();
//
//      }
//        super.onBackPressed();
//    }
}

    private List<SpinnerColor> getListColor(){
        List<SpinnerColor> listColor = new ArrayList<>();
        listColor.add(new SpinnerColor("Xám","#808080"));
        listColor.add(new SpinnerColor("Vàng kem","#FFFFB2"));
        listColor.add(new SpinnerColor("Đen","#191919"));

        return listColor;
    }

    private List<CharSequence>  getListsize(){
        List<CharSequence> listSize = new ArrayList<>();
        listSize.add(new String("L"));
        listSize.add(new String("XL"));
        listSize.add(new String("XXl"));

        return listSize;
    }
}