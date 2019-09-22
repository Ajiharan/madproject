package com.example.shopcenter.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.shopcenter.R;
import com.example.shopcenter.model.payments;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends ArrayAdapter<payments> {
    Activity context;
    List<payments> items;

    public NotificationAdapter(@NonNull Activity context, ArrayList<payments> dataArrayList) {
        super(context, 0, dataArrayList);
        this.context=context;
        this.items=dataArrayList;
    }

    private class ViewHolder {

        TextView name;
        TextView email_id;
        TextView zip_code;
        TextView cardNo;
        TextView Address;
        TextView amount;



    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NotificationAdapter.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(
                    R.layout.current_admin_notification, parent, false);

            holder = new NotificationAdapter.ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.admin_cus_notification_name);
            holder.email_id=convertView.findViewById(R.id.admin_cus_notification_mailid);
            holder.zip_code=convertView.findViewById(R.id.admin_cus_notification_zip);
            holder.cardNo=convertView.findViewById(R.id.admin_cus_notification_card);
            holder.Address=convertView.findViewById(R.id.admin_cus_notification_city);
            holder.amount=convertView.findViewById(R.id.admin_cus_notification_amount);
            convertView.setTag(holder);


        } else {
            holder = (NotificationAdapter.ViewHolder) convertView.getTag();
        }

        payments mypay = items.get(position);
        holder.name.setText(mypay.getName());
        holder.Address.setText(mypay.getCity());
        holder.cardNo.setText(mypay.getCardNo());
        holder.zip_code.setText(mypay.getZipcode());
        holder.email_id.setText(mypay.getEmail_id());
        holder.amount.setText(mypay.getTotal());
        return convertView;

    }


}

