package com.example.taphoaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.taphoaapp.Basket.DataCommunication;
import com.example.taphoaapp.DetailProduct.DetailProductActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements DataCommunication {


    private TabLayout mTabLayout;
    private ViewPager viewMain;
    private BottomNavigationView bottomnavigation;
    private String passName,passCategory,passcolor,passsize  , order,active ,PrevActive, ActiPrev,userID;
    private int passPrice,passquantity,passSoluong;
    Bundle extras;
    Context mContext;
    DataCommunication mCallback;
    String PasssPassword;
    public boolean getaddtoBasket;
    Intent i ;

    public boolean getaddmybasket() {
        return getaddtoBasket;
    }

    @Override
    public void attachBaseContext(Context context) {
        mContext = context;
        super.attachBaseContext(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (DataCommunication) this;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DataCommunication");
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        mCallback = (DataCommunication) MainActivity.this;
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


        i = getIntent();
        extras = getIntent().getExtras();


        if ( i!= null &&extras != null) {
            order = extras.getString("Order");
            PasssPassword = i.getStringExtra("password");
            Log.e("MainActive : ", PasssPassword.toString());
            getaddtoBasket = i.getBooleanExtra("addtoBask",false);

            userID = i.getStringExtra("userID");

            ActiPrev = i.getStringExtra("PrevActive");
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
        viewMain = findViewById(R.id.ViewPagerMain);
        bottomnavigation = findViewById(R.id.BotomNavMain);




        MainViewPagerAdpater view_pager_adpater = new MainViewPagerAdpater(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,getIntent());

        viewMain.setAdapter(view_pager_adpater);
//        mTabLayout.setupWithViewPager(viewMain);
        if(ActiPrev !=null) {
            if (i != null && extras != null && ActiPrev.toString().equalsIgnoreCase("DetailProduct")) {
                viewMain.setCurrentItem(1);
                bottomnavigation.getMenu().findItem(R.id.Menu_Basket).setChecked(true);
            }
            else if (i != null && extras != null && ActiPrev.toString().equalsIgnoreCase("changeInfo")) {
                viewMain.setCurrentItem(2);
                bottomnavigation.getMenu().findItem(R.id.Menu_Profile).setChecked(true);
            }
            else if (i != null && extras != null && ActiPrev.toString().equalsIgnoreCase("changePassword")) {
                viewMain.setCurrentItem(2);
                bottomnavigation.getMenu().findItem(R.id.Menu_Profile).setChecked(true);
            }
        }
//        if(getaddtoBasket) {
//            MainViewPagerAdpater view_pager_adpater1 = new MainViewPagerAdpater(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,getIntent());
//            viewMain.setAdapter(view_pager_adpater1);
//            viewMain.setCurrentItem(1);
//            getaddtoBasket = false;
//            getIntent().putExtra("addtoBask",getaddtoBasket);
//        }



        viewMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0: bottomnavigation.getMenu().findItem(R.id.Menu_Shop).setChecked(true); break;
                    case 1: {bottomnavigation.getMenu().findItem(R.id.Menu_Basket).setChecked(true);
//                        Toast.makeText(mContext,Boolean.toString(getaddtoBasket),Toast.LENGTH_SHORT).show();
                        if(getaddtoBasket) {
//                            MainViewPagerAdpater view_pager_adpater1 = new MainViewPagerAdpater(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,getIntent());
//                            viewMain.setAdapter(view_pager_adpater1);
//                            Log.e("MainActive:",Boolean.toString(getaddtoBasket));
//                            Toast.makeText(MainActivity.this,Boolean.toString(getaddtoBasket),Toast.LENGTH_SHORT).show();
//                            getaddtoBasket = false;
//                            getIntent().putExtra("addtoBask",getaddtoBasket);
////                            finish();
//                            overridePendingTransition(0, 0);
//                            getIntent().putExtra("addtoBask",true);
//                            startActivity(getIntent());
//                            overridePendingTransition(0, 0);
                        }
                        break;}
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

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        // Collect data from the intent and use it
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null) {
            getaddtoBasket = data.getBooleanExtra("addtoBask", false);
        }
    }

    @Override
    protected void onResume() {
//        if(getaddtoBasket()){
//            setaddtoBasket(false);
//            this.recreate();
//        }
//        if(getaddtoBasket)
//        {
//            this.recreate();
//            i.putExtra("addtoBask", false);
//        }
        super.onResume();

    }


    @Override
    public void supportNavigateUpTo(@NonNull Intent upIntent) {
//        if(getaddtoBasket)
//        {
//            this.recreate();
//            i.putExtra("addtoBask", false);
//        }
        super.supportNavigateUpTo(upIntent);
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
    public boolean getaddtoBasket() {
        return getaddtoBasket;
    }

    @Override
    public void setaddtoBasket(boolean getaddtoBasket) {

    }

    @Override
    public String getPassName() {
        return passName;
    }

    @Override
    public void setPassName(String passName) {
        this.passName = passName;
    }

    @Override
    public String getPassCategory() {
        return passCategory;
    }

    @Override
    public void setPassCategory(String passCategory) {
        this.passCategory = passCategory;
    }

    @Override
    public int getPassquantity() {
        return passquantity;
    }

    @Override
    public void setPassquantity(int passquantity) {
        this.passquantity = passquantity;
    }

    @Override
    public String getPasscolor() {
        return passcolor;
    }

    @Override
    public void setPasscolor(String passcolor) {
        this.passcolor = passcolor;
    }

    @Override
    public String getPasssize() {
        return passsize;
    }

    @Override
    public void setPasssize(String passsize) {
        this.passsize = passsize;
    }

    @Override
    public int getPassPrice() {
        return passPrice;
    }

    @Override
    public void setPassPrice(int passPrice) {
        this.passPrice = passPrice;
    }

    @Override
    public int getPassSoluong() {
        return passSoluong;
    }
    
    @Override
    public void setPassSoluong(int passSoluong) {
        this.passSoluong = passSoluong;
    }

    @Override
    public void setPrevActive(String PrevActive) {
        this.PrevActive = PrevActive;
    }

    @Override
    public String getPrevActive() {
        return PrevActive;
    }



}