package com.example.taphoaapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.taphoaapp.profile.ProfileFragment;
import com.example.taphoaapp.profile.ProfileMainFragment;

public class MainViewPagerAdpater extends FragmentStatePagerAdapter
{
    Bundle extras ;
  Intent i;
  boolean getaddtoBasket;


    public MainViewPagerAdpater(@NonNull FragmentManager fm, int behavior,Intent intent) {
        super(fm, behavior);

        i = intent;
        extras = i.getExtras();
        if ( i!= null &&extras != null) {
            getaddtoBasket = i.getBooleanExtra("addtoBask",false);
//            Log.e("MainViewPage:",Boolean.toString(getaddtoBasket));
            // and get whatever type user account id is
        }

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: return new HomeFragment();
            case 1: return new BasketFragment();
            case 2: return new ProfileMainFragment();
            default: return new HomeFragment();
//            default: if(getaddtoBasket){return new BasketFragment();}
//                else {
//                return new HomeFragment();
//            }

        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title ="";
        switch (position) {
            case 0: title = App.getAppResources().getString(R.string.Home); break;
            case 1: title = App.getAppResources().getString(R.string.Basket);break;
            case 2: title = App.getAppResources().getString(R.string.Profile);break;


        }
        return title;
    }
}
