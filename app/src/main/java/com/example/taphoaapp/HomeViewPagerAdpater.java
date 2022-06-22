package com.example.taphoaapp;

import static com.facebook.FacebookSdk.getApplicationContext;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeViewPagerAdpater extends FragmentStatePagerAdapter
{
    FragmentManager Fnew;
    List<String>Category = new ArrayList<String>();

//    Fragment ElectronFragment = QuanaoFragment.newInstance("DienTu");
//    Fragment ClothFragment = QuanaoFragment.newInstance("quanao");
//    Fragment ElectronFragment = QuanaoFragment.newInstance("DienTu");



    public HomeViewPagerAdpater(@NonNull FragmentManager fm, int behavior, List<String> Category) {
        super(fm, behavior);
        this.Fnew = fm;
        this.Category = Category;
//        Fnew.beginTransaction().add(new QuanaoFragment("quanao"),"QuanAoFragment").commit();
//        Fnew.beginTransaction().add(new QuanaoFragment("DienTu"),"DienTuFragment").commit();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position <=3){
        switch (position) {
//            case 0: return new QuanaoFragment("quanao");
//            case 0: if(Fnew.findFragmentByTag("QuanAoFragment") != null){ return Fnew.findFragmentByTag("QuanAoFragment");}
//            case 1: if(Fnew.findFragmentByTag("DienTuFragment") != null){
//                return Fnew.findFragmentByTag("DienTuFragment");
//            }
            case 0: return QuanaoFragment.newInstance("quanao");
            case 1: return  QuanaoFragment.newInstance("electron");
            case 2: return QuanaoFragment.newInstance("vanphong");
            case 3: return QuanaoFragment.newInstance("sach");
            default: return QuanaoFragment.newInstance("quanao");
//                    new QuanaoFragment("quanao");
        }
        }
        else{
            if(Category.size()>=4) {
                for (int i = 4; i < Category.size(); i++) {
                    if(i == position){
                        return QuanaoFragment.newInstance(Category.get(i));

                    }
                }
            }
        }
        return new QuanaoFragment("quanao");
    }

    @Override
    public int getCount() {
        return Category.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title ="";

//            case 0: title = App.getAppResources().getString(R.string.clothes); break;
//            case 1: title = App.getAppResources().getString(R.string.Electron);break;
//            case 2: title = App.getAppResources().getString(R.string.Office);break;
//            case 3: title = App.getAppResources().getString(R.string.books);break;

                if(position <=3){
                    switch (position) {
                        case 0:
                            title = App.getAppResources().getString(R.string.clothes);
                            break;
                        case 1:
                            title = App.getAppResources().getString(R.string.Electron);
                            break;
                        case 2:
                            title = App.getAppResources().getString(R.string.Office);
                            break;
                        case 3:
                            title = App.getAppResources().getString(R.string.books);
                            break;
                    }
                }else {
                    if(Category.size()>=4) {
                        for (int i = 4; i < Category.size(); i++) {
                           if(i == position){
                               title = Category.get(i).toString();
                           }
                        }
                    }
                }



        return title;
    }
}
