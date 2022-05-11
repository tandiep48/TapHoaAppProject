package com.example.taphoaapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taphoaapp.widget.CustomViewPager;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private TabLayout mTabLayout;
    private CustomViewPager viewMain;
    private View mView;
    private String order;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Bundle extras = getActivity().getIntent().getExtras();
//
//        if (extras != null) {
//            order = extras.getString("Order");
//            // and get whatever type user account id is
//        }
//        if(order == "YES") {
//            Fragment newFragment = new BasketFragment();
//            ((FragmentActivity) getActivity()).getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fragment_home, new BasketFragment())
//                    .commit();
//        }

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        mView =  inflater.inflate(R.layout.fragment_home, container, false);

        mTabLayout = mView.findViewById(R.id.TopTabHome);
        viewMain = mView.findViewById(R.id.ViewPagerHome);

        HomeViewPagerAdpater view_pager_adpater = new HomeViewPagerAdpater(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewMain.setAdapter(view_pager_adpater);
        viewMain.setPagingEnabled(true);
        mTabLayout.setupWithViewPager(viewMain);

        return mView;
    }
}