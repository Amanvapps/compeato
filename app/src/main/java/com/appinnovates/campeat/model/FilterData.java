package com.appinnovates.campeat.model;

public class FilterData {

    public FilterData(){}

    public FilterData(int image, String text) {
        this.image = image;
        this.text = text;
    }

    private int image;
    private String text;

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    private Boolean isSelected;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
