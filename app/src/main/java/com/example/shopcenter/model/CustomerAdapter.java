package com.example.shopcenter.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopcenter.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewAdapter> {
    private Context contexts;
    private ArrayList<User> myitems;
    private static ClickListener clickListener;

    public CustomerAdapter(Context contexts, ArrayList<User> myitems) {
        this.contexts = contexts;
        this.myitems = myitems;
    }

    @NonNull
    @Override
    public CustomerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(contexts);
        View view=inflater.inflate(R.layout.admin_customer_list,null);

        return  new CustomerViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewAdapter holder, int position) {
        User user=myitems.get(position);
        holder.imageView.setImageBitmap(user.getImage());
        holder.cus_name.setText(user.getName());



    }

    @Override
    public int getItemCount() {
        return myitems.size();
    }

    class CustomerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private CircleImageView imageView;
        private TextView cus_name,cus_email,cus_pass;

        public CustomerViewAdapter(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.customer_profile_views);
            cus_name=itemView.findViewById(R.id.customer_name_view);

            imageView.setOnClickListener(this);
            imageView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onItemLongClick(getAdapterPosition(), view);
            return false;

        }
    }
    public void setOnItemClickListener(ClickListener clickListener) {
        CustomerAdapter.clickListener=clickListener;
    }
    public interface ClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }
}
