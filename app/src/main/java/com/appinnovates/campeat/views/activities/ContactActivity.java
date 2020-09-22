package com.appinnovates.campeat.views.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.services.InquiryService.InquiryService;
import com.appinnovates.campeat.services.InquiryService.InquiryServiceInterface;
import com.appinnovates.campeat.utils.CommonUtils;
import com.appinnovates.campeat.utils.EditTextFocusUtil;
import com.appinnovates.campeat.utils.LocaleManager;
import com.appinnovates.campeat.utils.PermissionsUtil;


public class ContactActivity extends AppCompatActivity implements View.OnClickListener, InquiryServiceInterface {

    private Button buttonSubmit;
    private EditText edtFullName, edtEmail, edtContact, edtMessage;
    private boolean isAllInfoFilled;
    private InquiryService inquiryService;
    private EditText[] editTexts;
    private String emailID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        initFields();
        initView();
        setUpServices();
        addEditTextToList();
        setListener();
    }

    private void addEditTextToList() {
        editTexts = new EditText[]{edtFullName, edtEmail, edtContact};

    }

    private void initFields() {
    }

    private void setUpServices() {
        inquiryService = InquiryService.getInstance();
        inquiryService.setDelegateAndContext(this, getApplicationContext());
    }

    private void setListener() {
        buttonSubmit.setOnClickListener(this);
        EditTextFocusUtil.getInstance().setFocusListener(getApplicationContext(), editTexts);
    }

    private void initView() {
        buttonSubmit = findViewById(R.id.button_submit);
        edtFullName = findViewById(R.id.edt_full_name);
        edtEmail = findViewById(R.id.edt_email);
        edtContact = findViewById(R.id.edt_contact);
        edtMessage = findViewById(R.id.edt_message);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == buttonSubmit.getId()) {
            emailID = edtEmail.getText().toString().trim().toLowerCase();

            checkValidation();

            if (!PermissionsUtil.isNetworkAvailable(this)) {
                CommonUtils.showToast(this, getString(R.string.no_internet_available));
                return;
            }
            if (isAllInfoFilled) {
                inquiryService.submitInquiry(edtFullName.getText().toString(), emailID, edtContact.getText().toString(), edtMessage.getText().toString());
            }

        }
    }

    private void checkValidation() {
        isAllInfoFilled=true;
        if (TextUtils.isEmpty(emailID)) {
            edtEmail.setError(getString(R.string.error_msg_email));
            isAllInfoFilled = false;
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(emailID).matches()) {
                edtEmail.setError(getString(R.string.error_msg_valid_email));
                isAllInfoFilled = false;
            }
        }
        if (TextUtils.isEmpty(edtFullName.getText().toString())) {
            edtFullName.setError(getString(R.string.error_msg_user_name));
            isAllInfoFilled = false;
        }
        if (TextUtils.isEmpty(edtContact.getText().toString())) {
            edtContact.setError(getString(R.string.error_msg_contact));
            isAllInfoFilled = false;
        } else if (edtContact.getText().toString().length()<10 && edtContact.getText().toString().length()>13){
            edtContact.setError(getResources().getString(R.string.valid_mobile));
            isAllInfoFilled = false;
        }
        if (TextUtils.isEmpty(edtMessage.getText().toString())) {
            edtMessage.setError(getString(R.string.error_msg_message));
            isAllInfoFilled = false;
        }


    }

    //InquiryService Response
    @Override
    public void onInquirySuccess(String message) {
        startActivity(new Intent(getApplicationContext(), EnquiryActivity.class));
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        finish();
    }

    @Override
    public void onInquiryFailure(String message) {
        CommonUtils.showToast(getApplicationContext(), message);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this, LocaleManager.getLanguage(this));
    }
}
