package com.appinnovates.campeat.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.appinnovates.campeat.R;
import com.google.android.material.button.MaterialButton;

public class LocationDialog extends Dialog {

    private MaterialButton allow,deny;
    private LocationInterFace locationInterFace;
    private Context context;
    public LocationDialog(Context context,LocationInterFace locationInterFace) {
        super(context, R.style.Theme_AppCompat_Light_Dialog);
        this.locationInterFace=locationInterFace;
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.location_dialog);
        allow=findViewById(R.id.allow);
        deny=findViewById(R.id.deny);
        setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams wmlp = getWindow().getAttributes();
        wmlp.y = 100;

        allow.setOnClickListener(v -> {
            locationInterFace.onAllowClicked();
            dismiss();
        });

        deny.setOnClickListener(v -> {
            locationInterFace.onDenyClicked();
            dismiss();
        });
    }

    public interface LocationInterFace{
        void onAllowClicked();
        void onDenyClicked();
    }

}