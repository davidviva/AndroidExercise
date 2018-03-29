package com.example.yanwu.androidexercise.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by yanwu on 3/5/18.
 */

public class Student implements Serializable{
    private String name;
    private HashMap<String, Grade> students;

    public HashMap<String, Grade> getStudents() {
        return students;
    }

    public void setStudents(HashMap<String, Grade> students) {
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
