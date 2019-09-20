package com.example.shopcenter.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopcenter.R;
import com.example.shopcenter.model.Cart;

import java.util.ArrayList;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.CartViewAdapter> {

    private Context contexts;
    private ArrayList<Cart> myitems;
    private static cartAdapter.ClickListener clickListener;

    public cartAdapter(Context contexts, ArrayList<Cart> myitems) {
        this.contexts = contexts;
        this.myitems = myitems;
    }


    @NonNull
    @Override
    public CartViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(contexts);
        View view=inflater.inflate(R.layout.user_cart_views,null);

        return  new cartAdapter.CartViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewAdapter holder, int position) {
        Cart cart=myitems.get(position);
        holder.ProductImage.setImageBitmap(cart.getBitmap());
        holder.ProductName.setText(cart.getName());
        holder.productPrice.setText("$"+cart.getPrice());
        holder.ProductCount.setText(cart.getCount());

    }

    @Override
    public int getItemCount() {
        return myitems.size();
    }

    class CartViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView ProductImage, user_delete_button;
        TextView ProductName;
        TextView ProductCount;
        TextView productPrice;


        public CartViewAdapter(@NonNull View itemView) {
            super(itemView);

            ProductImage = itemView.findViewById(R.id.cart_product_image);
            ProductName = itemView.findViewById(R.id.User_list_product_name);
            productPrice = itemView.findViewById(R.id.User_product_price);
            ProductCount = itemView.findViewById(R.id.User_list_product_quantity);
            user_delete_button = itemView.findViewById(R.id.user_delete_cart);
            user_delete_button.setOnClickListener(this);

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
        cartAdapter.clickListener = clickListener;
    }

        public interface ClickListener {
            void onItemClick(int position, View v);

            void onItemLongClick(int position, View v);
        }
    }



