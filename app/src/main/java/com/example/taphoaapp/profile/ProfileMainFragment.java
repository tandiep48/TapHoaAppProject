package com.example.taphoaapp.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.taphoaapp.HomeViewPagerAdpater;
import com.example.taphoaapp.R;
import com.example.taphoaapp.widget.CustomViewPager;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileMainFragment extends Fragment {

    private TabLayout mTabLayout;
    private CustomViewPager viewMain;
    private View mView;
    private String order;
    private Context mContext;
    Intent i;
    Boolean getaddDonHang;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        // This ma;kes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception

    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileMainFragment() {
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
    public static ProfileMainFragment newInstance(String param1, String param2) {
        ProfileMainFragment fragment = new ProfileMainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        if (i != null && viewMain != null &&  getaddDonHang) {
            viewMain.setCurrentItem(0);
//            mTabLayout.getMenu().findItem(R.id.Menu_Profile).setChecked(true);
        }
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//      String extras = getActivity().getIntent().getStringExtra("Order");
//
 //      if (extras != null) {
//           order = extras.getString("Order");
           // and get whatever type user account id is
//       }
//        if(extras == "YES") {
//            Fragment newFragment = new BasketFragment();
//            ((FragmentActivity) getActivity()).getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fragment_home, new BasketFragment())
//                   .commit();
//       }

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

        i = requireActivity().getIntent();
        if ( i!= null ) {
            getaddDonHang = i.getBooleanExtra("addtoDonhang",false);
            // and get whatever type user account id is
        }


        mTabLayout = mView.findViewById(R.id.TopTabHome);
        viewMain = mView.findViewById(R.id.ViewPagerHome);

        ProfileViewPagerAdpater view_pager_adpater = new ProfileViewPagerAdpater(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewMain.setAdapter(view_pager_adpater);

        if (i != null &&  getaddDonHang) {
            viewMain.setCurrentItem(0);
//            mTabLayout.getMenu().findItem(R.id.Menu_Profile).setChecked(true);
        }

        viewMain.setPagingEnabled(true);
        mTabLayout.setupWithViewPager(viewMain);

        return mView;
    }
}