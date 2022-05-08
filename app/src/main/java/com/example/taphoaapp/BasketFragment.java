package com.example.taphoaapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taphoaapp.Basket.BasketProductAdapter;
import com.example.taphoaapp.DetailProduct.DetailProductActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BasketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BasketFragment extends Fragment {

    private RecyclerView mRecycler;
    private BasketProductAdapter BasproAdapter;
    private View mView;
    private TextView total;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Locale locale = new Locale("vi", "VN");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    public BasketFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BasketFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BasketFragment newInstance(String param1, String param2) {
        BasketFragment fragment = new BasketFragment();
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

        mView =  inflater.inflate(R.layout.fragment_basket, container, false);

        Spinner spinerArea = mView.findViewById(R.id.basket_area_spinner);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_spinner_item, getListArea());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerArea.setAdapter(adapter);
        spinerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(), adapter.getItem(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mRecycler = mView.findViewById(R.id.rvFoods);
        total = mView.findViewById(R.id.tvTotal);

        BasproAdapter = new BasketProductAdapter(getActivity());

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
//        mRecycler.setLayoutManager(linearLayoutManager);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false) {
            @Override
            public void onLayoutCompleted(RecyclerView.State state) {
                super.onLayoutCompleted(state);
                total.setText(String.valueOf(currencyFormatter.format(BasproAdapter.getTinh())));
            }
        });


        BasproAdapter.setData(getListProduct());
        mRecycler.setAdapter(BasproAdapter);

        return mView;
    }

    private List<product_item> getListProduct(){
        List<product_item> products = new ArrayList<>();
        products.add( new product_item("https://cf.shopee.vn/file/34acd5e930c8a21e1c3a70d3cf2a70c5","Áo thun nam POLO trơn vải cá sấu cotton cao cấp ngắn tay cực sang trọng","55%",2,198000,89000));
        products.add( new product_item("https://cf.shopee.vn/file/b2612c1a8242069aced2f2f26b592f38","Mũ lưỡi trai ❤️ Nón kết thêu chữ Memorie phong cách Ulzzang","22%",5,58000,45000));
        products.add( new product_item("https://cf.shopee.vn/file/1a32d71426b5299936d59909870e92b6","Bộ Quần Áo Mặc Nhà Thể Thao Nam Mùa Hè Phong Cách Cao Cấp ZERO","42%",3,300000,175000));
        return products;
    }

    private List<CharSequence>  getListArea(){
        List<CharSequence> listSize = new ArrayList<>();
        listSize.add(new String("Quận 1"));
        listSize.add(new String("Quận 3"));
        listSize.add(new String("Quận 5"));
        listSize.add(new String("Quận 9"));
        listSize.add(new String("Quận 10"));
        listSize.add(new String("Quận 12"));
        listSize.add(new String("Quận Bình Thạnh"));
        listSize.add(new String("Tp.Thủ Đức"));


        return listSize;
    }
}