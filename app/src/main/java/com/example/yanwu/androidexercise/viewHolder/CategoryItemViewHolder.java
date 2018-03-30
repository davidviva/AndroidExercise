package com.example.yanwu.androidexercise.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.yanwu.androidexercise.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Data;

@Data
public class CategoryItemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.name)
    TextView name;

    public CategoryItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
