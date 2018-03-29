package com.example.yanwu.androidexercise;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.yanwu.androidexercise.adapter.ListAdapter;
import com.example.yanwu.androidexercise.adapter.OnRecyclerViewItemClickedListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecyclerviewActivity extends AppCompatActivity {

    ListAdapter listAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        ButterKnife.bind(this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        listAdapter = new ListAdapter(this, R.layout.layout_list_item);
        listAdapter.setOnItemClickedListener(new OnRecyclerViewItemClickedListener() {
            @Override
            public void onItemClicked(View v, RecyclerView.Adapter<?> adapter, int position) {
                System.out.println("111111111111");
            }
        });
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);

        initList();
    }

    private void initList() {
        for (int i = 0; i < 50; i++) {
            listAdapter.add(String.valueOf(i));
        }
        listAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btn_test)
    public void onTestClick() {
        System.out.println("test clicked");
    }

}
