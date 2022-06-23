package com.example.taphoaapp.Search;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taphoaapp.DetailProduct.DetailProductActivity;
import com.example.taphoaapp.R;
import com.example.taphoaapp.product_item;
import com.example.taphoaapp.profile.DonhangDetailActivity;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class search_adapter extends RecyclerView.Adapter<search_adapter.ViewHolder> implements Filterable {
    private List<Search_Item> search_items;
    private List<Search_Item> Org_search_items;
    private Context context;
    private Collator VNCollator;



    public search_adapter(List<Search_Item> search_items, Context context) {
        this.search_items = search_items;
        this.context = context;
    }
    public void addlist(List<Search_Item> search_items){
        this.search_items=search_items;
        this.Org_search_items = search_items;
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

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailProductActivity.class);
                intent.putExtra("NAME",holder.name.getText().toString());
                intent.putExtra("prevActive", "SearchActivity");
                context.startActivity(intent);
            }
        });

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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch =charSequence.toString();
                if(strSearch.isEmpty()) search_items = Org_search_items;
                else{
                    List<Search_Item> list = new ArrayList<>();
                    for(Search_Item product : Org_search_items){
                        if(product.getName().toLowerCase().contains(strSearch.toLowerCase())|| VNcontains(product.getName().toLowerCase(), strSearch.toLowerCase())){
                            list.add(product);
                        }
                    }
                    search_items = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = search_items;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                search_items = (List<Search_Item>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    private boolean VNcontains(String source, String target) {
        VNCollator = Collator.getInstance(new Locale("vi", "VN")); //Your locale here
        VNCollator.setStrength(Collator.PRIMARY); //desired strength
        if (target.length() > source.length()) {
            return false;
        }

        Collator collator = Collator.getInstance();
        collator.setStrength(Collator.PRIMARY);

        int end = source.length() - target.length() + 1;

        for (int i = 0; i < end; i++) {
            String sourceSubstring = source.substring(i, i + target.length());

            if (collator.compare(sourceSubstring, target) == 0) {
                return true;
            }
        }

        return false;
    }
}
