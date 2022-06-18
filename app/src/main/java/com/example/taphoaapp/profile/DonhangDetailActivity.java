package com.example.taphoaapp.profile;

import static java.lang.Math.toIntExact;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NavUtils;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.taphoaapp.Basket.BasketProductAdapter;
import com.example.taphoaapp.Basket.DataCommunication;
import com.example.taphoaapp.BasketFragment;
import com.example.taphoaapp.DetailProduct.SpinnerColor;
import com.example.taphoaapp.DetailProduct.SpinnerColorAdapter;
import com.example.taphoaapp.MainActivity;
import com.example.taphoaapp.R;
import com.example.taphoaapp.basket_product_item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DonhangDetailActivity extends AppCompatActivity implements DataCommunication {

    DataCommunication mCallback;

    private RecyclerView mRecycler;
    private DonHangProductAdapter BasproAdapter;
    basket_product_item product_item;

    List<basket_product_item> products;

    private TabLayout mTabLayout;
    private ViewPager viewMain;
    private BottomNavigationView bottomnavigation;
    private ImageView main , one , two , three;
    private TextView MaHD,Status , nguoiOrder,tvpay,date,phone,delivery,TransFee,address;
    private Spinner spinColor , spinSize;
    SpinnerColorAdapter spinnerColor;
    String name, image, discount, Namevalue, ColorVal , SizeVal, category,IDsp ;
    Integer soluong,giacu,gia;
    Integer size;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userID;
    String Password;

    private String passName,passCategory,passcolor,passsize, maDH;
    private int passPrice,passquantity,passSoluong;
    basket_product_item productItem;
    basket_product_item ProtrungGian;

    List <String> TrungGian;
    List<String> listColor;
    List<String> listSize;
    String PrevActive;
    Bundle extras ;
    private Button add,order, cancelOrder;
//    DataCommunication mCallback;

//    @Override
//    public void attachBaseContext(Context context) {
//        super.attachBaseContext(context);
//
//        // This makes sure that the container activity has implemented
//        // the callback interface. If not, it throws an exception
//        try {
//            mCallback = (DataCommunication) this;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString()
//                    + " must implement DataCommunication");
//        }
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail__order);


        mRecycler = findViewById(R.id.DonHang_productRC);
        BasproAdapter = new DonHangProductAdapter(this);
        mRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false) {
            @Override
            public void onLayoutCompleted(RecyclerView.State state) {
                super.onLayoutCompleted(state);
            }
        });

        extras = getIntent().getExtras();
        Locale locales = new Locale("vi");
        Locale.setDefault(locales);
        Configuration config = new Configuration();
        config.locale = locales;
        this.getApplicationContext().getResources().updateConfiguration(config, null);
//        mCallback = (DataCommunication) this.getApplicationContext();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Intent i = getIntent();
        Bundle extras = getIntent().getExtras();

        if ( i!= null &&extras != null) {
            userID = i.getStringExtra("userID");
            maDH = i.getStringExtra("maDH");
            Password = i.getStringExtra("password");
        }
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        MaHD = findViewById(R.id.tvOrderName);
        Status = findViewById(R.id.tvStatusOrder);
        nguoiOrder = findViewById(R.id.tvPersonName);
        tvpay = findViewById(R.id.tvPrice);
        date = findViewById(R.id.tvDate);
        phone = findViewById(R.id.tvPhone);
        delivery = findViewById(R.id.tvDelivery);
        TransFee = findViewById(R.id.tvTransFee);
        address = findViewById(R.id.tvAddress);
        cancelOrder = findViewById(R.id.DonHang_Huy);

//        MaHD.setText("DH02");
//        Status.setText("Đang lấy hàng");
//        nguoiOrder.setText("Diệp Đức Tân");
//        date.setText("16-thg 6-2022");
//        phone.setText("0902548260");
////        delivery.setText("");
//        TransFee.setText("15.000đ");
//        address.setText("828 Sư Vạn Hạnh, Phường 13, Quận 10");
//        tvpay.setText(currencyFormatter.format(104000 ));

        getListProduct();



        if (extras != null) {
            Namevalue = extras.getString("NAME");
            //The key argument here must match that used in the other activity
        }

        cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Status.getText() == "đang lấy hàng . . .") {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    db.collection("Don_hang").document(maDH).update("status","Đã hủy").addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(DonhangDetailActivity.this);
                                            builder.setMessage("Hủy Đơn hàng thành công")
                                                    .setTitle("Thông báo");
                                            builder.show();
                                        }
                                    });
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    dialog.dismiss();
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(DonhangDetailActivity.this);
                    builder.setMessage("Có chắc muốn hủy đơn hàng này ?").setPositiveButton("Có", dialogClickListener)
                            .setNegativeButton("Không", dialogClickListener).show();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(DonhangDetailActivity.this);
                    builder.setMessage("Đơn hàng đã quá thời hạn có thể hủy")
                            .setTitle("Thông báo");
                    builder.show();
                }
            }
        });

//
//        ConstraintLayout ml = findViewById(R.id.detail_Parrent_Constraint);
//        ml.invalidate();

        Toolbar toolbar = (Toolbar) findViewById(R.id.DonHangdetail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chi tiết Đơn hàng" + maDH);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);


        NestedScrollView scrollView = findViewById(R.id.myScrollView);
        scrollView.invalidate();
        scrollView.requestLayout();

}
    private List<basket_product_item> getListProduct(){
        products = new ArrayList<>();
//        products.add( new basket_product_item("Quần áo","SP02","xám","Áo thun nam POLO trơn vải cá sấu cotton cao cấp ngắn tay cực sang trọng",1,89000,"L",20));
//        products.add( new basket_product_item("Quần áo","SP03","xanh tía","Mũ lưỡi trai ❤ Nón kết thêu chữ Memorie phong cách Ulzzang",5,225000,"",6));
        db.collection("Don_hang")
                .whereEqualTo("DonHang_Id", maDH)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                MaHD = findViewById(R.id.tvOrderName);
                                Status = findViewById(R.id.tvStatusOrder);
                                nguoiOrder = findViewById(R.id.tvPersonName);
                                tvpay = findViewById(R.id.tvPrice);
                                date = findViewById(R.id.tvDate);
                                phone = findViewById(R.id.tvPhone);
                                delivery = findViewById(R.id.tvDelivery);
                                TransFee = findViewById(R.id.tvTransFee);
                                address = findViewById(R.id.tvAddress);

                                MaHD .setText(document.getString("DonHang_Id"));
                                Status.setText(document.getString("status"));
                                nguoiOrder.setText(document.getString("name"));
                                tvpay.setText(document.getLong("TongThanhToan").toString());
                                date.setText(document.getString("ngaydat"));
                                phone.setText(document.getString("SoDienThoai"));
                                if(document.getBoolean("nhận tại cửa hàng")==true) {
                                    delivery.setText("nhận tại cửa hàng");
                                }
                                else{
                                    delivery.setText("giao hàng");
                                }
                                TransFee.setText(document.getLong("phiVanChuyen").toString());
                                address.setText(document.getString("DiaChi"));


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
                                BasproAdapter.setData(products);
                                mRecycler.setAdapter(BasproAdapter);

                            }
                        } else {

                        }
                    }
                });

//        BasproAdapter.setData(products);
//        mRecycler.setAdapter(BasproAdapter);
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

    @Override
    public Intent getSupportParentActivityIntent() {
        return getParentActivityIntentImpl();
    }

    @Override
    public Intent getParentActivityIntent() {
        return getParentActivityIntentImpl();
    }

    private Intent getParentActivityIntentImpl() {
        Intent i = null;
        if (extras != null) {
            PrevActive = extras.getString("prevActive");
            //The key argument here must match that used in the other activity
        }

        // Here you need to do some logic to determine from which Activity you came.
        // example: you could pass a variable through your Intent extras and check that.
        if (PrevActive == "MainActivity") {
            i = new Intent(this, MainActivity.class);
            // set any flags or extras that you need.
            // If you are reusing the previous Activity (i.e. bringing it to the top
            // without re-creating a new instance) set these flags:
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            // if you are re-using the parent Activity you may not need to set any extras
            i.putExtra("prevActive", "DetailProduct");
        } else {
            i = new Intent(this, BasketFragment.class);
            // same comments as above
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            i.putExtra("prevActive", "DetailProduct");
        }

        return i;
    }

    @Override
    public boolean getaddtoBasket() {
        return false;
    }

    @Override
    public void setaddtoBasket(boolean getaddtoBasket) {

    }

    @Override
    public String getPassName() {
        return passName;
    }

    @Override
    public void setPassName(String passName) {
        this.passName = passName;
    }

    @Override
    public String getPassCategory() {
        return passCategory;
    }

    @Override
    public void setPassCategory(String passCategory) {
        this.passCategory = passCategory;
    }

    @Override
    public int getPassquantity() {
        return passquantity;
    }

    @Override
    public void setPassquantity(int passquantity) {
        this.passquantity = passquantity;
    }

    @Override
    public String getPasscolor() {
        return passcolor;
    }

    @Override
    public void setPasscolor(String passcolor) {
        this.passcolor = passcolor;
    }

    @Override
    public String getPasssize() {
        return passsize;
    }

    @Override
    public void setPasssize(String passsize) {
        this.passsize = passsize;
    }

    @Override
    public int getPassPrice() {
        return passPrice;
    }

    @Override
    public void setPassPrice(int passPrice) {
        this.passPrice = passPrice;
    }

    @Override
    public int getPassSoluong() {
        return passSoluong;
    }

    @Override
    public void setPassSoluong(int passSoluong) {
        this.passSoluong = passSoluong;
    }

    @Override
    public void setPrevActive(String PrevActive) {
        this.PrevActive = PrevActive;
    }

    @Override
    public String getPrevActive() {
        return PrevActive;
    }
}