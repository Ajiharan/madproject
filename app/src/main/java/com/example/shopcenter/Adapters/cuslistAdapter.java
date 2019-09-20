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
import com.example.shopcenter.model.User;

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



        holder.image.setImageBitmap(user.getImage());


        return convertView;

    }


}
