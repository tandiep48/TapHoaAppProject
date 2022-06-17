package com.example.taphoaapp.Search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.taphoaapp.Basket.BasketProductAdapter;
import com.example.taphoaapp.R;
import com.example.taphoaapp.product_item;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView mRecycler;
    private search_adapter searchAdapter;
      List<Search_Item>  ListOriginsearchAdapt;
    androidx.appcompat.widget.SearchView search;
    private Collator VNCollator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        super.onCreate(savedInstanceState);
        VNCollator = Collator.getInstance(new Locale("vi", "VN")); //Your locale here
        VNCollator.setStrength(Collator.PRIMARY); //desired strength

        mRecycler = findViewById(R.id.searchView_recyclerview);
        ListOriginsearchAdapt = getListProduct();

        Toolbar toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        search=findViewById(R.id.searchView);

        search.setIconifiedByDefault(true);
        search.setFocusable(true);
        search.setIconified(false);
        search.requestFocusFromTouch();
        search.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchword(newText);
                return false;
            }
        });


        searchAdapter = new search_adapter(ListOriginsearchAdapt,SearchActivity.this);

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
//        mRecycler.setLayoutManager(linearLayoutManager);
        mRecycler.setLayoutManager(new LinearLayoutManager(SearchActivity.this, RecyclerView.VERTICAL, false) {
            @Override
            public void onLayoutCompleted(RecyclerView.State state) {
                super.onLayoutCompleted(state);
            }
        });


        searchAdapter.addlist(ListOriginsearchAdapt);
        mRecycler.setAdapter(searchAdapter);
    }

    private void searchword(String textSearch){

        ArrayList<Search_Item> searchList = new ArrayList<>();
        if(textSearch.isEmpty()) searchAdapter.addlist(ListOriginsearchAdapt);;
        if (searchList.size()>0){
            searchAdapter.clear();
        }
        for ( Search_Item item:getListProduct()){

//            if (item.getName().toLowerCase().contains(textSearch.toLowerCase())){
//                searchList.add(item);
//            }
            if (VNcontains(item.getName().toLowerCase(),textSearch.toLowerCase()) == true){
                searchList.add(item);
            }

        }
        searchAdapter.addlist(searchList);
        searchAdapter.notifyDataSetChanged();

    }

    private List<Search_Item> getListProduct(){
        List<Search_Item> products = new ArrayList<>();
        products.add( new Search_Item("Áo thun nam POLO trơn vải cá sấu cotton cao cấp ngắn tay cực sang trọng","Quần áo"));
        products.add( new Search_Item("Mũ lưỡi trai ❤️ Nón kết thêu chữ Memorie phong cách Ulzzang","Quần áo"));
        products.add( new Search_Item("Quần áo phòng dịch đi máy bay đã kiểm định","Quần áo"));
        products.add( new Search_Item("Tất ngắn nam Vớ thấp cổ 4 màu trơn chống hôi chân","Quần áo"));
        products.add( new Search_Item("Bộ Quần Áo Mặc Nhà Thể Thao Nam Mùa Hè Phong Cách Cao Cấp ZERO","Quần áo"));

        products.add( new Search_Item("Sách - Ngàn Năm Áo Mũ - Trần Quang Đức","Sách"));
        products.add( new Search_Item("Sách Chiếc Áo Xường Xám Màu Hoa Đào","Sách"));
        products.add( new Search_Item("Sách - Trường Phái Kinh Tế Học Áo - Lược Khảo","Sách"));
        products.add( new Search_Item("Bút gấu brow mặc áo cosplay ngộ nghĩnh ROSESHOP (C13)","văn phòng"));

        products.add( new Search_Item("Pin cúc áo CR1632 Lithium 3V dùng cho các thiết bị điện tử","Điện tử"));


        return products;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public int compare(String arg1, String arg2) {
        return VNCollator.compare(arg1, arg2);
    }

    private static boolean VNcontains(String source, String target) {
        if (target.length() > source.length()) {
            return false;
        }

        Collator collator = Collator.getInstance();
        collator.setStrength(Collator.PRIMARY);

        int end = source.length() - target.length() + 1;

        for (int i = 0; i < end; i++) {
            String sourceSubstring = source.substring(i, i + target.length());

            if (collator.compare(sourceSubstring, target) == 0) {
                return true;
            }
        }

        return false;
    }

}