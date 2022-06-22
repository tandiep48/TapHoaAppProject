package com.example.taphoaapp.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.taphoaapp.App;
import com.example.taphoaapp.R;

public class ProfileViewPagerAdpater extends FragmentStatePagerAdapter
{



    public ProfileViewPagerAdpater(@NonNull FragmentManager fm, int behavior) { super(fm, behavior); }

    @NonNull
    @Override
    public Fragment getItem(int position) {


        switch (position) {
            case 0: return new DonHangFragment();
            case 1: return new ProfileFragment();
//            case 2: return new SettingFragment();
            default: return new DonHangFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title ="";
        switch (position) {
            case 0: title = App.getAppResources().getString(R.string.Shipment);break;
            case 1: title = App.getAppResources().getString(R.string.Profile); break;
//            case 2: title = App.getAppResources().getString(R.string.Setting);break;
        }
        return title;
    }
}
