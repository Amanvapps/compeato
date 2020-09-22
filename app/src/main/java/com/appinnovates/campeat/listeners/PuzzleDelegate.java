package com.appinnovates.campeat.listeners;

import android.content.Context;

public interface PuzzleDelegate {
    void move(Context context, String direction, int position);
}
