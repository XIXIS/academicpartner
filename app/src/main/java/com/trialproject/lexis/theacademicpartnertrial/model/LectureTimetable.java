package com.trialproject.lexis.theacademicpartnertrial.model;

import java.io.Serializable;

/**
 * Created by Lexis on 4/1/2016.
 */
public class LectureTimetable implements Serializable{

    private String courseCode;
    private String courseTitle;
    private String time;
    private String venue;


    public LectureTimetable(String courseCode, String courseTitle, String time, String venue) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.time = time;
        this.venue = venue;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }




}
