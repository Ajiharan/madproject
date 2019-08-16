package com.example.shopcenter.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopcenter.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryItemsAdapter extends RecyclerView.Adapter<CategoryItemsAdapter.CategoryItemsViewAdapter> {
    private Context contexts;
    private ArrayList<CategoryItems> myitems;

    public CategoryItemsAdapter(Context contexts, ArrayList<CategoryItems> myitems) {
        this.contexts = contexts;
        this.myitems = myitems;
    }

    @NonNull
    @Override
    public CategoryItemsViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(contexts);
        View view=inflater.inflate(R.layout.admin_category_lists,null);

        return  new CategoryItemsViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItemsViewAdapter holder, int position) {
        CategoryItems category=myitems.get(position);
        holder.categoryImage.setImageBitmap(category.getBitmap());
        holder.categoryImageName.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return  myitems.size();
    }

    class CategoryItemsViewAdapter extends RecyclerView.ViewHolder{
        ImageView categoryImage;
        TextView categoryImageName;

        public CategoryItemsViewAdapter(@NonNull View itemView) {
            super(itemView);

            categoryImage=itemView.findViewById(R.id.admin_category_delete_product_btn);
            categoryImageName=itemView.findViewById(R.id.admins_edit_products);
        }
    }

}
