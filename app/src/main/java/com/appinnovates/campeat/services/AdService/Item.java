package com.appinnovates.campeat.services.AdService;

import android.graphics.Bitmap;

public class Item {
    public Bitmap bitmap;
    public int seconds;

    public Item(Bitmap bitmap, int seconds) {
        this.bitmap = bitmap;
        this.seconds = seconds;
    }
}
