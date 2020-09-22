package com.appinnovates.campeat.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.utils.CommonUtils;
import com.appinnovates.campeat.utils.Constant;
import com.parse.ParseUser;


public class VerifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_mail);
        EditText email = findViewById(R.id.edit_text_email_reset);

        String userEmail=getIntent().getStringExtra("email");
        Bundle bundle=getIntent().getBundleExtra("bundle");
        assert bundle != null;
        String name=bundle.getString("name");
        String password=bundle.getString("password");
        String mobile=bundle.getString("mobile");
        String countryCode=bundle.getString("country_code");

        countryCode = countryCode.split("  ")[1];
        String phone = countryCode.concat(mobile);

        Log.i("mobile",mobile);

        email.setText(userEmail);

        Button verify = findViewById(R.id.button_reset);
        verify.setOnClickListener(view -> {
            ParseUser parseUser = new ParseUser();
            String username = email.getText().toString();
            parseUser.put("name",name);
            parseUser.put("phone",phone);
            parseUser.put(Constant.USERNAME,email.getText().toString());
            parseUser.setUsername(username);
            parseUser.setEmail(username);

            parseUser.setPassword(password);
            parseUser.signUpInBackground(e -> {
                if (e == null) {
                    startActivity(new Intent(VerifyActivity.this, SignInActivity.class));
                    CommonUtils.showToast(VerifyActivity.this, getString(R.string.successfully_signUp));
                    finishAffinity();

                } else {
                    Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
