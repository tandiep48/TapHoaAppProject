package com.example.taphoaapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taphoaapp.Search.SearchActivity;
import com.example.taphoaapp.widget.CustomViewPager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private TabLayout mTabLayout;
    private CustomViewPager viewMain;
    private View mView;
    private String order,Cate;
    private Context mContext;
    private View ViewItem;
    List<String> Category;

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

        mTabLayout = mView.findViewById(R.id.TopTabHome);
        viewMain = mView.findViewById(R.id.ViewPagerHome);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Category =new ArrayList<>(Arrays.asList("quanao","electron","vanphong","sach"));

        db.collection("SAN_PHAM").whereNotIn("CATEGORY", Arrays.asList("quanao","electron","vanphong","sach"))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                            Cate = document.getString("CATEGORY");
                            Log.e("CategoryWierd",Cate);
                            boolean add = true;
                            boolean Fin = false;
                            for(int i = 0; i < Category.size();i++)
                            {
                                if(Cate.equalsIgnoreCase(Category.get(i).toString())){
                                    add =false;
                                }
                                if(i == Category.size()-1){ Fin = true; }
                            }
                            if(add && Fin)
                            {
                                Category.add(Cate);
                            }
                        HomeViewPagerAdpater view_pager_adpater = new HomeViewPagerAdpater(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,Category);

                        viewMain.setAdapter(view_pager_adpater);
                        viewMain.setPagingEnabled(true);
                        mTabLayout.setupWithViewPager(viewMain);
                    }
                } else {

                }
            }
        });


        return mView;
    }


}