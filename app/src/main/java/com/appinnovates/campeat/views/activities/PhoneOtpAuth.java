package com.appinnovates.campeat.views.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;

import com.appinnovates.campeat.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class PhoneOtpAuth extends AppCompatActivity {

    EditText edit1, edit2, edit3, edit4, edit5, edit6;
    private static final String TAG = "PhoneOtpAuth";
    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";
    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;
    private FirebaseAuth mAuth;
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private String email;
    private String phoneNumber;
    private String name;
    private String password;
    private PhoneAuthCredential credential;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private ContentLoadingProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_otp_auth);
        progressBar = findViewById(R.id.progressBar2);

        edit1 = findViewById(R.id.text_view_number);
        edit2 = findViewById(R.id.text_view_number2);
        edit3 = findViewById(R.id.text_view_number3);
        edit4 = findViewById(R.id.text_view_number4);
        edit5 = findViewById(R.id.text_view_number5);
        edit6 = findViewById(R.id.text_view_number6);
        TextView resendOtp = findViewById(R.id.email_address_verify);
        edit2.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (edit2.getText().toString().equalsIgnoreCase("")) {
                    edit1.requestFocus();
                    edit2.setBackgroundTintList(getResources().getColorStateList(R.color.light_grey));
                }
            }
            return false;
        });
        edit3.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (edit3.getText().toString().equalsIgnoreCase("")) {
                    edit2.requestFocus();
                    edit3.setBackgroundTintList(getResources().getColorStateList(R.color.light_grey));
                }
            }
            return false;
        });
        edit4.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (edit4.getText().toString().equalsIgnoreCase("")) {
                    edit3.requestFocus();
                    edit4.setBackgroundTintList(getResources().getColorStateList(R.color.light_grey));
                }
            }
            return false;
        });
        edit5.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (edit5.getText().toString().equalsIgnoreCase("")) {
                    edit4.requestFocus();
                    edit5.setBackgroundTintList(getResources().getColorStateList(R.color.light_grey));
                }
            }
            return false;
        });
        edit6.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (edit6.getText().toString().equalsIgnoreCase("")) {
                    edit5.requestFocus();
                    edit6.setBackgroundTintList(getResources().getColorStateList(R.color.light_grey));
                }
            }
            return false;
        });
        Bundle userDetails = getIntent().getBundleExtra("bundle");
        name = userDetails != null ? userDetails.getString("name") : "Not Specified";
        password = userDetails != null ? userDetails.getString("password") : "Not Specified";
        Bundle bundle = getIntent().getExtras();
        String phone = null;
        if (bundle != null) {
            phone = bundle.getString("mobile");
        }
        if (bundle != null) {
            email = bundle.getString("email");
        }
        String countryCode = Objects.requireNonNull(bundle).getString("country_code");

        Log.i("phone", countryCode + phone);
        countryCode = countryCode.split(" ")[1];
        phoneNumber = countryCode.concat(Objects.requireNonNull(phone));

        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
        mAuth = FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                mVerificationInProgress = false;
                updateUI(STATE_VERIFY_SUCCESS, credential);
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);
                mVerificationInProgress = false;
                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                }


                updateUI(STATE_VERIFY_FAILED);
                // [END_EXCLUDE]
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);
                mVerificationId = verificationId;
                mResendToken = token;

                // [START_EXCLUDE]
                // Update UI
                updateUI(STATE_CODE_SENT);
                // [END_EXCLUDE]
            }
        };


        edit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edit1.getText().toString().length() == 1)     //size as per your requirement
                {
                    edit2.requestFocus();
                    edit1.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
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
                if (edit2.getText().toString().length() == 1)     //size as per your requirement
                {
                    edit3.requestFocus();
                    edit2.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
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
                if (edit3.getText().toString().length() == 1)     //size as per your requirement
                {
                    edit4.requestFocus();
                    edit3.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
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
                if (edit4.getText().toString().length() == 1)     //size as per your requirement
                {
                    edit5.requestFocus();
                    edit4.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                    edit5.setTextColor(getResources().getColor(R.color.white_color));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        edit5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edit5.getText().toString().length() == 1)     //size as per your requirement
                {
                    edit6.requestFocus();
                    edit5.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                    edit6.setTextColor(getResources().getColor(R.color.white_color));

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        edit6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edit6.getText().toString().length() == 1)     //size as per your requirement
                {
                    edit6.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                    if (edit1.getText().length() == 1 && edit2.getText().length() == 1 && edit3.getText().length() == 1 && edit4.getText().length() == 1 && edit5.getText().length() == 1 && edit6.getText().length() == 1) {
                        String otp = edit1.getText().toString() + edit2.getText().toString() + edit3.getText().toString() + edit4.getText().toString() + edit5.getText().toString() + edit6.getText().toString();
                        verifyPhoneNumberWithCode(mVerificationId, otp);
                        Log.i("check", otp + "");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        startPhoneNumberVerification(phoneNumber);
        resendOtp.setOnClickListener(view -> resendVerificationCode(phoneNumber, mResendToken));
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        if (mVerificationInProgress) {
            startPhoneNumberVerification(phoneNumber);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }


    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    // [START resend_verification]
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }
    // [END resend_verification]

    // [START sign_in_with_phone]
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");

                        FirebaseUser user = task.getResult().getUser();
                        // [START_EXCLUDE]
                        updateUI(STATE_VERIFY_SUCCESS, user);

                        // [END_EXCLUDE]
                    } else {
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                        }

                        updateUI(STATE_SIGNIN_FAILED);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END sign_in_with_phone]

    private void updateUI(int uiState) {
        updateUI(uiState, mAuth.getCurrentUser(), null);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            updateUI(STATE_SIGNIN_SUCCESS, user);
        } else {
            updateUI(STATE_INITIALIZED);
        }
    }

    private void updateUI(int uiState, FirebaseUser user) {
        updateUI(uiState, user, null);
    }

    private void updateUI(int uiState, PhoneAuthCredential cred) {
        updateUI(uiState, null, cred);
    }

    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
        switch (uiState) {
            case STATE_INITIALIZED:
                // Initialized state, show only the phone number field and start button

                break;
            case STATE_CODE_SENT:
                // Code sent state, show the verification field, the

                break;
            case STATE_VERIFY_FAILED:
                // Verification has failed, show all options

                break;
            case STATE_VERIFY_SUCCESS:
                verifyUser(cred);
                break;

            case STATE_SIGNIN_SUCCESS:
                // Np-op, handled by sign-in check
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    void verifyUser(PhoneAuthCredential cred) {
        if (cred != null) {
            if (cred.getSmsCode() != null) {
                String otp = cred.getSmsCode();
                edit1.setText(otp.charAt(0) + "");
                edit2.setText(otp.charAt(1) + "");
                edit3.setText(otp.charAt(2) + "");
                edit4.setText(otp.charAt(3) + "");
                edit5.setText(otp.charAt(4) + "");
                edit6.setText(otp.charAt(5) + "");

            } else {
                edit1.setText(R.string.instant_validation);
            }
        }
        progressBar.show();
        saveData();
    }

    void saveData(){
        progressBar.hide();
        ParseUser parseUser = ParseUser.getCurrentUser();
        parseUser.put("name", name);
        parseUser.put("phone", phoneNumber);
        parseUser.setUsername(email);
        parseUser.setEmail(email);
        parseUser.setPassword(password);

        Map<String, String> map = new HashMap<>();
        String id = String.valueOf(ParseUser.getCurrentUser().getObjectId());
        map.put("userId", id);

        ParseCloud.callFunctionInBackground("verifyUserByMobileOTP", map, (HashMap<String, Object> objects, ParseException e) -> {
            if (e == null) {
                Toast.makeText(this, "Phone number Verified", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, SignInActivity.class));
                finish();
            } else {
                Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
}
