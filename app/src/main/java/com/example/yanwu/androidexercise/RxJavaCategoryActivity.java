package com.example.yanwu.androidexercise;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.yanwu.androidexercise.adapter.CategoryAdapter;
import com.example.yanwu.androidexercise.adapter.OnRecyclerViewItemClickedListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RxJavaCategoryActivity extends BaseActivity {

    private CategoryAdapter categoryAdapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_category);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        showBackButton();

        initCategory();
    }

    private void initCategory() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        categoryAdapter = new CategoryAdapter(this, R.layout.layout_category_item);
        categoryAdapter.setOnItemClickedListener(new OnRecyclerViewItemClickedListener() {
            @Override
            public void onItemClicked(View v, RecyclerView.Adapter<?> adapter, int position) {

            }
        });
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(categoryAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
