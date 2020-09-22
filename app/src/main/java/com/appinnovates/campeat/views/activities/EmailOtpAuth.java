package com.appinnovates.campeat.views.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.appinnovates.campeat.R;

public class EmailOtpAuth extends AppCompatActivity {

    EditText edit1,edit2,edit3,edit4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_otp_auth);

        edit1=findViewById(R.id.text_view_number);
        edit2=findViewById(R.id.text_view_number2);
        edit3=findViewById(R.id.text_view_number3);
        edit4=findViewById(R.id.text_view_number4);

        edit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(edit1.getText().toString().length()==1)     //size as per your requirement
                {
                    edit2.requestFocus();
                    edit2.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                    edit2.setTextColor(getResources().getColor(R.color.white_color));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(edit2.getText().toString().length()==1)     //size as per your requirement
                {
                    edit3.requestFocus();
                    edit3.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                    edit3.setTextColor(getResources().getColor(R.color.white_color));

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edit3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(edit3.getText().toString().length()==1)     //size as per your requirement
                {
                    edit4.requestFocus();
                    edit4.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                    edit4.setTextColor(getResources().getColor(R.color.white_color));

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edit4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(edit4.getText().toString().length()==1)     //size as per your requirement
                {

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
