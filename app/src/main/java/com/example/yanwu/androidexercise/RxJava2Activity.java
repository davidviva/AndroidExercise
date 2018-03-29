package com.example.yanwu.androidexercise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RxJava2Activity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava2);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_error_handling)
    public void onErrorHandlingClick() {
        Intent intent1 = new Intent(this, RxErrorHandlingActivity.class);
        startActivity(intent1);
    }

    @OnClick(R.id.btn_compose)
    public void onComposeClick() {
        Intent intent2 = new Intent(this, RxComposeActivity.class);
        startActivity(intent2);
    }

    @OnClick(R.id.btn_workflow)
    public void onWorkflowClick() {
        Intent intent3 = new Intent(this, RxWorkflowActivity.class);
        startActivity(intent3);
    }
}
