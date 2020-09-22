package com.appinnovates.campeat.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.utils.Constant;
import com.appinnovates.campeat.views.fragments.Agreement;
import com.google.android.material.button.MaterialButton;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseUser;

import java.util.HashMap;
import java.util.Map;

public class UserAgreement extends AppCompatActivity {

    private Map<String,String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement);
        map=new HashMap<>();
        Toolbar toolbar = findViewById(R.id.tool_bar);
        MaterialButton button=findViewById(R.id.btn_next);
        toolbar.setNavigationIcon(R.drawable.arrow_back_black);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        getSupportFragmentManager().beginTransaction().add(R.id.fragment,new Agreement()).commit();
        button.setOnClickListener(v -> {
            String userId= ParseUser.getCurrentUser().getObjectId();
            map.put("user_id",userId);
            acceptTnC(map);
        });

    }

    private void launchHomeScreen() {
        Intent intent = new Intent(this, TutorialScreen.class);
        intent.putExtra(Constant.ISSPLASH, true);
        intent.putExtra(Constant.ISLOGINPAGE, true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    void acceptTnC(Map map){
        ParseCloud.callFunctionInBackground("acceptTerms",map , (FunctionCallback<Map>) (objects, e) -> {
            if (e == null) {
                if (objects != null && objects.size() > 0) {
                    launchHomeScreen();
                }
            } else {
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
}
