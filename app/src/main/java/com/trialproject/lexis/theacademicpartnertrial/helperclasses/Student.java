package com.trialproject.lexis.theacademicpartnertrial.helperclasses;

import java.io.Serializable;

/**
 * Created by Lexis on 25/01/2016.
 */
public class Student implements Serializable{

    private String level;
    private String school;
    private String college;
    private String[] departments;
    private String[] courses;

    public Student(String level, String[] courses, String[] departments, String college, String school) {
        this.level = level;
        this.courses = courses;
        this.departments = departments;
        this.college = college;
        this.school = school;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String[] getDepartments() {
        return departments;
    }

    public void setDepartments(String[] departments) {
        this.departments = departments;
    }

    public String[] getCourses() {
        return courses;
    }

    public void setCourses(String[] courses) {
        this.courses = courses;
    }
}
