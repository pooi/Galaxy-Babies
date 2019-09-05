package com.samsung.galaxy_babies.obj;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Comment implements Serializable {

    private User user;
    private Calendar date;
    private String content;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getDateString(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
        return sdf.format(this.date.getTime());
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
