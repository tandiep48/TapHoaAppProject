package com.example.taphoaapp;

import static android.graphics.Color.rgb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taphoaapp.Basket.DataCommunication;
import com.example.taphoaapp.DetailProduct.DetailProductActivity;
import com.example.taphoaapp.Search.SearchActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> implements Filterable {

    DataCommunication mCallback;
    private Context mContext;
    private  List<product_item> productItemList;
    private  List<product_item> OriginproductItemList;
    String userID;
    String Password;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        try {
            mCallback = (DataCommunication) mContext;
        } catch (ClassCastException e) {
            throw new ClassCastException(mContext.toString()
                    + " must implement DataCommunication");
        }
    }

    public ProductAdapter(List<product_item> productItemList) {
        this.productItemList = productItemList;
        OriginproductItemList = productItemList;
    }

    public ProductAdapter(Context mContext) {
        this.mContext = mContext;
    }



    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageView;
        private TextView  name , soluong, discount , price ,giagoc;
        private Button buy;
        private ImageView favorite;
        private LinearLayout pressLayout,LinearBound;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.image_product);
            name = itemView.findViewById(R.id.product_name);
            discount = itemView.findViewById(R.id.product_discount);
            price = itemView.findViewById(R.id.product_gia);
            giagoc = itemView.findViewById(R.id.product_giagoc);
            soluong = itemView.findViewById(R.id.product_soluong);
            buy = (Button) itemView.findViewById(R.id.MuaNgay);
            favorite = (ImageView) itemView.findViewById(R.id.product_favorite);
            pressLayout = itemView.findViewById(R.id.wrap_press_product);
            LinearBound = itemView.findViewById(R.id.Bound_item_product);
        }
    }


    public void setData(List<product_item> product){
        this.productItemList = product;
        OriginproductItemList = product;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        product_item product = productItemList.get(position);

//        Log.e("product.NAME", String.valueOf(product.getName()));
        Log.e("product.URL", String.valueOf(product.getImageUrl()));
        Log.e("product.soluong", String.valueOf(product.getSoluong()));
        Intent i = ((Activity) mContext).getIntent();
        Bundle extras = ((Activity) mContext).getIntent().getExtras();

        if ( i!= null &&extras != null) {
            userID = i.getStringExtra("userID");
            Password = i.getStringExtra("password");
        }

        if(product == null) return;
        holder.pressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailProductActivity.class);
                intent.putExtra("NAME",product.getName());
                intent.putExtra("prevActive", "MainActivity");
                intent.putExtra("userID", userID);
                intent.putExtra("password", Password);
//                intent.putExtra("Category",product.getCategory());
//                intent.putExtra("Soluong",1);
//                intent.putExtra("gia",product.getPrice());
//                intent.putExtra("color","");
                int requestCode = 1; // Or some number you choose
                ((Activity) mContext).startActivityForResult(intent, requestCode);
//                mContext.startActivity(intent);
            }
        });
            Glide
                    .with(mContext)
                    .load(product.getImageUrl())
                    .into(holder.mImageView);

            holder.name.setText(product.getName());
            if(product.getSoluong() <= 0)
            {
                holder.soluong.setText(String.valueOf("Hết hàng"));
                holder.soluong.setTextColor(mContext.getResources().getColor(R.color.error_red));
                holder.soluong.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
                holder.LinearBound.setAlpha((float) 0.4);
//                holder.LinearBound.setBackgroundColor(mContext.getResources().getColor(R.color.LightGrey));
//                holder.LinearBound.setBackgroundColor(Color.alpha(R.color.TransParent));
////                holder.LinearBound.setBackgroundResource();
            }
            else {
                holder.soluong.setText(String.valueOf("số lượng : " + product.getSoluong()));
            }
            holder.discount.setText("Khuyến mãi : " +product.getDiscount()+"%");
            holder.price.setText(String.valueOf("Giá : " +currencyFormatter.format(product.getPrice() ) ));
            holder.giagoc.setText(String.valueOf("Giá gốc : " +currencyFormatter.format(product.getGiaGoc()) ));
            holder.giagoc.setPaintFlags(holder.giagoc.getPaintFlags() |Paint.STRIKE_THRU_TEXT_FLAG);

        holder.discount.setTextColor(rgb(238, 77, 45));
        holder.price.setTextColor(mContext.getColor(R.color.negative_red));

        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, DetailProductActivity.class);
                intent.putExtra("NAME",product.getName());
                intent.putExtra("prevActive", "MainActivity");
                intent.putExtra("userID", userID);
                intent.putExtra("password", Password);
//                intent.putExtra("Category",product.getCategory());
//                intent.putExtra("Soluong",1);
//                intent.putExtra("gia",product.getPrice());
//                intent.putExtra("color","");
                int requestCode = 1; // Or some number you choose
                ((Activity) mContext).startActivityForResult(intent, requestCode);
//                mContext.startActivity(intent);
//                mCallback.setPassCategory(product.getCategory());
//                mCallback.setPassName(product.getName());
//                mCallback.setPassPrice(product.getPrice());
//                mCallback.setPassquantity(1);
//                mCallback.setPassSoluong(product.getSoluong());
//
//                Fragment newFragment = new BasketFragment();
//                ((FragmentActivity)mContext).getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fragment_home,new BasketFragment())
//                        .commit();
//                FragmentTransaction transaction = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
//
//// Replace whatever is in the fragment_container view with this fragment,
//// and add the transaction to the back stack if needed
//                transaction.replace(R.id.ViewPagerHome, newFragment);
////                transaction.addToBackStack(null);
//
//// Commit the transaction
//                transaction.commit();
            }
        });

        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public int getItemCount() {
        if(productItemList != null) return productItemList.size();
        return 0;
    }

    public void clear(){
        productItemList.clear();
        notifyDataSetChanged();
    }

    @Override
    public  Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch =charSequence.toString();
                if(strSearch.isEmpty()) productItemList = OriginproductItemList;
                else{
                    List<product_item> list = new ArrayList<>();
                    for(product_item product : OriginproductItemList){
                        if(product.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(product);
                        }
                    }
                    productItemList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = productItemList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                productItemList = (List<product_item>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


}
