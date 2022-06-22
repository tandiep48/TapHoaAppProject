package com.example.taphoaapp.profile;

import static java.lang.Math.toIntExact;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.taphoaapp.Basket.DataCommunication;
import com.example.taphoaapp.IOnBackPressed;
import com.example.taphoaapp.MainActivity;
import com.example.taphoaapp.ProductAdapter;
import com.example.taphoaapp.R;
import com.example.taphoaapp.Search.SearchActivity;
import com.example.taphoaapp.product_item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DonHangFragment extends Fragment  implements IOnBackPressed {

  private RecyclerView mRecycler;
  private DonHangAdapter proAdapter;
  private SearchView searchView;
  private LinearLayout pressLayout;
    private View ViewItem;
    private View mView;
    private Spinner spinner;
    public List<DonHang_item> SortDonHang;
    private Collator VNCollator;
//    String name, image, discount,category;
String maDH; String status; String time;
    Integer soluong,giacu,gia,pay;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userID;
    TextView EmptyDH;
    public boolean getaddtoBasket,getaddDonHang;
    private SwipeRefreshLayout mSrlLayout;


    Bundle extras;
    Intent i ;





    public DonHangFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DonHangFragment newInstance(String param1, String param2) {
        DonHangFragment fragment = new DonHangFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity) getActivity();
        getaddDonHang =  activity.getaddmyDonhang();
        Log.e("DonHangFragment",Boolean.toString(getaddDonHang));
        if(getaddDonHang)
        {
//            Log.e("BaskFrag:",Boolean.toString(getaddtoBasket));
            getListDonHang();
        }
//        Toast.makeText(getContext(),Boolean.toString(getaddtoBasket),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VNCollator = Collator.getInstance(new Locale("vi", "VN")); //Your locale here
        VNCollator.setStrength(Collator.PRIMARY); //desired strength

        setHasOptionsMenu(true);

        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView =  inflater.inflate(R.layout.fragment_product_list, container, false);
        // Inflate the layout for this fragment
        i = getActivity().getIntent();
        extras = getActivity().getIntent().getExtras();


        if ( i!= null &&extras != null) {

            getaddtoBasket = i.getBooleanExtra("addtoBask", false);

            userID = i.getStringExtra("userID");
            getaddDonHang = i.getBooleanExtra("addtoDonhang", false);

        }


        spinner = mView.findViewById(R.id.Spinner_sort);
//        SortProduct = getListProduct();
        SortDonHang = new ArrayList<DonHang_item>();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        EmptyDH = mView.findViewById(R.id.Empty_DonHangList);


        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_spinner_item, getListsize());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSrlLayout = mView.findViewById(R.id.sf_refresh_layout1);

        mSrlLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                proAdapter.clear();
                proAdapter.notifyDataSetChanged();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getListDonHang();
                    }
                }, 500);
            }
        });

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(), adapter.getItem(position), Toast.LENGTH_SHORT).show();
                Log.e("possition",String.valueOf(position));
//                proAdapter.clear();
                if(position<=1){
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
//                        SortProduct.sort((o1, o2) -> (o1.getName().substring(0,1)).compareToIgnoreCase((o2.getName().substring(0,1))));
                        SortDonHang.sort((o1, o2) -> compare(o1.getMaDH(),o2.getMaDH()));
//                        Collections.sort(SortDonHang, VNCollator);

                    }
                    if (position==1){
                        Collections.reverse(SortDonHang);
                    }

                }
                if(position>1&&position<=3){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        SortDonHang.sort(((o1, o2) ->  Integer.valueOf(o1.getPay()).compareTo(Integer.valueOf(o2.getPay()))));
                    }
                    if (position==3){
                        Collections.reverse(SortDonHang);
                    }

                }

                proAdapter.setData(SortDonHang);
                mRecycler.setAdapter(proAdapter);
                proAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        return mView;
    }


    public int compare(String arg1, String arg2) {
        return VNCollator.compare(arg1, arg2);
    }

    private List<CharSequence>  getListsize(){
        List<CharSequence> listSize = new ArrayList<>();
        listSize.add(new String("Mã đơn hàng: A-Z"));
        listSize.add(new String("Mã đơn hàng: Z-A"));
        listSize.add(new String("Giá trị tăng dần"));
        listSize.add(new String("Giá trị giảm dần"));
//        listSize.add(new String("Khuyến mãi"));

        return listSize;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Toolbar myToolbar = (Toolbar) view.findViewById(R.id.my_toolbar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
//        SearchView searchView = (SearchView) view.findViewById(R.id.search_viewmenu);
//        searchView.onActionViewExpanded();



//        ViewItem=view.findViewById(R.id.search_item_menu);
//        ViewItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), SearchActivity.class);
//                startActivity(intent);
//            }
//        });
        mRecycler = view.findViewById(R.id.rcv_product_list);
        proAdapter = new DonHangAdapter(getActivity());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        mRecycler.setLayoutManager(linearLayoutManager);

//        proAdapter.setData(SortDonHang);
        getListDonHang();


        RecyclerView.ItemDecoration itemDecoration  = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        mRecycler.addItemDecoration(itemDecoration);




    }

    private void LoadNRefreshLayout()
    {
        proAdapter.setData(getListDonHang());
        mRecycler.setAdapter(proAdapter);
    }

    private List<DonHang_item> getListDonHang(){
        List<DonHang_item> DonHangs = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Don_hang")
                .whereEqualTo("userID", userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                maDH = document.getString("DonHang_Id");
                                status = document.getString("status");
                                soluong = toIntExact(document.getLong("soluongsanpham"));
                                pay = toIntExact(document.getLong("TongThanhToan"));
                                time = document.getString("ngaydat");

                                DonHangs.add( new DonHang_item(maDH,status,soluong,pay,time));
                                Log.e("documment", document.getId() + " => " + document.getData());
                            }
                            SortDonHang = DonHangs;
                            if(DonHangs.isEmpty()||DonHangs.size() ==0||DonHangs ==null)
                            {
                                EmptyDH.setVisibility(View.VISIBLE);
                                EmptyDH.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                                EmptyDH.requestLayout();
                            }
                            else{
                                EmptyDH.setVisibility(View.GONE);
                            }

                            proAdapter.setData(SortDonHang);
                            mRecycler.setAdapter(proAdapter);
                            proAdapter.notifyDataSetChanged();
                            mSrlLayout.setRefreshing(false);
                        } else {
                            Log.e("documment", "Error getting documents: ", task.getException());
                        }
                    }
                });


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
//        DonHangs.add( new DonHang_item("DH02","Đang lấy hàng",1,104000, formattedDate));
//    SortDonHang = DonHangs;
//    proAdapter.setData(SortDonHang);

//        products.add( new product_item("https://cf.shopee.vn/file/34acd5e930c8a21e1c3a70d3cf2a70c5","Áo thun nam POLO trơn vải cá sấu cotton cao cấp ngắn tay cực sang trọng","55%",10,198000,89000));
//        products.add( new product_item("https://cf.shopee.vn/file/b2612c1a8242069aced2f2f26b592f38","Mũ lưỡi trai ❤️ Nón kết thêu chữ Memorie phong cách Ulzzang","22%",15,58000,45000));
//        products.add( new product_item("https://cf.shopee.vn/file/bb8871d9ef15f8772df509343b3c2c89","Quần áo phòng dịch đi máy bay đã kiểm định","55%",22,70000,330000));
//        products.add( new product_item("https://cf.shopee.vn/file/8e022cdaa1ccc462c0b3b51d02438c91","Tất ngắn nam Vớ thấp cổ 4 màu trơn chống hôi chân","20%",5,3900,3500));
//        products.add( new product_item("https://cf.shopee.vn/file/1a32d71426b5299936d59909870e92b6","Bộ Quần Áo Mặc Nhà Thể Thao Nam Mùa Hè Phong Cách Cao Cấp ZERO","42%",8,300000,175000));


        return DonHangs;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu) ;
//        SearchManager searchManager = (SearchManager) ((AppCompatActivity)getActivity()).getSystemService(Context.SEARCH_SERVICE);
//        searchView = (SearchView) menu.findItem(R.id.search_item_menu).getActionView();
////        ViewItem=menu.findItem(R.id.search_item_menu);
////        ViewItem.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent intent = new Intent(getActivity(), SearchActivity.class);
////                startActivity(intent);
////            }
////        });
//
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(((AppCompatActivity)getActivity()).getComponentName()));
//        searchView.setMaxWidth(Integer.MAX_VALUE);
//        searchView.setFocusable(true);

        super.onCreateOptionsMenu(menu, inflater);

//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                proAdapter.getFilter().filter(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                proAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });

    }


    @Override
    public boolean onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return true ;
        }
        else{
            return false;
        }
    }
}