package com.appinnovates.campeat.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private ArrayList<ImageView> mButtons;
    private int mColumnWidth, mColumnHeight;

    public CustomAdapter(ArrayList<ImageView> buttons, int columnWith, int columnHeight) {
        mButtons = buttons;
        mColumnWidth = columnWith;
        mColumnHeight = columnHeight;
    }

    @Override
    public int getCount() {
        return mButtons.size();
    }

    @Override
    public Object getItem(int position) {
        return (Object) mButtons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView button;

        if (convertView == null){
            button = mButtons.get(position);
        }else {
            button = (ImageView)convertView;
        }

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(mColumnWidth, mColumnHeight);
        button.setLayoutParams(params);

        return button;
    }
}
