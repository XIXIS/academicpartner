package com.trialproject.lexis.theacademicpartnertrial.helperclasses;

import java.io.Serializable;

/**
 * Created by Lexis on 25/01/2016.
 */
public class Student implements Serializable{

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String[] getDeptartments() {
        return deptartments;
    }

    public void setDeptartments(String[] deptartments) {
        this.deptartments = deptartments;
    }

    public String[] getCourses() {
        return courses;
    }

    public void setCourses(String[] courses) {
        this.courses = courses;
    }

    private int id;
    private int pin;
    private int level;
    private String[] deptartments;
    private String[] courses;

    public Student(){

    }

    public Student(String[] courses, String[] deptartments, int level, int pin, int id) {
        this.courses = courses;
        this.deptartments = deptartments;
        this.level = level;
        this.pin = pin;
        this.id = id;
    }
}
