package com.appinnovates.campeat.views.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.appinnovates.campeat.R;

public class EasyPayFinalAmountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_pay_final_amount);
        findViewById(R.id.back).setOnClickListener(v -> finish());
    }
}
