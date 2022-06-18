package com.example.taphoaapp.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taphoaapp.ChangePasswordActivity;
import com.example.taphoaapp.DetailProduct.DetailProductActivity;
import com.example.taphoaapp.HomeViewPagerAdpater;
import com.example.taphoaapp.LoginActivity;
import com.example.taphoaapp.R;
import com.example.taphoaapp.Search.SearchActivity;
import com.example.taphoaapp.widget.CustomViewPager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private TabLayout mTabLayout;
    private CustomViewPager viewMain;
    private View mView;
    Button changeInfo, changePass , StoreInfo, Logout;
    TextView Info_fullname,Info_email,Info_age,Info_gender,Info_phone;
    String UID,PassPassword;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mAuth = FirebaseAuth.getInstance();

        mView =  inflater.inflate(R.layout.fragment_info, container, false);

        Info_fullname = mView.findViewById(R.id.Info_fullName);
        Info_email = mView.findViewById(R.id.Info_email);
        Info_age = mView.findViewById(R.id.Info_age);
        Info_gender = mView.findViewById(R.id.Info_gender);
        Info_phone = mView.findViewById(R.id.Info_phone);

        UID = mAuth.getCurrentUser().getUid();


        changeInfo = mView.findViewById(R.id.btnChangeInfo);
        changePass = mView.findViewById(R.id.btnChangePass);
        StoreInfo =  mView.findViewById(R.id.btnStoreInfo);
        Logout =  mView.findViewById(R.id.btnLogout);

        Intent i = requireActivity().getIntent();
        Bundle extras = requireActivity().getIntent().getExtras();

        if ( i!= null && extras != null) {

            PassPassword = extras.getString("password");
            // and get whatever type user account id is
        }


        db.collection("User")
                .document(UID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        if (document.exists()) {
                            Info_fullname.setText(document.getString("fullName"));
                            Info_email .setText(document.getString("email"));
                            Info_age.setText(document.getString("age"));
                            Info_gender.setText(document.getString("gender"));
                            Info_phone.setText(document.getString("phone"));
                        }
                    }
                }
                else {
                }
            }
        });

        changeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), changeInfo.class);
//                intent.putExtra("userID", UID);
                intent.putExtra("Info_fullname", Info_fullname.getText().toString());
                intent.putExtra("Info_email", Info_email.getText().toString());
                intent.putExtra("Info_age",Info_age.getText().toString());
                intent.putExtra("Info_gender",Info_gender.getText().toString());
                intent.putExtra("Info_phone",Info_phone.getText().toString());
                intent.putExtra("password",PassPassword);
                startActivity(intent);
            }
        });
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                intent.putExtra("userID", UID);
                intent.putExtra("Info_fullname", Info_fullname.getText().toString());
                intent.putExtra("Info_email", Info_email.getText().toString());
                intent.putExtra("Info_age",Info_age.getText().toString());
                intent.putExtra("Info_gender",Info_gender.getText().toString());
                intent.putExtra("Info_phone",Info_phone.getText().toString());
                intent.putExtra("password",PassPassword.toString());
                startActivity(intent);
            }
        });

        StoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StoreInfo.class);
                intent.putExtra("userID", UID);
                intent.putExtra("Info_fullname", Info_fullname.getText().toString());
                intent.putExtra("Info_email", Info_email.getText().toString());
                intent.putExtra("Info_age",Info_age.getText().toString());
                intent.putExtra("Info_gender",Info_gender.getText().toString());
                intent.putExtra("Info_phone",Info_phone.getText().toString());
                intent.putExtra("password",PassPassword);
                startActivity(intent);
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Đăng xuất khỏi tài khoản "+  mAuth.getCurrentUser().getEmail(),Toast.LENGTH_SHORT).show();
                mAuth.getCurrentUser().getEmail();
//                mAuth.signOut();
//                Intent intent = new Intent(getActivity(), LoginActivity.class);
//
////                intent.putExtra("userID", UID);
//                startActivity(intent);
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

//        mTabLayout = mView.findViewById(R.id.TopTabProfile);
//        viewMain = mView.findViewById(R.id.ViewPagerProfile);
//
//        HomeViewPagerAdpater view_pager_adpater = new HomeViewPagerAdpater(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//
//        viewMain.setAdapter(view_pager_adpater);
//        viewMain.setPagingEnabled(false);
//        mTabLayout.setupWithViewPager(viewMain);

        return mView;
    }
}