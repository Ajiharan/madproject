package com.example.shopcenter.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.shopcenter.R;
import com.example.shopcenter.model.sellers;

import java.util.ArrayList;
import java.util.List;


public class sellerAdapter extends ArrayAdapter<sellers> {
        Activity context;
        List<sellers> items;

public sellerAdapter(@NonNull Activity context, ArrayList<sellers> dataArrayList) {
        super(context, 0, dataArrayList);
        this.context=context;
        this.items=dataArrayList;
}

private class ViewHolder {

    TextView name;
    ImageView image;


}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        sellerAdapter.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(
                    R.layout.seller_all_lists, parent, false);

            holder = new sellerAdapter.ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.Seller_name_view);

            holder.image = (ImageView) convertView.findViewById(R.id.Seller_profile_views);

            convertView.setTag(holder);

        } else {
            holder = (sellerAdapter.ViewHolder) convertView.getTag();
        }

        sellers user1 = items.get(position);


        holder.name.setText(user1.getName());



        holder.image.setImageBitmap(user1.getBitmap());


        return convertView;

    }


}

