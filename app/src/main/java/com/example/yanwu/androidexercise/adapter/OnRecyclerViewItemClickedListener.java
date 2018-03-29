package com.example.yanwu.androidexercise.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public interface OnRecyclerViewItemClickedListener {

    void onItemClicked(View v, RecyclerView.Adapter<?> adapter, int position);
}
