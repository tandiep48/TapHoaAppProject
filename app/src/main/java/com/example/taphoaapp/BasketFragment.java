package com.example.taphoaapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
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
import android.widget.Toast;

import com.example.taphoaapp.Basket.BasketProductAdapter;
import com.example.taphoaapp.Basket.DataCommunication;
import com.example.taphoaapp.profile.ProfileMainFragment;
import com.example.taphoaapp.profile.changeInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BasketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BasketFragment extends Fragment implements DataCommunication  {

    private RecyclerView mRecycler;
    private BasketProductAdapter BasproAdapter;
    private View mView;
    private TextView total,TransFee,IdDonHang;
    List<basket_product_item> products;
    List<basket_product_item> Sosanhproducts;
    DataCommunication mCallback;
    private Button order;
    Bundle extras ;
    String prevActive,ActiPrev,PassCategory,PassName,userID,Area;
     private Integer PassPrice,PassQuantity,PassSoluong,PhiVC;
    private EditText name, address,phone;
    private CheckBox nhanhang;
   private Intent i;
   basket_product_item product_item;
   private ImageView btnAdd, btnSubtract;
   int FinalTong;
   Integer size;
   FirebaseFirestore db = FirebaseFirestore.getInstance();
   basket_product_item ProtrungGian;
   boolean getaddtoBasket,addDonHang;
    private NestedScrollView mSrlLayout;

    private FirebaseAuth mAuth;
    int sizeTaoDonHang = 0;
    Spinner spinerArea;
    String Quan;
    Boolean checkDistric;
    String MaHD;

    String Category,Name;
    Integer price, numdat, soluong;

    private RecyclerViewReadyCallback recyclerViewReadyCallback;

    @Override
    public boolean getaddtoBasket() {
        return getaddtoBasket;
    }

    @Override
    public void setaddtoBasket(boolean getaddtoBasket) {

    }

    @Override
    public String getPassName() {
        return null;
    }

    @Override
    public void setPassName(String passName) {

    }

    @Override
    public String getPassCategory() {
        return null;
    }

    @Override
    public void setPassCategory(String passCategory) {

    }

    @Override
    public int getPassquantity() {
        return 0;
    }

    @Override
    public void setPassquantity(int passquantity) {

    }

    @Override
    public String getPasscolor() {
        return null;
    }

    @Override
    public void setPasscolor(String passcolor) {

    }

    @Override
    public String getPasssize() {
        return null;
    }

    @Override
    public void setPasssize(String passsize) {

    }

    @Override
    public int getPassPrice() {
        return 0;
    }

    @Override
    public void setPassPrice(int passPrice) {

    }

    @Override
    public int getPassSoluong() {
        return 0;
    }

    @Override
    public void setPassSoluong(int passSoluong) {

    }

    @Override
    public void setPrevActive(String PrevActive) {

    }

    @Override
    public String getPrevActive() {
        return null;
    }

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
        MainActivity activity = (MainActivity) getActivity();
        getaddtoBasket =  activity.getaddmybasket();
        if(getaddtoBasket)
        {
//            Log.e("BaskFrag:",Boolean.toString(getaddtoBasket));
            getListProduct();
            getaddtoBasket = false;
            getActivity().getIntent().putExtra("addtoBask",getaddtoBasket);
        }
//        Toast.makeText(getContext(),Boolean.toString(getaddtoBasket),Toast.LENGTH_SHORT).show();
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

    public boolean getaddDonHang(){
        return addDonHang;
    }

    public void productsDelAt(int position){
        if(products != null || !products.isEmpty()||products.size() >0)
        products.remove(position);
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
        }mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mView =  inflater.inflate(R.layout.fragment_basket, container, false);
//        extras = getActivity().getIntent().getExtras();
        mSrlLayout = mView.findViewById(R.id.basket_myScrollView);
        products = new ArrayList<>();
        PhiVC = 10000;
        Area = "Quận 3";
        checkDistric = false;

        mAuth = FirebaseAuth.getInstance();
        mRecycler = mView.findViewById(R.id.rvFoods);
        total = mView.findViewById(R.id.tvTotal);
        TransFee = mView.findViewById(R.id.tvTransFee);
        name= mView.findViewById(R.id.editTextPersonName);
        address = mView.findViewById(R.id.editTextAdress);
        phone = mView.findViewById(R.id.editTextPhone);
        nhanhang = mView.findViewById(R.id.basket_deliver_check);
        IdDonHang = mView.findViewById(R.id.tvIdDonHang);
//
//        if (requireActivity().getIntent().hasExtra("prevActive")) {
//            prevActive = savedInstanceState.getString("prevActive");
//        }

        i = getActivity().getIntent();
        extras = getActivity().getIntent().getExtras();


        if ( i!= null &&extras != null) {

//            PassCategory = i.getStringExtra("PrevActive");
//            PassName = i.getStringExtra("PrevActive");
//            PassPrice = i.getIntExtra("PrevActive",0);
//            PassQuantity = i.getIntExtra("PrevActive",0);
//            PassSoluong= i.getIntExtra("PrevActive",0);

            product_item = (basket_product_item) i.getSerializableExtra("productItem");
            getaddtoBasket = i.getBooleanExtra("addtoBask",false);
//            Toast.makeText(getContext(),Boolean.toString(getaddtoBasket),Toast.LENGTH_SHORT).show();

//                userID = i.getStringExtra("userID");


            ActiPrev = i.getStringExtra("PrevActive");

            if( ActiPrev != null) {
                Log.e("PrevActive : ", ActiPrev.toString());
            }
            // and get whatever type user account id is
        }

        userID = mAuth.getCurrentUser().getUid();
        RelativeLayout ml = mView.findViewById(R.id.basket_root);
        ml.invalidate();

        order =mView.findViewById(R.id.btnPlaceOrder);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().isEmpty()) {
                    name.setError("Tên không được để trống");
                    name.requestFocus();
                }
                if(!nhanhang.isChecked()&& address.getText().toString().isEmpty()) {
                    address.setError("Địa chỉ không được để trống khi chọn giao hàng");
                    address.requestFocus();
                }
                else {
                     if (products == null ||products.isEmpty()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Giỏ hàng trống!")
                                .setTitle("Thông báo");

                        builder.show();
                    }
                    else {
                        checkQuan();
                        if(checkDistric) {
                            addDonHang = true;
                            MainActivity activity = (MainActivity) getActivity();
                            activity.changeAddmyDonhang(addDonHang);
                            getActivity().getIntent().putExtra("addtoDonhang", addDonHang);
                            Log.e("BasketAddDonHang", Boolean.toString(addDonHang));
                            OrderListProduct(products);
                            // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
//                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                        builder.setMessage("Đặt hàng thành công!")
//                                .setTitle("Thông báo");
//
//                        builder.show();
                        }
                    }
                }

            }
        });
        address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                checkQuan();
            }
        });

        NestedScrollView scrollView = mView.findViewById(R.id.basket_myScrollView);
        scrollView.invalidate();
        scrollView.requestLayout();
        spinerArea = mView.findViewById(R.id.basket_area_spinner);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_spinner_item, getListArea());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinerArea.setAdapter(adapter);
        spinerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               Area = spinerArea.getSelectedItem().toString();
//                Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT);
//                Toast.makeText(getActivity(), spinerArea.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                checkQuan();
                if(nhanhang.isChecked()){ChangeTransFee(0);}
                else if(Area.equalsIgnoreCase("Quận 3") ||Area.equalsIgnoreCase("Quận 1") || Area.equalsIgnoreCase("Quận 10")){
                    ChangeTransFee(10000);
                }
                else if (Area.equalsIgnoreCase("Phú Nhuận") ||Area.equalsIgnoreCase("Bình Thạnh") || Area.equalsIgnoreCase("Tân Bình")){
                    ChangeTransFee(15000);
                }
                else if(Area.equalsIgnoreCase("Quận 5") ||Area.equalsIgnoreCase("Quận 4") )
                    {
                        ChangeTransFee(18000);
                    }

                else if(Area.equalsIgnoreCase("Quận 2") ||Area.equalsIgnoreCase("Quận 11") ){
                    {
                        ChangeTransFee(20000);
                    }
                }
                else if(Area.equalsIgnoreCase("Quận 6") ||Area.equalsIgnoreCase("Quận 7") || Area.equalsIgnoreCase("Quận 8")){
                    ChangeTransFee(25000);
                }
                else
                {
                    ChangeTransFee(30000);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        db.collection("User")
                .document(mAuth.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
//                    size =  (document.getDouble("Count").intValue() +1);
                    name.setText(document.getString("fullName"));
                    phone.setText(document.getString("phone"));
                } else {
                    Log.d("this", "Error getting documents: ");
                }
            }
        });



        nhanhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nhanhang.isChecked())
                {
                    TransFee.setText(String.valueOf(currencyFormatter.format(0)));
                    PhiVC = 0;
                    SetTotalPrice();
                }
                else{
                    TransFee.setText(String.valueOf(currencyFormatter.format(PhiVC)));
                    if(Area.equalsIgnoreCase("Quận 3") ||Area.equalsIgnoreCase("Quận 1") || Area.equalsIgnoreCase("Quận 10")){
                        ChangeTransFee(10000);
                    }
                    else if (Area.equalsIgnoreCase("Phú Nhuận") ||Area.equalsIgnoreCase("Bình Thạnh") || Area.equalsIgnoreCase("Tân Bình")){
                        ChangeTransFee(15000);
                    }
                    else if(Area.equalsIgnoreCase("Quận 5") ||Area.equalsIgnoreCase("Quận 4") )
                    {
                        ChangeTransFee(18000);
                    }

                    else if(Area.equalsIgnoreCase("Quận 2") ||Area.equalsIgnoreCase("Quận 11") ){
                        {
                            ChangeTransFee(20000);
                        }
                    }
                    else if(Area.equalsIgnoreCase("Quận 6") ||Area.equalsIgnoreCase("Quận 7") || Area.equalsIgnoreCase("Quận 8")){
                        ChangeTransFee(25000);
                    }
                    else
                    {
                        ChangeTransFee(30000);
                    }
                }
            }
        });
        BasproAdapter = new BasketProductAdapter(getActivity(), BasketFragment.this);

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
//        mRecycler.setLayoutManager(linearLayoutManager);



        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false) {
            @Override
            public void onLayoutCompleted(RecyclerView.State state) {
                super.onLayoutCompleted(state);
//                total.setText(String.valueOf(currencyFormatter.format(BasproAdapter.getTinh())));
                SetTotalPrice();
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
//                total.setText(String.valueOf(currencyFormatter.format(BasproAdapter.getTinh())));
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

    private void checkQuan(){
        int start =0 ;
        int end = 0;
        String tam2;
        String tam = address.getText().toString();
        if(!tam.isEmpty()) {
            Log.e("addressFull", tam);
            if(tam.contains("Quận")) {
                Matcher m = Pattern.compile("\\bQuận($| )").matcher(tam);
                while (m.find()) {
                    start = m.start();
                    end = m.end();
                }
                tam2 = tam.substring(start);
                if (tam2.contains(",")) {
                    Quan = tam2.substring(0, tam2.indexOf(","));
                    Log.e("Quan", Quan);
                    if (!Quan.equalsIgnoreCase(spinerArea.getSelectedItem().toString())) {
                        Log.e("spinner Area", spinerArea.getSelectedItem().toString());
                        address.setError("Quận ở Địa chỉ nhập khác với Quận được chọn ");
                        checkDistric = false;
                    } else {
                        checkDistric = true;
                        address.setError(null);
                    }
                } else {
                    checkDistric = false;
                    address.setError("Địa chỉ nhập sai định dạng");
                }
            }
            else{
                checkDistric = false;
                address.setError("Vui lòng nhập Địa chỉ theo định dạng số nhà Tên đường, Phường , Quận , thành phố");
            }
        }
    }

    private List<basket_product_item> getListProduct(){
        db.collection("Don_hang")
                .document("Counter")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    size =  (document.getDouble("Count").intValue() +1);
                    db.collection("Gio_hang")
                            .document(userID)
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if(document != null){
                                    if (document.exists()) {
                                        if(document.getString("name")!= null && document.getString("name")!="" ) {
                                            name.setText(document.getString("name"));
                                        }
                                        if(document.getString("DiaChi")!= null) {
                                            if (document.getString("DiaChi").toString() != "") {
                                                address.setText(document.getString("DiaChi"));
                                            }
                                        }
                                        else{
                                            address.setText("828 Sư Vạn Hạnh, Phường 13, Quận 10, TP.HCM");
                                        }
                                        if(document.getString("SoDienThoai")!= null && document.getString("SoDienThoai")!="" ) {
                                            phone.setText(document.getString("SoDienThoai"));
                                        }

                                        nhanhang.setChecked(document.getBoolean("giaohang"));
                                        if(size <=9) {
                                            IdDonHang.setText("DH0"+size);
                                        }
                                        else{
                                            IdDonHang.setText("DH"+size);
                                        }
                                        if (document.get("ListProducts") != null||document.get("ListProducts")!="") {
                                            List<Map<String, Object>> productDOX = (List<Map<String, Object>>) document.get("ListProducts");
                                            for(Map<String, Object> ObjPro : productDOX)
                                            {
                                                ProtrungGian = new basket_product_item();
                                                ProtrungGian.setCategory(ObjPro.get("category").toString());
                                                ProtrungGian.setID(ObjPro.get("id").toString());
                                                if(ObjPro.get("mau")!= null) {
                                                    if (ObjPro.get("mau").toString() != "") {
                                                        ProtrungGian.setMau(ObjPro.get("mau").toString());
                                                    }
                                                }
                                                else{
                                                    ProtrungGian.setMau("");
                                                }


                                                if(ObjPro.get("size")!= null) {
                                                    if (ObjPro.get("size").toString() != "") {
                                                        ProtrungGian.setSize(ObjPro.get("size").toString());
                                                    }
                                                }
                                                else{
                                                    ProtrungGian.setSize("");
                                                }

                                                ProtrungGian.setName(ObjPro.get("name").toString());
                                                ProtrungGian.setNumdat(Integer.parseInt(ObjPro.get("numdat").toString()));
                                                ProtrungGian.setPrice(Integer.parseInt(ObjPro.get("price").toString()));
                                                ProtrungGian.setSoluong(Integer.parseInt(ObjPro.get("soluong").toString()));

                                                products.add(ProtrungGian);
                                            }
//                            products = (List<basket_product_item>) document.get("ListProducts");
//                            Log.d("check",((List<?>) document.get("ListProducts")).get(0).toString().sp+"");
//                            for(Array set : document.get("ListProducts").toArray() )

                                        }

                                        for(int i = 0 ; i < products.size()-1;i++)
                                        {
                                            for(int z = 1 ; z < products.size();z ++)
                                            {
                                                if(products.get(i).getName().equalsIgnoreCase(products.get(z).getName()))
                                                {
                                                    Log.e("BasketRemove:" , products.get(i).getName());
                                                    products.remove(i);
                                                    break;
                                                }
                                            }
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
                } else {
                    db.collection("Don_hang")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            sizeTaoDonHang = task.getResult().size();
                                        }
                                    } else {
                                        Log.e("Đếm Don_hang","Don_Hang chua được tạo!");
                                    }
                                }
                            });


                    Map<String, Object> Counter = new HashMap<>();
                    Counter.put("Count", sizeTaoDonHang);

                    db.collection("Don_hang").document("Counter")
                            .set(Counter)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("Don_Hang", "Tạo mới Counter thành công!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Don_Hang", "Tạo mới Counter thất bại!");
                                }
                            });
                    Log.d("this", "Error getting documents: ");
                }
            }
        });

//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        products.add( new product_item("https://cf.shopee.vn/file/34acd5e930c8a21e1c3a70d3cf2a70c5","Áo thun nam POLO trơn vải cá sấu cotton cao cấp ngắn tay cực sang trọng","55%",2,198000,89000));
//        products.add( new product_item("https://cf.shopee.vn/file/b2612c1a8242069aced2f2f26b592f38","Mũ lưỡi trai ❤️ Nón kết thêu chữ Memorie phong cách Ulzzang","22%",5,58000,45000));
//        products.add( new product_item("https://cf.shopee.vn/file/
//        products.add( new basket_product_item("QuầnÁO","Áo thun nam POLO trơn vải cá sấu cotton cao cấp ngắn tay cực sang trọng",89000,2,20));
//        products.add( new basket_product_item("QuầnÁO","Mũ lưỡi trai ❤️ Nón kết thêu chữ Memorie phong cách Ulzzang",45000,5,5));

//        products.add( new basket_product_item("Quần áo","SP02","xám","Áo thun nam POLO trơn vải cá sấu cotton cao cấp ngắn tay cực sang trọng",1,89000,"L",20));
//        products.add( new basket_product_item("Quần áo","SP03","xám","Mũ lưỡi trai ❤️ Nón kết thêu chữ Memorie phong cách Ulzzang",1,45000,"L",6));

  //      if (prevActive == "DetailProduct"||prevActive == null)
//        if(i!= null && extras !=null &&ActiPrev.toString().equalsIgnoreCase("DetailProduct"))
//        {products.add( new basket_product_item(product_item.getCategory(),product_item.getID(),product_item.getMau(), product_item.getName(),product_item.getNumdat(),product_item.getPrice(),product_item.getSize(),product_item.getSoluong())); }
     //   {products.add( new product_item(PassCategory, PassName,PassPrice,PassQuantity,PassSoluong)); }

//        BasproAdapter.setData(products);
//        mRecycler.setAdapter(BasproAdapter);
        return products;
    }

    public void ChangeTransFee(int VCfee){
        PhiVC = VCfee;
        TransFee.setText(String.valueOf(currencyFormatter.format(PhiVC)));
        SetTotalPrice();
    }

    private void FillBasket(){
        Map<String, Object> data = new HashMap<>();

                    if(size <=9) {
                        data.put("DonHang_Id", "DH0"+size);
                    }
                    else{
                        data.put("DonHang_Id", "DH"+size);
                    }
                    data.put("name", "");
                    data.put("phiVanChuyen", "");
                    data.put("DiaChi", "");
                    data.put("SoDienThoai", "");
                    data.put("TongThanhToan", 0);
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


    }

    private List<CharSequence>  getListArea(){
        List<CharSequence> listSize = new ArrayList<>();
        listSize.add(new String("Quận 1"));
        listSize.add(new String("Quận 2"));
        listSize.add(new String("Quận 3"));
        listSize.add(new String("Quận 4"));
        listSize.add(new String("Quận 5"));
        listSize.add(new String("Quận 6"));
        listSize.add(new String("Quận 7"));
        listSize.add(new String("Quận 8"));
        listSize.add(new String("Quận 9"));
        listSize.add(new String("Quận 10"));
        listSize.add(new String("Quận 11"));
        listSize.add(new String("Quận 12"));
        listSize.add(new String("Quận Bình Thạnh"));
        listSize.add(new String("Quận Phú Nhuận"));
        listSize.add(new String("Quận Tân Bình"));
        listSize.add(new String("Quận Gò Vấp"));
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
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("hh:mm aaa dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        Map<String, Object> order = new HashMap<>();
        db.collection("Don_hang")
                .document("Counter")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                size = (document.getDouble("Count").intValue() +1);
                                if(size <=9) {
                                    IdDonHang.setText("DH0"+size);
                                }
                                else{
                                    IdDonHang.setText("DH"+size);
                                }

                            } else {

                            }
                        } else {

                        }
                    }
                });

        order.put("userID", userID);
        order.put("name", name.getText().toString());
        order.put("DiaChi", address.getText().toString());
        order.put("SoDienThoai", phone.getText().toString());
        order.put("phiVanChuyen", 15000);
        order.put("TongThanhToan",FinalTong);
        order.put("nhận tại cửa hàng", nhanhang.isChecked());
        order.put("DonHang_Id", IdDonHang.getText().toString());
        order.put("ngaydat",formattedDate);
        order.put("soluongsanpham",products.size());
        order.put("status","Đang lấy hàng . . .");

        order.put("ListProducts", products);
        MaHD = userID + formattedDate;
        i.putExtra("MaHD",MaHD);



        db.collection("Don_hang").document(MaHD)
                .set(order)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("PostOrder", "DocumentSnapshot successfully written!");
                        Toast.makeText(getActivity(),"Đặt hàng thành công!",Toast.LENGTH_LONG).show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                MainActivity main = (MainActivity) getActivity();
                                main.changeFragZeroTwo(2);
                            }
                        }, 500);

                        db.collection("Gio_hang").document(userID).update("ListProducts",Arrays.asList());
                        db.collection("Don_hang").document("Counter").update("Count",size);
                        if(size <=9) {
                            db.collection("Gio_hang").document(userID).update("DonHang_Id","DH0" + size + 1);
                            IdDonHang.setText("DH0" + size + 1);
                        }
                        else{
                            db.collection("Gio_hang").document(userID).update("DonHang_Id", "DH" + size + 1);
                            IdDonHang.setText( "DH" + size + 1);
                        }
                        products.clear();
                        BasproAdapter.setData(products);
                        mRecycler.setAdapter(BasproAdapter);


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
        FinalTong = FinalTong + PhiVC ;
        total.setText(String.valueOf(currencyFormatter.format(FinalTong)));
    }
//    public void changeText(int data,TextView tv)
//    {
//        tv.setText(String.valueOf(currencyFormatter.format(data)));
//    }
}