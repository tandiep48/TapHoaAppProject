package com.example.taphoaapp.Basket;

import static android.graphics.Color.rgb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taphoaapp.BasketFragment;
import com.example.taphoaapp.DetailProduct.DetailProductActivity;
import com.example.taphoaapp.R;
import com.example.taphoaapp.basket_product_item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class BasketProductAdapter extends RecyclerView.Adapter<BasketProductAdapter.ProductViewHolder>{

    DataCommunication mCallback;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    BasketFragment mFragment;
    private Context mContext;
    private List<basket_product_item> productItemList;
    Bundle extras ;
    String userID;
    private Intent i;

    public int getTinh() {
        return tinh;
    }

    public void setTinh(int tinh) {
        this.tinh = tinh;
    }

    private int tinh,sum, vitri , tmp;

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

    public BasketProductAdapter(Context mContext,BasketFragment Fragment) {
        this.mContext = mContext;
        this.mFragment=Fragment;
    }

    public BasketProductAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private TextView  cate ,name ,type_mau,type_size, soluong,  price , tong, tvQuan;
        private ImageView minus , plus ,edit ,delete;
        private ImageView btnAdd, btnSubtract;
        public int numtong, vitri;


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
//            cate = itemView.findViewById(R.id.product_type);
            name = itemView.findViewById(R.id.product_name);
            type_mau = itemView.findViewById(R.id.product_variant_color);
            type_size = itemView.findViewById(R.id.product_variant_size);
            price = itemView.findViewById(R.id.product_price);
            soluong = itemView.findViewById(R.id.product_Quantity);
            tong = itemView.findViewById(R.id.tvFSum);
            tvQuan = itemView.findViewById(R.id.tvQuantity);
            minus =  itemView.findViewById(R.id.btnSubtract);
            plus =  itemView.findViewById(R.id.btnAdd);
            edit = (ImageView) itemView.findViewById(R.id.basket_product_edit);
            delete = (ImageView) itemView.findViewById(R.id.basket_product_cancel);
            btnAdd = (ImageView) itemView.findViewById(R.id.btnAdd);
            btnSubtract = itemView.findViewById(R.id.btnSubtract);




        }

        public int getNumtong() {
            return numtong;
        }

        public void setNumtong(int numtong) {
            this.numtong = numtong;
        }
    }


    public void setData(List<basket_product_item> product){
        tinh = 0 ;
        sum = 0 ;
        this.productItemList = product;
//        HashMap<String, Object> map = new HashMap<String, Object>();
//
//        //Getting Collection of values from HashMap
//
//        Collection<Object> values = map.values();
//
//        //Creating an ArrayList of values
//
//        ArrayList<String> listOfValues = new ArrayList<String>(values);

        notifyDataSetChanged();
    }

    public void DeleteAt(int position){

        productItemList.remove(position);
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
        mAuth = FirebaseAuth.getInstance();
        i = ((Activity) mContext).getIntent();
        extras = ((Activity) mContext).getIntent().getExtras();

        if ( i!= null &&extras != null) {
//            userID = i.getStringExtra("userID");
        }
        userID = mAuth.getCurrentUser().getUid();
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        holder.vitri = position;
        Log.e("check",productItemList+"");
        Log.e("checklist",productItemList.get(position).getID()+"");
        basket_product_item product = productItemList.get(position);
//        basket_product_item product = new basket_product_item(productItemList.get(position).getID(),productItemList.get(position).getCategory(),productItemList.get(position).getName(),productItemList.get(position).getMau(),productItemList.get(position).getSize(),productItemList.get(position).getSoluong(),productItemList.get(position).getPrice(),productItemList.get(position).getNumdat()) ;

//        HashMap<String, String> map = new HashMap<String, String>();
//
//        Collection<String> values = map.values();

        //Creating an ArrayList of values
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.productsDelAt(position);
//                db.collection("Gio_hang").document(userID).update("ListProducts", FieldValue.arrayRemove(productItemList.get(position)));
                DeleteAt(position);
                db.collection("Gio_hang").document(userID).update("ListProducts", productItemList);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailProductActivity.class);
                intent.putExtra("NAME",product.getName());
                intent.putExtra("prevActive", "BasketProduct");
                intent.putExtra("Order", "NO");

                mContext.startActivity(intent);
            }
        });


        if(product == null) return;
//            if(product.getName() == mCallback.getPassName()) {
//                holder.name.setText(mCallback.getPassName());
//                holder.soluong.setText(String.valueOf(product.getSoluong()));
//                holder.tvQuan.setText(String.valueOf(mCallback.getPassquantity()));
//                sum = mCallback.getPassPrice() * mCallback.getPassquantity();
//                holder.price.setText(String.valueOf(currencyFormatter.format(mCallback.getPassPrice())));
//                holder.tong.setText(String.valueOf(currencyFormatter.format(sum)));
//                tinh += sum;
//            }
//            else{
//                holder.name.setText(product.getName());
//                holder.name.setTooltipText(product.getName());
//                holder.name.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        holder.name.performLongClick();
//                    }
//                });
//                holder.soluong.setText(String.valueOf(product.getSoluong()));
//                holder.tvQuan.setText(String.valueOf(product.getSoluong()));
//                sum = product.getPrice() * product.getSoluong();
//                holder.price.setText(String.valueOf(currencyFormatter.format(product.getPrice())));
//                holder.setNumtong(sum);
//                holder.tong.setText(String.valueOf(currencyFormatter.format(sum)));
//                tinh += sum;
//            }

        holder.name.setText(product.getName());
        holder.name.setTooltipText(product.getName());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.name.performLongClick();
            }
        });
        holder.soluong.setText(String.valueOf(product.getSoluong()));
        holder.tvQuan.setText(String.valueOf(1));
        product.setNumdat(1);
        if(product.getMau() == null) {
            holder.type_mau.setText("");
        }
        else {
            holder.type_mau.setText("Màu: " + String.valueOf(product.getMau()));
        }
        if(product.getSize() == null) {
            holder.type_size.setText("");
        }
        else {
            holder.type_size.setText("Size: " + String.valueOf(product.getSize()));
        }
        sum = product.getPrice() * product.getNumdat();
        holder.price.setText(String.valueOf(currencyFormatter.format(product.getPrice())));
        holder.setNumtong(sum);
        holder.tong.setText(String.valueOf(currencyFormatter.format(sum)));
        tinh += sum;

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int tmp;
               if( (Integer.parseInt(holder.tvQuan.getText().toString()) + 1) >= Integer.parseInt(holder.soluong.getText().toString())) {tmp = Integer.parseInt(holder.soluong.getText().toString());}
               else {tmp = Integer.parseInt(holder.tvQuan.getText().toString()) + 1;}
                sum = product.getPrice() * tmp;
                holder.setNumtong(sum);
                holder.tong.setText(String.valueOf(currencyFormatter.format(sum)));
//                holder.numtong = product.getPrice();
//                for (int i = 0; i < productItemList.size(); i++){
//                    product_item productTMP = productItemList.get(i);
//                    int sumTMP = productTMP.getPrice() * productTMP.getSoluong();
//                    tinh += sumTMP;
//                }
                mFragment.SetTotalPrice();

               holder.tvQuan.setText(String.valueOf(tmp));
//                notifyItemChanged(position);
                product.setNumdat(tmp);
            }
        });
            holder.btnSubtract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tnp;
                    if(Integer.parseInt(holder.tvQuan.getText().toString()) >= 2) {
                        tnp = Integer.parseInt(holder.tvQuan.getText().toString()) - 1;
                    }
                    else {tnp = 1;}
                    sum = product.getPrice() * tnp;
                    holder.setNumtong(sum);
                    holder.tong.setText(String.valueOf(currencyFormatter.format(sum)));
////                    tinh = 0;
//                    for (int i = 0; i < productItemList.size(); i++){
//                        product_item productTNP = productItemList.get(i);
//
//                        int sumTNP = productTNP.getPrice() * productTNP.getSoluong();
//                        tinh += sumTNP;
//                    }
                    mFragment.SetTotalPrice();
                    holder.tvQuan.setText(String.valueOf(tnp));
//                    notifyItemChanged(position);
                    if(Integer.parseInt(holder.soluong.getText().toString()) <= 0 ) product.setNumdat(0);
                    else product.setNumdat(tnp);
                }
            });


        holder.price.setTextColor(mContext.getColor(R.color.negative_red));


    }

    @Override
    public int getItemCount() {
        if(productItemList != null){
            return productItemList.size();}
        return 0;
    }




}
