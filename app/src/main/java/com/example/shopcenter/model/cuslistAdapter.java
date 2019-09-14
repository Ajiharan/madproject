package com.example.shopcenter.model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.shopcenter.R;

import java.util.ArrayList;
import java.util.List;

public class cuslistAdapter extends ArrayAdapter<User> {
    Activity context;
    List<User> items;

    public cuslistAdapter(@NonNull Activity context, ArrayList<User> dataArrayList) {
        super(context, 0, dataArrayList);
        this.context=context;
        this.items=dataArrayList;
    }

    private class ViewHolder {

        TextView  name;
        ImageView image;


    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        cuslistAdapter.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(
                    R.layout.admin_customer_list, parent, false);

            holder = new cuslistAdapter.ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.customer_name_view);

            holder.image = (ImageView) convertView.findViewById(R.id.customer_profile_views);

            convertView.setTag(holder);

        } else {
            holder = (cuslistAdapter.ViewHolder) convertView.getTag();
        }

        User user = items.get(position);


        holder.name.setText(user.getName());

        Bitmap myLogo = BitmapFactory.decodeResource(context.getResources(), R.drawable.profile);
        if(user.getImage()==null){
            holder.image.setImageBitmap(myLogo);
        }
        else {
            holder.image.setImageBitmap(user.getImage());
        }

        return convertView;

    }


}
