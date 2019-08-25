package com.example.shopcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopcenter.R;
import com.example.shopcenter.model.Product;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Product_Approval_Notification_List_Adapter extends ArrayAdapter<Product> implements View.OnClickListener{

    private ArrayList<Product> productDataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView productName;
        TextView productCategory;
        TextView date;
        TextView status;
        Button delete;
    }

    public Product_Approval_Notification_List_Adapter(ArrayList<Product> products, Context context) {
        super(context, R.layout.approval_notification_list, products);
        this.productDataSet = products;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Product dataModel=(Product) object;

        switch (v.getId())
        {
            case R.id.notification_delete:
                remove(dataModel);
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Product dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.approval_notification_list, parent, false);
            viewHolder.productName = convertView.findViewById(R.id.approved_product_name);
            viewHolder.productCategory = convertView.findViewById(R.id.approved_product_category);
            viewHolder.date = convertView.findViewById(R.id.approved_date);
            viewHolder.status = convertView.findViewById(R.id.approval_status);
            viewHolder.delete = convertView.findViewById(R.id.notification_delete);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        lastPosition = position;

        viewHolder.productName.setText("Product Name : "+dataModel.getProductName());
        viewHolder.productCategory.setText("Product Category : "+dataModel.getProductCategory());
        viewHolder.date.setText("Approved Date : "+dataModel.getProductApprovalDate());
        viewHolder.status.setText("Approval Status : "+dataModel.getProductApprovalStatus());
        viewHolder.delete.setOnClickListener(this);
        // Return the completed view to render on screen
        return convertView;
    }
}

