package com.appinnovates.campeat.views.activities;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.appinnovates.campeat.R;

public class EasyPayAmountActivity extends AppCompatActivity {

    private EditText edtPoints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_pay_amount);
        setUpViews();
    }

    private void setUpViews(){
        findViewById(R.id.back).setOnClickListener(v -> finish());
        edtPoints = findViewById(R.id.edit_text_amount);
        findViewById(R.id.button_discount).setOnClickListener(view -> {
            double points = 0;
            if (edtPoints.getText() != null) {
                points = Double.parseDouble(edtPoints.getText().toString().replace("₩", ""));
            }

            if (points <= 0){

                return;
            }


        });
    }
}
