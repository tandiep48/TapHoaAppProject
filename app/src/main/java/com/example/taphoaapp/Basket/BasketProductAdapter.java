package com.example.taphoaapp.Basket;

import static android.graphics.Color.rgb;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taphoaapp.R;
import com.example.taphoaapp.product_item;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class BasketProductAdapter extends RecyclerView.Adapter<BasketProductAdapter.ProductViewHolder>{

    private Context mContext;
    private List<product_item> productItemList;

    public int getTinh() {
        return tinh;
    }

    public void setTinh(int tinh) {
        this.tinh = tinh;
    }

    private int tinh,sum;

    public BasketProductAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private TextView  cate ,name ,type, soluong,  price , tong, tvQuan;
        private ImageView minus , plus ,edit ,delete;


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            cate = itemView.findViewById(R.id.product_type);
            name = itemView.findViewById(R.id.product_name);
            type = itemView.findViewById(R.id.product_variant);
            price = itemView.findViewById(R.id.product_price);
            soluong = itemView.findViewById(R.id.product_Quantity);
            tong = itemView.findViewById(R.id.tvFSum);
            tvQuan = itemView.findViewById(R.id.tvQuantity);
            minus =  itemView.findViewById(R.id.btnSubtract);
            plus =  itemView.findViewById(R.id.btnAdd);
            edit = (ImageView) itemView.findViewById(R.id.basket_product_edit);
            delete = (ImageView) itemView.findViewById(R.id.basket_product_cancel);

        }
    }


    public void setData(List<product_item> product){
        tinh = 0 ;
        sum = 0 ;
        this.productItemList = product;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_product_item,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        product_item product = productItemList.get(position);


        if(product == null) return;


            holder.name.setText(product.getName());
            holder.soluong.setText(String.valueOf(  product.getSoluong()+1));
            holder.tvQuan.setText(String.valueOf(  product.getSoluong()));
            sum = product.getPrice()* product.getSoluong();
            holder.price.setText(String.valueOf(currencyFormatter.format(product.getPrice() ) ));
            holder.tong.setText(String.valueOf(currencyFormatter.format(sum ) ));
        tinh += sum;


        holder.price.setTextColor(mContext.getColor(R.color.negative_red));


    }

    @Override
    public int getItemCount() {
        if(productItemList != null){
            return productItemList.size();}
        return 0;
    }




}
