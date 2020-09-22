package com.appinnovates.campeat.model;

import java.util.List;

/**
 * Created by reetu on 26/6/18.
 */

public class BranchMenuType {
    private String menuType;
    private int index;
    private List<String> menuName;

    public BranchMenuType(String menuType,int index){
        this.menuType = menuType;
        this.index = index;
    }

    public List<String> getMenuName() {
        return menuName;
    }

    public void setMenuName(List<String> menuName) {
        this.menuName = menuName;
    }

    public String getMenuType() {
        return menuType;
    }

    public int getIndex() {
        return index;
    }
}
