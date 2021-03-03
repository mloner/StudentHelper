package com.example.studenthelpermobile.Model;

import java.util.Date;

public class Lesson {

    private String subject_name;
    private String prepod_name;
    private String class_name;
    private String lesson_type;
    private String lesson_start;
    private String lesson_end;
    private String group;
    private Date date;
    private boolean isRemote;
    private int subgroup;
    private String description;
    private String weekdayname;

    public Lesson(){
        subject_name = "";
        prepod_name = "";
        class_name = "";
        lesson_type = "";
        lesson_end = "";
        group = "";
        date = new Date();
        isRemote = false;
        subgroup = 0;
        description = "";
        weekdayname = "";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLesson_start() {
        return lesson_start;
    }

    public void setLesson_start(String lesson_start) {
        this.lesson_start = lesson_start;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getPrepod_name() {
        return prepod_name;
    }

    public void setPrepod_name(String prepod_name) {
        this.prepod_name = prepod_name;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getLesson_type() {
        return lesson_type;
    }

    public void setLesson_type(String lesson_type) {
        this.lesson_type = lesson_type;
    }

    public String getLesson_end() {
        return lesson_end;
    }

    public void setLesson_end(String lesson_end) {
        this.lesson_end = lesson_end;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isRemote() {
        return isRemote;
    }

    public void setRemote(boolean remote) {
        isRemote = remote;
    }

    public int getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(int subgroup) {
        this.subgroup = subgroup;
    }

    public String getWeekdayname() {
        return weekdayname;
    }

    public void setWeekdayname(String weekdayname) {
        this.weekdayname = weekdayname;
    }
}
