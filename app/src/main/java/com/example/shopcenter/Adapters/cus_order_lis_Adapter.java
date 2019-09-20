package com.example.shopcenter.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.shopcenter.R;
import com.example.shopcenter.model.cus_orders;

import java.util.ArrayList;
import java.util.List;

public class cus_order_lis_Adapter  extends ArrayAdapter<cus_orders> {
    Activity context;
    List<cus_orders> items;

    public cus_order_lis_Adapter(@NonNull Activity context, ArrayList<cus_orders> dataArrayList) {
        super(context, 0, dataArrayList);
        this.context=context;
        this.items=dataArrayList;
    }
    private class ViewHolder {

        TextView id,total,type;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        cus_order_lis_Adapter.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(
                    R.layout.cus_order_views, parent, false);

            holder = new cus_order_lis_Adapter.ViewHolder();
            holder.id = (TextView) convertView.findViewById(R.id.User_list_product_order_id);
            holder.total = (TextView) convertView.findViewById(R.id.User_payment_total);
            holder.type = (TextView) convertView.findViewById(R.id.User_list_product_delivery);


            convertView.setTag(holder);

        } else {
            holder = (cus_order_lis_Adapter.ViewHolder) convertView.getTag();
        }

       cus_orders orders = items.get(position);


       holder.id.setText(orders.getOrder_id());
       holder.total.setText(orders.getTot());
       if((orders.getCheck_order().equals("0"))){
           holder.type.setText("Not delivered");
       }
       else{
           holder.type.setText("Delivered");
       }



        return convertView;

    }
}
