package com.example.taphoaapp.profile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.taphoaapp.Basket.DataCommunication;
import com.example.taphoaapp.DetailProduct.DetailProductActivity;
import com.example.taphoaapp.IOnBackPressed;
import com.example.taphoaapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;


import java.util.ArrayList;
import java.util.Locale;

public class StoreInfo extends AppCompatActivity  {


    private TabLayout mTabLayout;
    private ViewPager viewMain;
    private BottomNavigationView bottomnavigation;
    private String passName,passCategory,passcolor,passsize  , order,active ,PrevActive, ActiPrev,userID;
    private int passPrice,passquantity,passSoluong;
    Bundle extras;
    Context mContext;
    DataCommunication mCallback;
    ImageView mImageView,mImageView2;
    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map = null;
    ScaleBarOverlay mScaleBarOverlay;
    RotationGestureOverlay mRotationGestureOverlay;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        Context ctx = getApplicationContext();
//        Configuration.getInstance
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));


        setContentView(R.layout.store_infor);


//        mCallback = (DataCommunication) StoreInfo.this;
//        if(PrevActive!= null){
//        Log.e("PrevActive : " , PrevActive.toString());
//        }
//        extras = getIntent().getExtras();
//
//        if (extras != null) {
//            order = extras.getString("Order");
//            active = extras.getString("prevActive");
//            // and get whatever type user account id is
//        }

        mImageView = findViewById(R.id.head_image_product);
        mImageView2 = findViewById(R.id.head_image_product2);
        Glide
                .with(this)
                .load("https://blog.dktcdn.net/files/kinh-nghiem-mo-shop-thoi-trang-1.jpg")
                .into(mImageView);

        Glide
                .with(this)
                .load("https://cdn.brvn.vn/news/480px/2015/7895_BachhoaXanh.jpg")
                .into(mImageView2);
        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);



        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        map.canZoomIn();
        map.canZoomOut();
        map.isFlingEnabled();
        map.setFlingEnabled(true);


        mRotationGestureOverlay = new RotationGestureOverlay(this, map);
        mRotationGestureOverlay.setEnabled(true);
        map.setMultiTouchControls(true);
        map.getOverlays().add(this.mRotationGestureOverlay);

        final Context context = this;
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mScaleBarOverlay = new ScaleBarOverlay(map);
        mScaleBarOverlay.setCentred(true);
//play around with these values to get the location on screen in the right place for your application
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        map.getOverlays().add(this.mScaleBarOverlay);


        IMapController mapController = map.getController();
        mapController.setZoom(19.5);
        GeoPoint startPoint = new GeoPoint(10.776013945661287, 106.66737569733458);
        mapController.setCenter(startPoint);

        Intent i = getIntent();
        Bundle extras = getIntent().getExtras();

        if ( i!= null &&extras != null) {
            order = extras.getString("Order");

            userID = i.getStringExtra("userID");

            ActiPrev = i.getStringExtra("PrevActive");
            if(ActiPrev !=null) {
                Log.e("PrevActive : ", ActiPrev.toString());
            }

            // and get whatever type user account id is
        }

//        if(savedInstanceState == null  ) {
//            Fragment newFragment = new BasketFragment();
//            this.getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.Main_root, new BasketFragment())
//                    .commit();
//        }

//        Locale locales = new Locale("vi");
//        Locale.setDefault(locales);
//        Configuration config = new Configuration();
//        config.locale = locales;
//        this.getApplicationContext().getResources().updateConfiguration(config, null);

//        mTabLayout =findViewById(R.id.TopTabmain);

        if(ActiPrev !=null) {
            if (i != null && extras != null && ActiPrev.toString().equalsIgnoreCase("DetailProduct")) {
                viewMain.setCurrentItem(1);
            }
        }


    }
    @Override
    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }


    @Override
    public void onPause() {
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_quanao);
      if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
          super.onBackPressed();

      }
        super.onBackPressed();
    }





}