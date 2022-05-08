package com.example.taphoaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class HomeViewPagerAdpater extends FragmentStatePagerAdapter
{



    public HomeViewPagerAdpater(@NonNull FragmentManager fm, int behavior) { super(fm, behavior); }

    @NonNull
    @Override
    public Fragment getItem(int position) {


        switch (position) {
            case 0: return new QuanaoFragment();
            case 1: return new DientuFragment();
            case 2: return new VanphongFragment();
            case 3: return new SachFragment();
            default: return new QuanaoFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title ="";
        switch (position) {
            case 0: title = App.getAppResources().getString(R.string.clothes); break;
            case 1: title = App.getAppResources().getString(R.string.Electron);break;
            case 2: title = App.getAppResources().getString(R.string.Office);break;
            case 3: title = App.getAppResources().getString(R.string.books);break;

        }
        return title;
    }
}
