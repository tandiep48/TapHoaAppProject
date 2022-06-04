package com.example.taphoaapp.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.taphoaapp.App;
import com.example.taphoaapp.DientuFragment;
import com.example.taphoaapp.QuanaoFragment;
import com.example.taphoaapp.R;
import com.example.taphoaapp.SachFragment;
import com.example.taphoaapp.VanphongFragment;

public class ProfileViewPagerAdpater extends FragmentStatePagerAdapter
{



    public ProfileViewPagerAdpater(@NonNull FragmentManager fm, int behavior) { super(fm, behavior); }

    @NonNull
    @Override
    public Fragment getItem(int position) {


        switch (position) {
            case 0: return new AccFragment();
            case 1: return new DientuFragment();
            case 2: return new VanphongFragment();
            case 3: return new SachFragment();
            default: return new AccFragment();
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
