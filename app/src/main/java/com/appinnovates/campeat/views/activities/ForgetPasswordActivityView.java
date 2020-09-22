package com.appinnovates.campeat.views.activities;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.services.ForgotPasswordService.ForgotPasswordService;
import com.appinnovates.campeat.services.ForgotPasswordService.ForgotPasswordServiceViewInterface;
import com.appinnovates.campeat.utils.CommonUtils;
import com.appinnovates.campeat.utils.EditTextFocusUtil;
import com.appinnovates.campeat.utils.LocaleManager;
import com.appinnovates.campeat.utils.PermissionsUtil;

public class ForgetPasswordActivityView extends Activity implements View.OnClickListener, ForgotPasswordServiceViewInterface {

    private TextView textViewSignIn;
    private EditText edtEmail;
    private Button btnGetResetLink;
    private ForgotPasswordService forgotPasswordService = null;
    private EditText[] editTexts;
    private String emailID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initView();
        setListener();
        setUpServices();
    }

    private void setListener() {
        textViewSignIn.setOnClickListener(this);
        btnGetResetLink.setOnClickListener(this);

        EditTextFocusUtil.getInstance().setFocusListener(getApplicationContext(), editTexts);
    }

    private void initView() {
        textViewSignIn = findViewById(R.id.text_view_sign_in);
        edtEmail = findViewById(R.id.edit_text_email_reset);
        btnGetResetLink = findViewById(R.id.button_reset);

        editTexts = new EditText[]{edtEmail};
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_view_sign_in:
                finish();
                break;
            case R.id.button_reset:
                emailID = edtEmail.getText().toString().trim().toLowerCase();
                getResetLink();
                break;

        }
    }

    private void getResetLink() {
        if (!Patterns.EMAIL_ADDRESS.matcher(emailID).matches()) {
            edtEmail.setError(getString(R.string.error_msg_valid_email));
        } else {
            if (!PermissionsUtil.isNetworkAvailable(this)) {
                CommonUtils.showToast(this, getString(R.string.no_internet_available));
                return;
            }
            forgotPasswordService.requestResetPassword(emailID);
        }
    }

    private void setUpServices() {
        forgotPasswordService = ForgotPasswordService.getInstance();
        forgotPasswordService.setDelegate(this);
    }

    //Forgot password response
    @Override
    public void onForgotPasswordSuccess(String email) {
        CommonUtils.showToast(this, getString(R.string.reset_link_send_to, email));
    }

    @Override
    public void onForgotPasswordFailure(String message) {
        CommonUtils.showToast(this, message);
    }

    @Override
    public void showProgress() {
        CommonUtils.showProgress(this);
    }

    @Override
    public void hideProgress() {
        CommonUtils.hideProgress();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this, LocaleManager.getLanguage(this));
    }
}
