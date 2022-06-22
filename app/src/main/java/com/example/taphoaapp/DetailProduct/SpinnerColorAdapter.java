package com.example.taphoaapp.DetailProduct;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.taphoaapp.R;

import java.util.List;

public class SpinnerColorAdapter extends ArrayAdapter<String> {
//    @NonNull List<SpinnerColor> objects
    Context mContext;
    List<String> daynay;
    public SpinnerColorAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        mContext = context;
        daynay = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner_selected,parent,false);
        TextView tvColor  = convertView.findViewById(R.id.tv_spinnerselect_color);
//        ImageView iColor = convertView.findViewById(R.id.img_spinnerselect_color);

//        SpinnerColor
              String spinnerColor = daynay.get(position).toString() ;
              String bgspinnerColor = spinnerColor.toUpperCase().replace(" ","_");
        if(spinnerColor != null) {
            tvColor.setText(spinnerColor);
            Resources res = mContext.getResources();
            String packageName = mContext.getPackageName();

            int colorId = res.getIdentifier(bgspinnerColor, "color", packageName);
            int desiredColor = res.getColor(colorId);

            convertView.setBackgroundColor(desiredColor);
//            convertView.setBackgroundColor(Color.parseColor());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner_color,parent,false);
        TextView tvColor  = convertView.findViewById(R.id.tv_spinner_color);
        View vColor = convertView.findViewById(R.id.view_spinner_color);

        String spinnerColor = daynay.get(position);
        String bgspinnerColor = spinnerColor.toUpperCase().replace(" ","_").toString();
        if(spinnerColor != null) {
            tvColor.setText(spinnerColor);
            Resources res = mContext.getResources();
            String packageName = mContext.getPackageName();

            int colorId = res.getIdentifier(bgspinnerColor, "color", packageName);
            int desiredColor = res.getColor(colorId);

            convertView.setBackgroundColor(desiredColor);
            if(desiredColor == res.getColor(R.color.ĐEN)) tvColor.setTextColor(res.getColor(R.color.TRẮNG));

//        SpinnerColor spinnerColor = this.getItem(position);
//        if(spinnerColor != null) {
//            tvColor.setText(spinnerColor.getName());
//            vColor.setBackgroundColor(Color.parseColor(spinnerColor.getColor()));
//        }
        }
        return convertView;
    }
}
