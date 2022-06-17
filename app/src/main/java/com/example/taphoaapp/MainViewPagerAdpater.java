package com.example.taphoaapp;

import android.os.Bundle;

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




    public MainViewPagerAdpater(@NonNull FragmentManager fm, int behavior) { super(fm, behavior); }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: return new HomeFragment();
            case 1: return new BasketFragment();
            case 2: return new ProfileMainFragment();
            default: return new HomeFragment();
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
