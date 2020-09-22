package com.appinnovates.campeat.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.appinnovates.campeat.R;

public class YoutubeDialog  extends Dialog {

    public YoutubeDialog(@NonNull Context context) {
        super(context, R.style.Theme_AppCompat_Light_Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.youtube_dialog_layout);
        setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams wmlp = getWindow().getAttributes();
        findViewById(R.id.cross).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

}
