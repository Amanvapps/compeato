package com.appinnovates.campeat.views.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.utils.LocaleManager;

public class EnquiryActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry);
        initView();
        setListener();
    }

    private void setListener() {
        buttonDone.setOnClickListener(this);
    }

    private void initView() {
        buttonDone = findViewById(R.id.button_done);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == buttonDone.getId()) {
            finish();
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this, LocaleManager.getLanguage(this));
    }
}
