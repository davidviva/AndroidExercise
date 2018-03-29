package com.example.yanwu.androidexercise;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MPChartActivity extends BaseActivity {

    @BindView(R.id.barchart)
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpchart);
        ButterKnife.bind(this);

        initBarChart();
    }

    private void initBarChart() {
        // Single group of barchart
//        // Create a dataset
//        ArrayList<BarEntry> entries = new ArrayList<>();
//        entries.add(new BarEntry(4f, 0));
//        entries.add(new BarEntry(8f, 1));
//        entries.add(new BarEntry(6f, 2));
//        entries.add(new BarEntry(12f, 3));
//        entries.add(new BarEntry(8f, 4));
//        entries.add(new BarEntry(9f, 5));
//
//        BarDataSet dataset = new BarDataSet(entries, "number of calls");
//
//        // defining x-axis labels
//        ArrayList<String> labels = new ArrayList<>();
//        labels.add("January");
//        labels.add("February");
//        labels.add("March");
//        labels.add("April");
//        labels.add("May");
//        labels.add("June");
//
//        // set the data
//        BarData data = new BarData(labels, dataset);
//
//        barChart.setDescription("Description");
//
//        // set colors
//        ArrayList<Integer> colors = new ArrayList<>();
//        colors.add(Color.BLACK);
//        colors.add(Color.RED);
//        colors.add(Color.GREEN);
//        colors.add(Color.BLUE);
//        colors.add(Color.YELLOW);
//        colors.add(Color.GRAY);
//
//        dataset.setColors(colors);
//
//        barChart.animateY(5000);
//
//        barChart.setData(data);
//        barChart.notifyDataSetChanged();
//        barChart.invalidate();


        // Two groups

        // Create a dataset
        ArrayList<BarEntry> group1 = new ArrayList<>();
        group1.add(new BarEntry(4f, 0));
        group1.add(new BarEntry(8f, 1));
        group1.add(new BarEntry(6f, 2));
        group1.add(new BarEntry(12f, 3));
        group1.add(new BarEntry(8f, 4));
        group1.add(new BarEntry(9f, 5));

        ArrayList<BarEntry> group2 = new ArrayList<>();
        group2.add(new BarEntry(6f, 0));
        group2.add(new BarEntry(7f, 1));
        group2.add(new BarEntry(8f, 2));
        group2.add(new BarEntry(12f, 3));
        group2.add(new BarEntry(15f, 4));
        group2.add(new BarEntry(10f, 5));

        BarDataSet dataset1 = new BarDataSet(group1, "brand1");
        BarDataSet dataset2 = new BarDataSet(group2, "brand2");
        dataset1.setColors(ColorTemplate.COLORFUL_COLORS);
        dataset2.setColors(ColorTemplate.COLORFUL_COLORS);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataset1);
        dataSets.add(dataset2);

//         defining x-axis labels
        ArrayList<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        BarData data = new BarData(labels, dataSets);
        barChart.setData(data);
    }
}
