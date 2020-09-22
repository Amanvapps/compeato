package com.appinnovates.campeat.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by neha on 5/14/18.
 */

public class HomeModel implements Serializable {
    private String name,time_left,slot;


    public HomeModel(String name, String time_left, String slot) {
        this.name = name;
        this.time_left = time_left;
        this.slot = slot;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime_left() {
        return time_left;
    }


}
