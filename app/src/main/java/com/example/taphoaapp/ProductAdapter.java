package com.example.taphoaapp;

import static android.graphics.Color.rgb;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> implements Filterable {

    private Context mContext;
    private  List<product_item> productItemList;
    private  List<product_item> OriginproductItemList;

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

        Log.e("product.URL", String.valueOf(product.getImageUrl()));
        Log.e("product.soluong", String.valueOf(product.getSoluong()));

        if(product == null) return;

            Glide
                    .with(mContext)
                    .load(product.getImageUrl())
                    .into(holder.mImageView);

            holder.name.setText(product.getName());
            holder.soluong.setText(String.valueOf("số lượng : " + product.getSoluong()));
            holder.discount.setText("Khuyến mãi : " +product.getDiscount());
            holder.price.setText(String.valueOf("Giá : " +currencyFormatter.format(product.getPrice() ) ));
            holder.giagoc.setText(String.valueOf("Giá gốc : " +currencyFormatter.format(product.getGiaGoc()) ));

        holder.discount.setTextColor(rgb(238, 77, 45));
        holder.price.setTextColor(mContext.getColor(R.color.negative_red));

        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
