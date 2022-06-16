package com.example.taphoaapp.profile;

import static android.graphics.Color.rgb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taphoaapp.Basket.DataCommunication;
import com.example.taphoaapp.BasketFragment;
import com.example.taphoaapp.DetailProduct.DetailProductActivity;
import com.example.taphoaapp.R;
import com.example.taphoaapp.product_item;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.ProductViewHolder> implements Filterable {

    DataCommunication mCallback;
    private Context mContext;
    private  List<DonHang_item> DonHangItemList;
    private  List<DonHang_item> OriginDonHangItemList;
    String userID;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
//        try {
//            mCallback = (DataCommunication) mContext;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(mContext.toString()
//                    + " must implement DataCommunication");
//        }
    }

    public DonHangAdapter(List<DonHang_item> DonHangItemList) {
        this.DonHangItemList = DonHangItemList;
        OriginDonHangItemList = DonHangItemList;
    }

    public DonHangAdapter(Context mContext) {
        this.mContext = mContext;
    }



    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private TextView  MaDH , soluong, Date , pay ,status;
        private LinearLayout pressLayout;


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            MaDH = itemView.findViewById(R.id.tvIdDonHang_value);
            Date = itemView.findViewById(R.id.tvOrderDate_value);
            pay = itemView.findViewById(R.id.tvOrderPrice_value);
            status = itemView.findViewById(R.id.tvStatus_value);
            soluong = itemView.findViewById(R.id.tvItemCount_value);
            pressLayout = itemView.findViewById(R.id.DonHang_press_wrap);


        }
    }


    public void setData(List<DonHang_item> DonHang){
        this.DonHangItemList = DonHang;
        OriginDonHangItemList = DonHang;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_layout_items_completed,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        DonHang_item DonHang = DonHangItemList.get(position);

//        Log.e("product.NAME", String.valueOf(product.getName()));
//        Log.e("product.URL", String.valueOf(product.getImageUrl()));
//        Log.e("product.soluong", String.valueOf(product.getSoluong()));
        Intent i = ((Activity) mContext).getIntent();
        Bundle extras = ((Activity) mContext).getIntent().getExtras();

        if ( i!= null &&extras != null) {
            userID = i.getStringExtra("userID");
        }

        if(DonHang == null) return;
        holder.pressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DonhangDetailActivity.class);
//                intent.putExtra("NAME",product.getName());
                intent.putExtra("prevActive", "MainActivity");
                intent.putExtra("userID", userID);
//                intent.putExtra("Category",product.getCategory());
//                intent.putExtra("Soluong",1);
//                intent.putExtra("gia",product.getPrice());
//                intent.putExtra("color","");
                mContext.startActivity(intent);
            }
        });



            holder.MaDH.setText(DonHang.getMaDH());
            holder.soluong.setText(String.valueOf(DonHang.getSoluong()));
            holder.Date.setText(String.valueOf(DonHang.getTime()));
            holder.pay.setText(String.valueOf(currencyFormatter.format(DonHang.getPay() ) ));
            holder.status.setText(String.valueOf(DonHang.getStatus() ));


    }

    @Override
    public int getItemCount() {
        if(DonHangItemList != null) return DonHangItemList.size();
        return 0;
    }

    public void clear(){
        DonHangItemList.clear();
        notifyDataSetChanged();
    }

    @Override
    public  Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch =charSequence.toString();
                if(strSearch.isEmpty()) DonHangItemList = OriginDonHangItemList;
                else{
                    List<DonHang_item> list = new ArrayList<>();
                    for(DonHang_item DonHang : OriginDonHangItemList){
//                        if(DonHang.getName().toLowerCase().contains(strSearch.toLowerCase())){
//                            list.add(DonHang);
//                        }
                    }
                    DonHangItemList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = DonHangItemList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                DonHangItemList = (List<DonHang_item>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
