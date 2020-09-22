package com.appinnovates.campeat.services.AdService;

import com.parse.ParseObject;

public class Option {
    public String id,name;
    public boolean isCorrect = false, isSelected = false;
    public ParseObject object;
    public Option(String id, String name,ParseObject object,boolean isCorrect) {
        this.id = id;
        this.name = name;
        this.object = object;
        this.isCorrect = isCorrect;
    }
}
