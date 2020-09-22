package com.appinnovates.campeat.model;

import androidx.cardview.widget.CardView;
import android.widget.TextView;

public class WalletModel<T> {
    public CardView cardView;
    public TextView textView;
    public T fragment;
    public boolean isAdded = false;

    public WalletModel(CardView cardView, TextView textView, T fragment) {
        this.cardView = cardView;
        this.textView = textView;
        this.fragment = fragment;
    }
}
