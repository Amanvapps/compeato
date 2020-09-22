package com.appinnovates.campeat.utils;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.appinnovates.campeat.R;

import java.util.List;

public class EditTextFocusUtil {
    private static EditTextFocusUtil editTextFocusUtil;

    public static EditTextFocusUtil getInstance(){
        if (editTextFocusUtil == null){
            editTextFocusUtil = new EditTextFocusUtil();
        }
        return editTextFocusUtil;
    }

    public void setFocusListener(final Context context, EditText[] editTexts){
        for (EditText editText:editTexts){
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean isFocused) {
                    if (isFocused){
                        view.setBackground(context.getResources().getDrawable(R.drawable.rounded_border_orange));
                    }else{
                        view.setBackground(context.getResources().getDrawable(R.drawable.rounded_border_grey));
                    }
                }
            });
        }

    }
}
