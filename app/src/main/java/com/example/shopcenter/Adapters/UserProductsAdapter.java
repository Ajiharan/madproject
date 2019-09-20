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
import com.example.shopcenter.model.Products;

import java.util.ArrayList;

public class UserProductsAdapter extends RecyclerView.Adapter<UserProductsAdapter.UserProductViewAdapter> {
    private Context contexts;
    private ArrayList<Products> myitems;
    private static UserProductsAdapter.ClickListener clickListener;
    public UserProductsAdapter(Context contexts, ArrayList<Products> myitems) {
        this.contexts = contexts;
        this.myitems = myitems;
    }

    @NonNull
    @Override


    public UserProductViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(contexts);
        View view=inflater.inflate(R.layout.user_product_lists,null);

        return  new UserProductsAdapter.UserProductViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserProductViewAdapter holder, int position) {
        Products product=myitems.get(position);
        holder.ProductImage.setImageBitmap(product.getBitmap());
        holder.ProductName.setText(product.getProduct_name());
        holder.ProductDesc.setText("Description\n"+product.getProduct_desc()+"\nPrice:"+product.getProduct_price()+"\nCount:"+product.getCount());


    }

    @Override
    public int getItemCount() {
        return  myitems.size();
    }

    class UserProductViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView ProductImage;
        TextView ProductName;
        TextView ProductDesc;




        public UserProductViewAdapter(@NonNull View itemView) {
            super(itemView);

            ProductImage=itemView.findViewById(R.id.user_view_product_Images);
            ProductName=itemView.findViewById(R.id.user_view_product_names);
            ProductDesc=itemView.findViewById(R.id.user_products_description);
            ProductImage.setOnClickListener(this);

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
        UserProductsAdapter.clickListener=clickListener;
    }
    public interface ClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }

}
