package com.example.taphoaapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SachFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SachFragment extends Fragment {

    private RecyclerView mRecycler;
    private ProductAdapter proAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SachFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SachFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SachFragment newInstance(String param1, String param2) {
        SachFragment fragment = new SachFragment();
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
        return inflater.inflate(R.layout.fragment_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycler = view.findViewById(R.id.rcv_product_list);
        proAdapter = new ProductAdapter(getActivity());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        mRecycler.setLayoutManager(linearLayoutManager);

        proAdapter.setData(getListProduct());
        mRecycler.setAdapter(proAdapter);
    }
    private List<product_item> getListProduct(){
        List<product_item> products = new ArrayList<>();
        products.add( new product_item("https://cf.shopee.vn/file/34acd5e930c8a21e1c3a70d3cf2a70c5","Áo thun nam POLO trơn vải cá sấu cotton cao cấp ngắn tay cực sang trọng","55%",10,198000,89000));
        products.add( new product_item("https://cf.shopee.vn/file/b2612c1a8242069aced2f2f26b592f38","Mũ lưỡi trai ❤️ Nón kết thêu chữ Memorie phong cách Ulzzang","22%",15,58000,45000));
        products.add( new product_item("https://cf.shopee.vn/file/bb8871d9ef15f8772df509343b3c2c89","Quần áo phòng dịch đi máy bay đã kiểm định","55%",22,70000,330000));
        products.add( new product_item("https://cf.shopee.vn/file/8e022cdaa1ccc462c0b3b51d02438c91","Tất ngắn nam Vớ thấp cổ 4 màu trơn chống hôi chân","20%",5,3900,3500));
        products.add( new product_item("https://cf.shopee.vn/file/1a32d71426b5299936d59909870e92b6","Bộ Quần Áo Mặc Nhà Thể Thao Nam Mùa Hè Phong Cách Cao Cấp ZERO","42%",8,300000,175000));


        return products;
    }
}