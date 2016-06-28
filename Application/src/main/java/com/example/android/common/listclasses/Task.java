package com.example.android.common.listclasses;

import java.util.Date;

/**
 * Created by JoeXL on 30/04/2016.
 */
public class Task {

    private String name;
    private Date date;
    private boolean selected;

    public Task(String name) {
        this.setName(name);
        //this.setDate(date);
        //this.setSelected(selected);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
