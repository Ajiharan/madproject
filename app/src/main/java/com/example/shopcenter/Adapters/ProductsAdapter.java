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

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewAdapter> {
    private Context contexts;
    private ArrayList<Products> myitems;
    private static ClickListener clickListener;

    public ProductsAdapter(Context contexts, ArrayList<Products> myitems) {
        this.contexts = contexts;
        this.myitems = myitems;
    }

    @NonNull
    @Override


    public ProductViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(contexts);
        View view=inflater.inflate(R.layout.admin_products_lists,null);

        return  new ProductsAdapter.ProductViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewAdapter holder, int position) {
        Products product=myitems.get(position);
        holder.ProductImage.setImageBitmap(product.getBitmap());
        holder.ProductName.setText(product.getProduct_name());
        holder.productPrice.setText("$"+product.getProduct_price());
        holder.ProductCount.setText(product.getCount());
        holder.availableProductCount.setText(product.getCount());


    }

    @Override
    public int getItemCount() {
        return  myitems.size();
    }

    class ProductViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        ImageView ProductImage;
        TextView ProductName;
        TextView ProductCount;
        TextView productPrice;
        TextView availableProductCount;



        public ProductViewAdapter(@NonNull View itemView) {
            super(itemView);

            ProductImage=itemView.findViewById(R.id.admin_view_product_Images);
            ProductName=itemView.findViewById(R.id.admin_view_product_names);
            productPrice=itemView.findViewById(R.id.admin_current_product_price);
            ProductCount=itemView.findViewById(R.id.admin_current_product_count);
            availableProductCount=itemView.findViewById(R.id.admin_avilable_product_count);
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
        ProductsAdapter.clickListener=clickListener;
    }
    public interface ClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }

}
