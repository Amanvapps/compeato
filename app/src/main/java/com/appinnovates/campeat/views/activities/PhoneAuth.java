package com.appinnovates.campeat.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.appinnovates.campeat.R;
import com.hbb20.CountryCodePicker;

public class PhoneAuth extends AppCompatActivity {

    TextView emailVerify;
    TextView mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);

        emailVerify=findViewById(R.id.email_address_verify);

        CountryCodePicker countryCodePicker = findViewById(R.id.code_picker);
        mobile = findViewById(R.id.mobile_number);
        Button next = findViewById(R.id.next);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        String phone = bundle.getString("mobile");
        String countryCode = bundle.getString("country_code");
        String email = bundle.getString("email");
        String password = bundle.getString("password");
        int code = Integer.valueOf(countryCode.split("  ")[1]);

        countryCodePicker.setArrowSize((int) getResources().getDimension(R.dimen._10sdp));
        countryCodePicker.setCountryForPhoneCode(code);
        mobile.setText(phone);

        next.setOnClickListener(view -> {
            if (validate()) {
                Intent intent = new Intent(this, PhoneOtpAuth.class);
                intent.putExtra("bundle", bundle);
                intent.putExtra("mobile", mobile.getText().toString());
                intent.putExtra("country_code", countryCodePicker.getTextView_selectedCountry().getText());
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        emailVerify.setOnClickListener(view -> {
            Intent intent=new Intent(PhoneAuth.this, VerifyActivity.class);
            intent.putExtra("bundle",bundle);
            intent.putExtra("email",email);
            intent.putExtra("password",password);
            intent.putExtra("mobile", mobile.getText().toString());
            intent.putExtra("country_code",countryCodePicker.getTextView_selectedCountry().getText() );
            startActivity(intent);
        });
    }

    boolean validate(){
        if (TextUtils.isEmpty(mobile.getText().toString())) {
            mobile.setError(getResources().getString(R.string.phone_text));
            return false;
        } else if (mobile.getText().length() != 10) {
            mobile.setError(getString(R.string.valid_phone));
            return false;
        }
        return true;
    }
}
