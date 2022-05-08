package com.example.taphoaapp.Search;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taphoaapp.R;

import java.util.ArrayList;
import java.util.List;

public class search_adapter extends RecyclerView.Adapter<search_adapter.ViewHolder> {
    private List<Search_Item> search_items;
    private Context context;
    public search_adapter(List<Search_Item> search_items, Context context) {
        this.search_items = search_items;
        this.context = context;
    }
    public void addlist(List<Search_Item> search_items){
        this.search_items=search_items;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public search_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
        return new search_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Search_Item search_item = search_items.get(position);
        if(search_item == null) return;
        holder.name.setText(search_item.getName());
        holder.category.setText(search_item.getCategory());

    }



    @Override
    public int getItemCount() {
        if(search_items != null){
            return search_items.size();}
        return 0;
    }
    public void clear(){
        search_items.clear();
        notifyDataSetChanged();
    }
    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView name , category;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.tv_search_item);
            category =itemView.findViewById(R.id.tv_search_category);
            linearLayout = itemView.findViewById(R.id.search);
        }
    }
}
