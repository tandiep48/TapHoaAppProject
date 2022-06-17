package com.example.taphoaapp;

import static java.lang.Math.toIntExact;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.taphoaapp.DetailProduct.DetailProductActivity;
import com.example.taphoaapp.Search.SearchActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


public class QuanaoFragment extends Fragment  implements IOnBackPressed{

  private RecyclerView mRecycler;
  private ProductAdapter proAdapter;
  private SearchView searchView;
  private LinearLayout pressLayout;
    private View ViewItem;
    private View mView;
    private Spinner spinner;
    public List<product_item> SortProduct;
    private Collator VNCollator;
    String name, image, discount,category;
    Integer soluong,giacu,gia;




    public QuanaoFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static QuanaoFragment newInstance(String param1, String param2) {
        QuanaoFragment fragment = new QuanaoFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

        mView =  inflater.inflate(R.layout.fragment_quanao, container, false);
        // Inflate the layout for this fragment
        spinner = mView.findViewById(R.id.Spinner_sort);
//        SortProduct = getListProduct();
        SortProduct = new ArrayList<product_item>();


        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_spinner_item, getListsize());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
                        SortProduct.sort((o1, o2) -> compare(o1.getName(),o2.getName()));
//                        Collections.sort(SortProduct, VNCollator);

                    }
                    if (position==1){
                        Collections.reverse(SortProduct);
                    }

                }
                if(position>1&&position<=3){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        SortProduct.sort(((o1, o2) ->  Integer.valueOf(o1.getPrice()).compareTo(Integer.valueOf(o2.getPrice()))));
                    }
                    if (position==3){
                        Collections.reverse(SortProduct);
                    }

                }

                proAdapter.setData(SortProduct);
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
        listSize.add(new String("A-Z"));
        listSize.add(new String("Z-A"));
        listSize.add(new String("Giá tăng dần"));
        listSize.add(new String("Giá giảm dần"));
        listSize.add(new String("Khuyến mãi"));

        return listSize;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar myToolbar = (Toolbar) view.findViewById(R.id.my_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
//        SearchView searchView = (SearchView) view.findViewById(R.id.search_viewmenu);
//        searchView.onActionViewExpanded();


        ViewItem=view.findViewById(R.id.search_item_menu);
        ViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("PrevActive", "MainActivity");
                startActivity(intent);
            }
        });
        mRecycler = view.findViewById(R.id.rcv_product_list);
        proAdapter = new ProductAdapter(getActivity());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        mRecycler.setLayoutManager(linearLayoutManager);

//        proAdapter.setData(SortProduct);
        getListProduct();
        mRecycler.setAdapter(proAdapter);

        RecyclerView.ItemDecoration itemDecoration  = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        mRecycler.addItemDecoration(itemDecoration);




    }

    private void LoadNRefreshLayout()
    {
        proAdapter.setData(getListProduct());
        mRecycler.setAdapter(proAdapter);
    }

    private List<product_item> getListProduct(){
        List<product_item> products = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("SAN_PHAM")
                .whereEqualTo("CATEGORY", "quanao")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                category = document.getString("CATEGORY");
                                name = document.getString("NAME");
                                image = document.getString("IMAGE");
                                discount = String.valueOf(toIntExact(document.getLong("DISCOUNT")));
                                soluong = toIntExact(document.getLong("SOLUONG"));
                                giacu = toIntExact(document.getLong("GIACU"));
                                gia = toIntExact(document.getLong("GIA"));

                                products.add( new product_item(category,image,name,discount,soluong,giacu,gia));
                                Log.e("documment", document.getId() + " => " + document.getData());
                            }
                            SortProduct = products;
                            proAdapter.setData(SortProduct);
                        } else {
                            Log.e("documment", "Error getting documents: ", task.getException());
                        }
                    }
                });

//        products.add( new product_item("https://cf.shopee.vn/file/34acd5e930c8a21e1c3a70d3cf2a70c5","Áo thun nam POLO trơn vải cá sấu cotton cao cấp ngắn tay cực sang trọng","55%",10,198000,89000));
//        products.add( new product_item("https://cf.shopee.vn/file/b2612c1a8242069aced2f2f26b592f38","Mũ lưỡi trai ❤️ Nón kết thêu chữ Memorie phong cách Ulzzang","22%",15,58000,45000));
//        products.add( new product_item("https://cf.shopee.vn/file/bb8871d9ef15f8772df509343b3c2c89","Quần áo phòng dịch đi máy bay đã kiểm định","55%",22,70000,330000));
//        products.add( new product_item("https://cf.shopee.vn/file/8e022cdaa1ccc462c0b3b51d02438c91","Tất ngắn nam Vớ thấp cổ 4 màu trơn chống hôi chân","20%",5,3900,3500));
//        products.add( new product_item("https://cf.shopee.vn/file/1a32d71426b5299936d59909870e92b6","Bộ Quần Áo Mặc Nhà Thể Thao Nam Mùa Hè Phong Cách Cao Cấp ZERO","42%",8,300000,175000));


        return products;
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