package com.example.yanwu.androidexercise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.yanwu.androidexercise.model.Grade;
import com.example.yanwu.androidexercise.model.Student;

import java.util.HashMap;

import butterknife.BindString;
import butterknife.ButterKnife;

public class TextViewActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textview);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        Student student = (Student) bundle.get("student");
        System.out.println(student.getName());
        System.out.println(student.getStudents().get("s1").getGrades().get("math"));
    }
}
