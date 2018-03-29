package com.example.yanwu.androidexercise;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

public class SpinnerActivity extends BaseActivity {
    private String oldRegion = "us-west-2";

    @BindView(R.id.spinner)
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        ButterKnife.bind(this);
        initSpinner();
    }

    private void initSpinner() {
        SpinnerAdapter adapter = ArrayAdapter.createFromResource(this, R.array.lambda_region, R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @OnItemSelected(R.id.spinner)
    public void onItemSelected(Spinner spinner, int position) {
        String newRegion = (String)spinner.getItemAtPosition(position);
        if (!newRegion.equals(oldRegion)) {
            oldRegion = newRegion;
            System.out.println("region changed to " + newRegion);
        }
    }
}
