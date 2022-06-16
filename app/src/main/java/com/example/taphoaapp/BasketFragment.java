package com.example.taphoaapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.taphoaapp.Basket.BasketProductAdapter;
import com.example.taphoaapp.Basket.DataCommunication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BasketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BasketFragment extends Fragment {

    private RecyclerView mRecycler;
    private BasketProductAdapter BasproAdapter;
    private View mView;
    private TextView total,TransFee,IdDonHang;
    List<basket_product_item> products;
    DataCommunication mCallback;
    private Button order;
    Bundle extras ;
    String prevActive,ActiPrev,PassCategory,PassName,userID;
     private Integer PassPrice,PassQuantity,PassSoluong;
    private EditText name, address,phone;
    private CheckBox nhanhang;
   private Intent i;
   basket_product_item product_item;
   private ImageView btnAdd, btnSubtract;
   int FinalTong;
   Integer size;
   FirebaseFirestore db = FirebaseFirestore.getInstance();

    String Category,Name;
    Integer price, numdat, soluong;

    private RecyclerViewReadyCallback recyclerViewReadyCallback;

    public interface RecyclerViewReadyCallback {
        void onLayoutReady();
    }



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Locale locale = new Locale("vi", "VN");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

    @Override
    public void onResume() {
        super.onResume();
        total.setText(String.valueOf(currencyFormatter.format(FinalTong)));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (DataCommunication) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DataCommunication");
        }
    }



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
//        extras = getActivity().getIntent().getExtras();
        if (requireActivity().getIntent().hasExtra("prevActive")) {
            prevActive = savedInstanceState.getString("prevActive");
        }

        i = getActivity().getIntent();
        extras = getActivity().getIntent().getExtras();

        if ( i!= null &&extras != null) {

            PassCategory = i.getStringExtra("PrevActive");
            PassName = i.getStringExtra("PrevActive");
            PassPrice = i.getIntExtra("PrevActive",0);
            PassQuantity = i.getIntExtra("PrevActive",0);
            PassSoluong= i.getIntExtra("PrevActive",0);

            product_item = (basket_product_item) i.getSerializableExtra("productItem");


                userID = i.getStringExtra("userID");


            ActiPrev = i.getStringExtra("PrevActive");

            if( ActiPrev != null) {
                Log.e("PrevActive : ", ActiPrev.toString());
            }
            // and get whatever type user account id is
        }

        RelativeLayout ml = mView.findViewById(R.id.basket_root);
        ml.invalidate();

        order =mView.findViewById(R.id.btnPlaceOrder);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(products != null) {
                    OrderListProduct(products);
                    // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Đặt hàng thành công!")
                            .setTitle("Thông báo");

                    builder.show();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Giỏ hàng trống!")
                            .setTitle("Thông báo");

                    builder.show();
                }

            }
        });

        NestedScrollView scrollView = mView.findViewById(R.id.basket_myScrollView);
        scrollView.invalidate();
        scrollView.requestLayout();
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
        TransFee = mView.findViewById(R.id.tvTransFee);
        name= mView.findViewById(R.id.editTextPersonName);
        address = mView.findViewById(R.id.editTextAdress);
        phone = mView.findViewById(R.id.editTextPhone);
        nhanhang = mView.findViewById(R.id.basket_deliver_check);
        IdDonHang = mView.findViewById(R.id.tvIdDonHang);




        BasproAdapter = new BasketProductAdapter(getActivity(), BasketFragment.this);

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
//        mRecycler.setLayoutManager(linearLayoutManager);



        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false) {
            @Override
            public void onLayoutCompleted(RecyclerView.State state) {
                super.onLayoutCompleted(state);
//                total.setText(String.valueOf(currencyFormatter.format(BasproAdapter.getTinh())));
            }


//            @Override
//            public void onItemsChanged(@NonNull RecyclerView recyclerView) {
//                super.onItemsChanged(recyclerView);
//                int FinalTong = 0;
//                for (int x = mRecycler.getChildCount(), i = 0; i < x; i++) {
//                    BasketProductAdapter.ProductViewHolder holder = (BasketProductAdapter.ProductViewHolder) mRecycler.getChildViewHolder(mRecycler.getChildAt(i));
//                    FinalTong += holder.getNumtong();
//                }
//                Toast.makeText(getActivity(), "Đã chạy OnTouch", Toast.LENGTH_LONG).show();
//                // i have been touched
//                total.setText(String.valueOf(currencyFormatter.format(FinalTong)));
//            }

        });

        getListProduct();

//        BasproAdapter.setData(getListProduct());
//        mRecycler.setAdapter(BasproAdapter);

        mRecycler.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (recyclerViewReadyCallback != null) {
                    recyclerViewReadyCallback.onLayoutReady();
                }
                mRecycler.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        recyclerViewReadyCallback = new RecyclerViewReadyCallback() {
            @Override
            public void onLayoutReady() {
                total.setText(String.valueOf(currencyFormatter.format(BasproAdapter.getTinh())));
                //here comes your code that will be executed after all items are laid down
            }
        };



//        mRecycler.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int FinalTong = 0;
//                for (int x = mRecycler.getChildCount(), i = 0; i < x; i++) {
//                    BasketProductAdapter.ProductViewHolder holder = (BasketProductAdapter.ProductViewHolder) mRecycler.getChildViewHolder(mRecycler.getChildAt(i));
//                    FinalTong += holder.getNumtong();
//                }
//                Toast.makeText(getActivity(), "Đã chạy OnTouch", Toast.LENGTH_SHORT).show();
//                // i have been touched
//                total.setText(String.valueOf(currencyFormatter.format(FinalTong)));
//            }
//        });


//        mView.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View arg0, MotionEvent event) {
//                if(event.getActionMasked()  == MotionEvent.ACTION_UP){
//                    int FinalTong = 0;
//                    for (int x = mRecycler.getChildCount(), i = 0; i < x; i++) {
//                        BasketProductAdapter.ProductViewHolder holder = (BasketProductAdapter.ProductViewHolder) mRecycler.getChildViewHolder(mRecycler.getChildAt(i));
//                        FinalTong += holder.getNumtong();
//                    }
//                    Toast.makeText(getActivity(), "Đã chạy OnTouch", Toast.LENGTH_LONG).show();
//                    // i have been touched
//                    total.setText(String.valueOf(currencyFormatter.format(FinalTong)));
//                    return true;
//                }
//                if(event.getActionMasked()  == MotionEvent.ACTION_DOWN){
//                    // you can implement this
//
//                    return true;
//                }
//                return false;
//            }
//        });

        return mView;
    }

    private List<basket_product_item> getListProduct(){
        products = new ArrayList<>();

//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        products.add( new product_item("https://cf.shopee.vn/file/34acd5e930c8a21e1c3a70d3cf2a70c5","Áo thun nam POLO trơn vải cá sấu cotton cao cấp ngắn tay cực sang trọng","55%",2,198000,89000));
//        products.add( new product_item("https://cf.shopee.vn/file/b2612c1a8242069aced2f2f26b592f38","Mũ lưỡi trai ❤️ Nón kết thêu chữ Memorie phong cách Ulzzang","22%",5,58000,45000));
//        products.add( new product_item("https://cf.shopee.vn/file/
//        products.add( new product_item("QuầnÁO","Áo thun nam POLO trơn vải cá sấu cotton cao cấp ngắn tay cực sang trọng",89000,2,20));
//        products.add( new product_item("QuầnÁO","Mũ lưỡi trai ❤️ Nón kết thêu chữ Memorie phong cách Ulzzang",45000,5,5));

  //      if (prevActive == "DetailProduct"||prevActive == null)
//        if(i!= null && extras !=null &&ActiPrev.toString().equalsIgnoreCase("DetailProduct"))
//        {products.add( new basket_product_item(product_item.getCategory(), product_item.getName(),product_item.getMau(),product_item.getSize(),product_item.getSoluong(),product_item.getPrice(),product_item.getNumdat())); }
     //   {products.add( new product_item(PassCategory, PassName,PassPrice,PassQuantity,PassSoluong)); }

        db.collection("Gio_hang")
                .document(userID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if(document != null){
                    if (document.exists()) {
                        name.setText(document.getString("name"));
                        address.setText(document.getString("DiaChi"));
                        phone.setText(document.getString("SoDienThoai"));
                        nhanhang.setChecked(document.getBoolean("giaohang"));
                        IdDonHang.setText(document.getString("DonHang_Id"));
                        if (document.get("ListProducts") != null) {
//                            products = (List<basket_product_item>) document.get("ListProducts");
//                            Log.d("check",((List<?>) document.get("ListProducts")).get(0).toString().sp+"");
//                            for(Array set : document.get("ListProducts").toArray() )
                        }
                        BasproAdapter.setData(products);
                        mRecycler.setAdapter(BasproAdapter);
                    }else{FillBasket();}
                    }else {
                       FillBasket();
                    }
                } else {
                    FillBasket();
                }
            }
        });


        return products;
    }

    private void FillBasket(){
        Map<String, Object> data = new HashMap<>();
        db.collection("Gio_hang")
                .document("Counter")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    size =  (document.getDouble("Count").intValue() +1);
                    data.put("DonHang_Id", "DH"+size);
                    data.put("name", "");
                    data.put("phiVanChuyen", "");
                    data.put("DiaChi", "");
                    data.put("SoDienThoai", "");
                    data.put("TongThanhToan", "");
                    data.put("giaohang", false);
                    if(ActiPrev != null) {
                        if (i != null && extras != null && ActiPrev.toString().equalsIgnoreCase("DetailProduct")) {
                            data.put("ListProducts", Arrays.asList(product_item));


                            db.collection("Gio_hang").document(userID).set(data);
                        }
                        else{
                            data.put("ListProducts",Arrays.asList());
                            db.collection("Gio_hang").document(userID).set(data);}
                    }
                    else{
                        data.put("ListProducts",Arrays.asList());
                        db.collection("Gio_hang").document(userID).set(data);
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Giỏ hàng mới tạo lần đầu , mời load lại để hiển thị chính xác!")
                            .setTitle("Thông báo");

                    builder.show();
                    getListProduct();
                    Log.e("documment", "Error getting documents: ");
                } else {
                    Log.d("this", "Error getting documents: ");
                }
            }
        });

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

    private void OrderListProduct( List<basket_product_item> products ){
        for(int tmp = 0 ; tmp < products.size();tmp++) {
            if(products.get(tmp).getNumdat() > products.get(tmp).getSoluong()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Sản phẩm"+products.get(tmp).getName()+" không đủ số lượng đặt")
                        .setTitle("Thông báo");
                builder.show();
                return;
            }
            else if(products.get(tmp).getNumdat() <=0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Sản phẩm"+products.get(tmp).getName()+"số lượng đặt không hợp lệ")
                        .setTitle("Thông báo");
                builder.show();
                return;
            }
        }


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Date d = new Date();
        Map<String, Object> order = new HashMap<>();
        order.put("name", name.getText().toString());
        order.put("DiaChi", address.getText().toString());
        order.put("SoDienThoai", phone.getText().toString());
        order.put("phiVanChuyen", TransFee.getText().toString());
        order.put("TongThanhToan", total.getText().toString());
        order.put("nhận tại cửa hàng", nhanhang.isChecked());
        order.put("DonHang_Id", IdDonHang.getText().toString());

        order.put("ListProducts", products);



        db.collection("Don_hang").document(name.getText().toString()+d.getTime())
                .set(order)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("PostOrder", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("PostOrder", "Error writing document", e);
                    }
                });

        for(int tmp = 0 ; tmp < products.size();tmp++) {
            double SoLuong;
            if(products.get(tmp).getSoluong() -products.get(tmp).getNumdat() ==0) SoLuong = 0;
            else if(products.get(tmp).getSoluong() - products.get(tmp).getNumdat() <0) SoLuong = products.get(tmp).getSoluong();
            else SoLuong = products.get(tmp).getSoluong() - products.get(tmp).getNumdat();
            db.collection("SAN_PHAM").document(products.get(tmp).getID())
                    .update("SOLUONG",(SoLuong ))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.e("UpdateSoLuongSP", "Thành công!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("UpdateSoLuongSP", "Thất bại!");
                        }
                    });
        }

    }

    public void SetTotalPrice(){
        //        Log.e("fianlPrice",String.valueOf(FinalTong));
//        Toast.makeText(getActivity(), "Đã chạy OnTouch", Toast.LENGTH_SHORT).show();
        FinalTong = 0;
        for (int x = mRecycler.getChildCount(), i = 0; i < x; i++) {
            BasketProductAdapter.ProductViewHolder holder = (BasketProductAdapter.ProductViewHolder) mRecycler.getChildViewHolder(mRecycler.getChildAt(i));
            FinalTong += holder.getNumtong();
        }
        total.setText(String.valueOf(currencyFormatter.format(FinalTong)));
    }
//    public void changeText(int data,TextView tv)
//    {
//        tv.setText(String.valueOf(currencyFormatter.format(data)));
//    }
}