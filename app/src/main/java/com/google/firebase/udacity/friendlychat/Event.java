package com.google.firebase.udacity.friendlychat;

/**
 * Created by desktop on 19/10/2017.
 */

public class Event {

    String title;
    String locaiton;
    String desc;
    String type;

    public Event(){

    }

    public Event(String title, String locaiton, String type, String desc){
        this.title = title;
        this.locaiton = locaiton;
        this.desc = desc;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocaiton() {
        return locaiton;
    }

    public void setLocaiton(String locaiton) {
        this.locaiton = locaiton;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
