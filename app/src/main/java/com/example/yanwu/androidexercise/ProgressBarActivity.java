package com.example.yanwu.androidexercise;

import android.os.Bundle;

import butterknife.ButterKnife;

public class ProgressBarActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);
        ButterKnife.bind(this);
    }
}
