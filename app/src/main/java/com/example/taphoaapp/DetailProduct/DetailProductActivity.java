package com.example.taphoaapp.DetailProduct;

import static java.lang.Math.toIntExact;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NavUtils;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.taphoaapp.Basket.DataCommunication;
import com.example.taphoaapp.BasketFragment;
import com.example.taphoaapp.MainActivity;
import com.example.taphoaapp.R;
import com.example.taphoaapp.basket_product_item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DetailProductActivity extends AppCompatActivity implements DataCommunication {

    DataCommunication mCallback;

    private TabLayout mTabLayout;
    private ViewPager viewMain;
    private BottomNavigationView bottomnavigation;
    private ImageView main , one , two , three;
    private TextView tvname, tvdiscount,tvsoluong,tvgiacu,tvgia,tvMota;
    private Spinner spinColor , spinSize;
    SpinnerColorAdapter spinnerColor;
    String name, image, discount, Namevalue, ColorVal , SizeVal, category,IDsp ;
    Integer soluong,giacu,gia;
    Double size;

    private String passName,passCategory,passcolor,passsize,userID;
    private int passPrice,passquantity,passSoluong;
    basket_product_item productItem;

    List <String> TrungGian;
    List<String> listColor;
    List<String> listSize;
    String PrevActive;
    Bundle extras ;
    private Button add,order;
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
        setContentView(R.layout.fragment_detail_product);
        extras = getIntent().getExtras();
        Locale locales = new Locale("vi");
        Locale.setDefault(locales);
        Configuration config = new Configuration();
        config.locale = locales;
        this.getApplicationContext().getResources().updateConfiguration(config, null);
//        mCallback = (DataCommunication) this.getApplicationContext();
        mCallback = (DataCommunication) DetailProductActivity.this;
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Intent i = getIntent();
        Bundle extras = getIntent().getExtras();

        if ( i!= null &&extras != null) {
            userID = i.getStringExtra("userID");

        }

        add = findViewById(R.id.detail_BuyNow);
        order = findViewById(R.id.detail_Order);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailProductActivity.this, MainActivity.class);
//                intent.putExtra("prevActive", "DetailProduct");

                productItem = new basket_product_item();

                productItem.setID(IDsp);
                productItem.setCategory(category);
                productItem.setName(tvname.getText().toString());
                productItem.setPrice(Integer.parseInt(tvgia.getText().toString()));
                //productItem.setNumdat(Integer.parseInt(tv.getText().toString()));
                productItem.setSoluong(Integer.parseInt(tvsoluong.getText().toString()));
                productItem.setMau(spinColor.getSelectedItem().toString());
                productItem.setSize(spinSize.getSelectedItem().toString());
//                List<product_item> products = new ArrayList<>();
//                products.add(productItem);
//                Map<String, Object> order = new HashMap<>();
//                order.put("ListProducts", products);



                db.collection("Gio_hang").document(userID).update("ListProducts", FieldValue.arrayUnion(productItem)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                db.collection("Gio_hang")
                                        .document("Counter")
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot document = task.getResult();
                                                    if (document.exists()) {
                                                        size = document.getDouble("Count");
                                                    } else {

                                                    }
                                                } else {

                                                }
                                            }
                                        });

                                Map<String, Object> data = new HashMap<>();
                                data.put("name", "");
                                data.put("phiVanChuyen", "");
                                data.put("DonHang_Id", "DH"+size);
                                data.put("DiaChi", "");
                                data.put("SoDienThoai", "");
                                data.put("TongThanhToan", "");
                                data.put("giaohang", "");
                                data.put("ListProducts", Arrays.asList(productItem));


                                db.collection("Gio_hang").document("userID").set(data);
                            }
                        });

                mCallback.setPrevActive("DetailProduct");
                intent.putExtra("Order", "YES");
                intent.putExtra("PrevActive", "DetailProduct");
                intent.putExtra("productItem",productItem);
                intent.putExtra("userID",userID);
                setPassCategory(category);
                setPassName(tvname.getText().toString());
                int price = -1 , num,soluong =-1;

                try {
                    price = Integer.parseInt(tvgia.getText().toString());
                    soluong = Integer.parseInt(tvsoluong.getText().toString());
                } catch(NumberFormatException nfe) {
                    System.out.println("Could not parse " + nfe);
                }
                setPassPrice(price);
                setPassquantity(1);
                setPassSoluong(soluong);
//                mCallback.getPasscolor();
//                mCallback.setPasssize();
////
                DetailProductActivity.this.startActivity(intent);
            }
        });

        tvname = findViewById(R.id.detail_product_name);
        tvdiscount = findViewById(R.id.detail_product_discount);
        tvsoluong = findViewById(R.id.detail_product_soluong);
        tvgiacu = findViewById(R.id.detail_product_giagoc);
        tvgia = findViewById(R.id.detail_product_gia);
        tvMota = findViewById(R.id.detail_mota);
        listColor = new ArrayList<>();
        listSize = new ArrayList<>();
        TrungGian = new ArrayList<>();

        spinColor = findViewById(R.id.detail_Color_choose);
        spinSize = findViewById(R.id.detail_Size_choose);
        spinnerColor = new SpinnerColorAdapter(DetailProductActivity.this,android.R.layout.simple_spinner_item,listColor);
        spinnerColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinColor.setAdapter(spinnerColor);
        spinColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(DetailProductActivity.this, spinnerColor.getItem(i).getName(), Toast.LENGTH_SHORT).show();
                ColorVal = spinnerColor.getItem(i);
                Log.e("gia tri Spinner mau :",String.valueOf(ColorVal));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter(DetailProductActivity.this,
                android.R.layout.simple_spinner_item, listSize);
//        simple_spinner_item
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSize.setAdapter(adapter);
        spinSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DetailProductActivity.this, adapter.getItem(position), Toast.LENGTH_SHORT).show();
                 SizeVal = spinSize.getSelectedItem().toString();
                Log.e("gia tri Spinner size :",String.valueOf(SizeVal));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        if (extras != null) {
            Namevalue = extras.getString("NAME");
            //The key argument here must match that used in the other activity
        }


        db.collection("SAN_PHAM")
                .whereEqualTo("NAME", Namevalue)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                IDsp = document.getId();
                                tvname.setText(document.getString("NAME"));
                                category = document.getString("CATEGORY");
                                Glide
                                        .with(DetailProductActivity.this)
                                        .load(document.getString("IMAGE"))
                                        .into(main);
                                tvdiscount.setText(String.valueOf(toIntExact(document.getLong("DISCOUNT"))));
                                Log.e("Soluong :",String.valueOf(toIntExact(document.getLong("SOLUONG"))));
                                tvsoluong.setText( String.valueOf(toIntExact(document.getLong("SOLUONG"))));
                                tvgiacu.setText(String.valueOf(toIntExact(document.getLong("GIACU"))));
                                tvgia.setText(String.valueOf(toIntExact(document.getLong("GIA"))));

                                Log.e("documment", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.e("documment", "Error getting documents: ", task.getException());
                        }
                    }
                });

        db.collection("CHI_TIET_SAN_PHAM")
                .whereEqualTo("NAME", Namevalue)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                tvMota.setText(document.getString("MOTA"));
                                Glide
                                        .with(DetailProductActivity.this)
                                        .load(document.getString("IMAGE1"))
                                        .into(one);
                                Glide
                                        .with(DetailProductActivity.this)
                                        .load(document.getString("IMAGE2"))
                                        .into(two);
                                Glide
                                        .with(DetailProductActivity.this)
                                        .load(document.getString("IMAGE3"))
                                        .into(three);
                                if(document.get("MAUSAC") != null) {
                                    TrungGian = (List<String>) document.get("MAUSAC");
                                    for (int i=0;i<TrungGian.size();i++) { // < instead of <=, don't hardcode the length
                                        if (TrungGian.get(i) != null)
                                        {
                                            listColor.add(new String(TrungGian.get(i)));
                                        }// once we insert a, stop looping
                                        }
                                    spinnerColor.notifyDataSetChanged();
                                    Log.e("ListMauSac :",String.valueOf(listColor));
                                }
                                if(document.get("SIZE") != null) {
                                    TrungGian = (List<String>) document.get("SIZE");

                                    for (int i=0;i<TrungGian.size();i++) { // < instead of <=, don't hardcode the length
                                        if (TrungGian.get(i) != null)
                                        {
                                            listSize.add(new String(TrungGian.get(i)));
                                        }// once we insert a, stop looping
                                    }
                                    adapter.notifyDataSetChanged();

                                    Log.e("Listsize :",String.valueOf(listSize));
                                }

                                Log.e("documment", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.e("documment", "Error getting documents: ", task.getException());
                        }
                    }
                });


        main = (ImageView) findViewById(R.id.head_image_product);
        one = (ImageView) findViewById(R.id.one_image_product);
        two = (ImageView) findViewById(R.id.two_image_product);
        three = (ImageView) findViewById(R.id.three_image_product);


//        Glide
//                .with(this)
//                .load("https://cf.shopee.vn/file/1657b14b218fd5962fc3508d367379fc")
//                .into(one);
//        Glide
//                .with(this)
//                .load("https://cf.shopee.vn/file/e86689e29f6f3d7131d0a0948ef254c1")
//                .into(two);
//        Glide
//                .with(this)
//                .load("https://cf.shopee.vn/file/afe17b6984db098f4e39e2f2c66a0d65")
//                .into(three);


        ConstraintLayout ml = findViewById(R.id.detail_Parrent_Constraint);
        ml.invalidate();

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Quần Áo");
//        getSupportActionBar().setDisplayShowTitleEnabled(false);


        NestedScrollView scrollView = findViewById(R.id.myScrollView);
        scrollView.invalidate();
        scrollView.requestLayout();

//        mTabLayout =findViewById(R.id.TopTabmain);
//        viewMain = findViewById(R.id.ViewPagerMain);
//        bottomnavigation = findViewById(R.id.BotomNavMain);
//
//        MainViewPagerAdpater view_pager_adpater = new MainViewPagerAdpater(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//
//        viewMain.setAdapter(view_pager_adpater);
////        mTabLayout.setupWithViewPager(viewMain);
//
//
//
//        viewMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                switch (position) {
//                    case 0: bottomnavigation.getMenu().findItem(R.id.Menu_Shop).setChecked(true); break;
//                    case 1: bottomnavigation.getMenu().findItem(R.id.Menu_Basket).setChecked(true); break;
//                    case 2: bottomnavigation.getMenu().findItem(R.id.Menu_Profile).setChecked(true); break;
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//        bottomnavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.Menu_Shop: viewMain.setCurrentItem(0); break;
//                    case R.id.Menu_Basket: viewMain.setCurrentItem(1); break;
//                    case R.id.Menu_Profile: viewMain.setCurrentItem(2); break;
//                }
//                return true;
//            }
//        });
//
//    }
//    //        String[] Fragement =  {"QuanaoFragment","DientuFragment","VanphongFragment","SachFragment" };
////        String[] Title =  {App.getAppResources().getString(R.string.clothes),App.getAppResources().getString(R.string.Electron),App.getAppResources().getString(R.string.Office),App.getAppResources().getString(R.string.books) };
////        List<String> FragList = Arrays.asList(Fragement);
////        List<String> TiList = Arrays.asList(Title);
//
//
//    @Override
//    public void onBackPressed() {
//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_quanao);
//      if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
//          super.onBackPressed();
//
//      }
//        super.onBackPressed();
//    }
}

    private List<SpinnerColor> getListColor(){
        List<SpinnerColor> listColor = new ArrayList<>();
        listColor.add(new SpinnerColor("Xám","#808080"));
        listColor.add(new SpinnerColor("Vàng kem","#FFFFB2"));
        listColor.add(new SpinnerColor("Đen","#191919"));

        return listColor;
    }

    private List<CharSequence>  getListsize(){
        List<CharSequence> listSize = new ArrayList<>();
        listSize.add(new String("L"));
        listSize.add(new String("XL"));
        listSize.add(new String("XXl"));

        return listSize;
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