package com.example.yanwu.androidexercise.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yanwu.androidexercise.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAdapter extends RecyclerViewArrayAdapter<String, ListAdapter.ItemViewHolder> {
    private int resourceId;
    private Context context;

    public ListAdapter(Context context, int resourceId) {
        this.context = context;
        this.resourceId = resourceId;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(resourceId, viewGroup, false);
        view.setOnClickListener(this);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int position) {
        String s = getItem(position);

        itemViewHolder.text.setText(s);

        itemViewHolder.itemView.setTag(position);
        itemViewHolder.itemView.setSelected(getSelectedPosition() == position);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_title)
        TextView text;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
