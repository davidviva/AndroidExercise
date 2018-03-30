package com.example.yanwu.androidexercise.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yanwu.androidexercise.viewHolder.CategoryItemViewHolder;

public class CategoryAdapter extends RecyclerViewArrayAdapter<String, CategoryItemViewHolder>{
    private int resourceId;
    private Context context;

    public CategoryAdapter(Context context, int resourceId) {
        this.context = context;
        this.resourceId = resourceId;
    }

    @Override
    public CategoryItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(resourceId, viewGroup, false);
        view.setOnClickListener(this);
        return new CategoryItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryItemViewHolder viewHolder, int position) {
        String s = getItem(position);
        viewHolder.getName().setText(s);

        viewHolder.itemView.setTag(position);
        viewHolder.itemView.setSelected(getSelectedPosition() == position);
    }
}
