package com.example.taphoaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager viewMain;
    private BottomNavigationView bottomnavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Locale locales = new Locale("vi");
        Locale.setDefault(locales);
        Configuration config = new Configuration();
        config.locale = locales;
        this.getApplicationContext().getResources().updateConfiguration(config, null);

//        mTabLayout =findViewById(R.id.TopTabmain);
        viewMain = findViewById(R.id.ViewPagerMain);
        bottomnavigation = findViewById(R.id.BotomNavMain);



        MainViewPagerAdpater view_pager_adpater = new MainViewPagerAdpater(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewMain.setAdapter(view_pager_adpater);
//        mTabLayout.setupWithViewPager(viewMain);



        viewMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0: bottomnavigation.getMenu().findItem(R.id.Menu_Shop).setChecked(true); break;
                    case 1: bottomnavigation.getMenu().findItem(R.id.Menu_Basket).setChecked(true); break;
                    case 2: bottomnavigation.getMenu().findItem(R.id.Menu_Profile).setChecked(true); break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottomnavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Menu_Shop: viewMain.setCurrentItem(0); break;
                    case R.id.Menu_Basket: viewMain.setCurrentItem(1); break;
                    case R.id.Menu_Profile: viewMain.setCurrentItem(2); break;
                }
                return true;
            }
        });

    }
    //        String[] Fragement =  {"QuanaoFragment","DientuFragment","VanphongFragment","SachFragment" };
//        String[] Title =  {App.getAppResources().getString(R.string.clothes),App.getAppResources().getString(R.string.Electron),App.getAppResources().getString(R.string.Office),App.getAppResources().getString(R.string.books) };
//        List<String> FragList = Arrays.asList(Fragement);
//        List<String> TiList = Arrays.asList(Title);


    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_quanao);
      if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
          super.onBackPressed();

      }
        super.onBackPressed();
    }
}