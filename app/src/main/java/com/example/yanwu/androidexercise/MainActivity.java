package com.example.yanwu.androidexercise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.yanwu.androidexercise.model.Grade;
import com.example.yanwu.androidexercise.model.Student;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_textview)
    public void onTextViewClick() {
        HashMap<String, String> grades1 = new HashMap<>();
        grades1.put("math", "99");
        grades1.put("chinese", "98");
        Grade grade = new Grade();
        grade.setGrades(grades1);

        HashMap<String, String> grades2 = new HashMap<>();
        grades2.put("math", "10");
        grades2.put("chinese", "20");
        Grade grade2 = new Grade();
        grade2.setGrades(grades2);

        HashMap<String, Grade> map = new HashMap<>();
        map.put("s1", grade);
        map.put("s2", grade2);
        Student student = new Student();
        student.setName("ersha");
        student.setStudents(map);

        Intent intent1 = new Intent(this, TextViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("student", student);
        intent1.putExtras(bundle);
        startActivity(intent1);
    }

    @OnClick(R.id.btn_edittext)
    public void onEditTextClick() {
        Intent intent2 = new Intent(this, EditTextActivity.class);
        startActivity(intent2);
    }

    @OnClick(R.id.btn_viewflipper)
    public void onViewFlipperClick() {
        Intent intent3 = new Intent(this, ViewFlipperActivity.class);
        startActivity(intent3);
    }

    @OnClick(R.id.btn_searchview)
    public void onSearchViewClick() {
        Intent intent4 = new Intent(this, SearchViewActivity.class);
        startActivity(intent4);
    }

    @OnClick(R.id.btn_rxjava)
    public void onRxJavaClick() {
        Intent intent5 = new Intent(this, RxJava2Activity.class);
        startActivity(intent5);
    }

    @OnClick(R.id.btn_ble)
    public void onBleClick() {
        Intent intent6 = new Intent(this, BleActivity.class);
        startActivity(intent6);
    }

    @OnClick(R.id.btn_progress_bar)
    public void onProgressBarClick() {
        Intent intent7 = new Intent(this, ProgressBarActivity.class);
        startActivity(intent7);
    }

    @OnClick(R.id.btn_spinner)
    public void onSpinnerClick() {
        Intent intent8 = new Intent(this, SpinnerActivity.class);
        startActivity(intent8);
    }

    @OnClick(R.id.btn_mpchart)
    public void onMPChartClick() {
        Intent intent9 = new Intent(this, MPChartActivity.class);
        startActivity(intent9);
    }

    @OnClick(R.id.btn_recyclerview)
    public void onRecyclerViewClick() {
        Intent intent10 = new Intent(this, RecyclerviewActivity.class);
        startActivity(intent10);
    }
}
