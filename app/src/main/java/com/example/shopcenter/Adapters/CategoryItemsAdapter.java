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
import com.example.shopcenter.model.CategoryItems;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CategoryItemsAdapter extends RecyclerView.Adapter<CategoryItemsAdapter.CategoryItemsViewAdapter> {
    private Context contexts;
    private ArrayList<CategoryItems> myitems;
    private static ClickListener clickListener;
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

    class CategoryItemsViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        ImageView categoryImage;
        TextView categoryImageName;
        FloatingActionButton mybutton;

        public CategoryItemsViewAdapter(@NonNull View itemView) {
            super(itemView);

            categoryImage=itemView.findViewById(R.id.admin_category_delete_product_btn);
            categoryImageName=itemView.findViewById(R.id.admins_edit_products);
            mybutton=itemView.findViewById(R.id.admin_edit_category_details );

            mybutton.setOnClickListener(this);
             categoryImage.setOnLongClickListener(this);


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
        CategoryItemsAdapter.clickListener=clickListener;
    }
    public interface ClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }

}
