package com.example.studenthelpermobile.Model;

public class ChatTitle {

    private String lessonName;
    private String prepodName;
    private String group;
    private int count;

    public ChatTitle() {
        lessonName = "";
        prepodName = "";
        count = 0;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getPrepodName() {
        return prepodName;
    }

    public void setPrepodName(String prepodName) {
        this.prepodName = prepodName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

