package com.example.yanwu.androidexercise.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by yanwu on 3/5/18.
 */

public class Grade implements Serializable{
    private HashMap<String, String> grades;

    public HashMap<String, String> getGrades() {
        return grades;
    }

    public void setGrades(HashMap<String, String> grades) {
        this.grades = grades;
    }
}
