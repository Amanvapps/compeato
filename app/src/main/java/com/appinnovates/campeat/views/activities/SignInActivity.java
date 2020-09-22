package com.appinnovates.campeat.views.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.services.CloudNetwork.ApiClient;
import com.appinnovates.campeat.services.CloudNetwork.ApiInterface;
import com.appinnovates.campeat.utils.CommonUtils;
import com.appinnovates.campeat.utils.Constant;
import com.appinnovates.campeat.utils.EditTextFocusUtil;
import com.appinnovates.campeat.utils.LocaleManager;
import com.appinnovates.campeat.utils.LocationDialog;
import com.appinnovates.campeat.utils.PermissionsUtil;
import com.appinnovates.campeat.utils.UserPreferences;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener, LocationDialog.LocationInterFace {


    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private TextView textViewSignUp;
    private Button signInButton;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView forgotTxt;
    private EditText[] editTexts;
    private KakaoSessionCallback mKakaocallback;
    private ImageView kakao;
    ImageView google;
    private String idToken = "";
    private String id = "";
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private GoogleSignInClient mGoogleSignInClient;
    private boolean kakaoLoginHit = false;
    private String userName = "";
    private String profilePic = "";
    private String profileThumbnail = "";
    private String firstName = "";
    private String lastName = "";
    private String emailID;
    private Float latitude, longitude;
    public int MY_PERMISSIONS_REQUEST_READ_CONTACT = 0;
    public LocationDialog dialog;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private Map<String, String> map;
    private Boolean isAggreement = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(200 * 1000);
        locationRequest.setNumUpdates(1);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        latitude = (float) location.getLatitude();
                        longitude = (float) location.getLongitude();
                        UserPreferences.getInstance().save(Constant.LATITUDE, latitude);
                        UserPreferences.getInstance().save(Constant.LONGITUDE, longitude);
                    }
                }
            }
        };
        initViews();
        setListener();
        getCurrentLocation();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]


    }
/*    other

            Code*/


    // [END on_start_check_user]

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // RestaurantDeals returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                CommonUtils.showProgress(this);
                GoogleSignInAccount account = task.getResult(ApiException.class);
                idToken = account.getIdToken();
                id = account.getId();
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                CommonUtils.hideProgress();
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
        } else if (requestCode == 101) {
            startActivity(new Intent(this, SignInActivity.class));
        } else {
            com.kakao.auth.Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data);
        }

    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());


        firstName = acct.getGivenName();
        lastName = acct.getFamilyName();
        // [START_EXCLUDE silent]
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        assert user != null;
                        userName = user.getDisplayName();
                        Map<String, String> authData = new HashMap<>();
                        authData.put("id_token", idToken);
                        authData.put("id", id);
                        authData.put("name", user.getDisplayName());
                        authData.put("email", user.getEmail());
                        logInWithAuthType("google", authData);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());

                    }

                    // [START_EXCLUDE]
                    // [END_EXCLUDE]
                });
    }
    // [END auth_with_google]

    // [START signin]

    // [END signin]

    private void setListener() {
        textViewSignUp.setOnClickListener(this);
        signInButton.setOnClickListener(this);
        forgotTxt.setOnClickListener(this);
        google.setOnClickListener(this);
        kakao.setOnClickListener(this);

        EditTextFocusUtil.getInstance().setFocusListener(getApplicationContext(), editTexts);
    }

    private void initViews() {
        map = new HashMap<>();
        textViewSignUp = findViewById(R.id.signup);
        signInButton = findViewById(R.id.sign_in);
        editTextEmail = findViewById(R.id.edit_text_user_name);
        editTextPassword = findViewById(R.id.edit_text_password);
        forgotTxt = findViewById(R.id.forgot);
        google = findViewById(R.id.google);
        kakao = findViewById(R.id.kakao);

        editTexts = new EditText[]{editTextEmail, editTextPassword};


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup:
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.forgot:
                startActivity(new Intent(getApplicationContext(), ForgetPasswordActivityView.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.sign_in:
                if (!PermissionsUtil.isNetworkAvailable(this)) {
                    CommonUtils.showToast(this, getString(R.string.no_internet_available));
                    return;
                }
                emailID = editTextEmail.getText().toString().trim().toLowerCase();
                signIn();
                break;
            case R.id.kakao:
                kakaoLoginHit = false;
                if (!PermissionsUtil.isNetworkAvailable(this)) {
                    CommonUtils.showToast(this, getString(R.string.no_internet_available));
                    return;
                }
                isKakaoLogin();
                break;
            case R.id.google:
                if (!PermissionsUtil.isNetworkAvailable(this)) {
                    CommonUtils.showToast(this, getString(R.string.no_internet_available));
                    return;
                }
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
                break;
        }
    }

    private void signIn() {
        if (checkValidation()) {
            CommonUtils.showProgress(this);
            ParseUser.logInInBackground(emailID, editTextPassword.getText().toString()
                    , (ParseUser user, ParseException e) -> {
                        if (e == null) {
                            if (user != null) {
                                CommonUtils.showToast(SignInActivity.this, getString(R.string.successfully_login));
                                checkUserAgreement();
                            }
                            CommonUtils.hideProgress();
                        } else {
                            if (e.getMessage().equalsIgnoreCase("user email is not verified.")) {
                                getVerificationLink();
                            } else {
                                CommonUtils.showToast(SignInActivity.this, e.getMessage() + "");
                                CommonUtils.hideProgress();
                            }
                        }
                    });
        }
    }

    private void getVerificationLink() {
        Map<String, String> map = new HashMap<>();
        map.put("X-Parse-Application-Id", getResources().getString(R.string.parse_app_id));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Object> call = apiInterface.resendVerificationLink(emailID, map);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                CommonUtils.hideProgress();
                if (response != null) {
                    if (response.body() != null) {
                        CommonUtils.showToast(SignInActivity.this, getString(R.string.verify_link_send_to));
                    } else {
                        CommonUtils.showToast(SignInActivity.this, response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
            }
        });
    }

    private boolean checkValidation() {
        if (TextUtils.isEmpty(emailID)
                && TextUtils.isEmpty(editTextPassword.getText().toString())) {
            editTextEmail.setError(getString(R.string.error_msg_email));
            editTextPassword.setError(getString(R.string.error_msg_pass));
            return false;
        } else if (TextUtils.isEmpty(emailID)) {
            editTextEmail.setError(getString(R.string.error_msg_email));
            return false;
        } else if (TextUtils.isEmpty(editTextPassword.getText().toString())) {
            editTextPassword.setError(getString(R.string.error_msg_pass));
            return false;
        }
        return true;
    }


    private void isKakaoLogin() {
        // 카카오 세션을 오픈한다
        mKakaocallback = new KakaoSessionCallback();
        com.kakao.auth.Session.getCurrentSession().addCallback(mKakaocallback);
        com.kakao.auth.Session.getCurrentSession().checkAndImplicitOpen();
        com.kakao.auth.Session.getCurrentSession().open(AuthType.KAKAO_TALK, SignInActivity.this);
    }

    /**
     * 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
     */
    protected void KakaorequestMe() {
        List<String> keys = new ArrayList<>();
        keys.add("email");
        CommonUtils.showProgress(this);
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                CommonUtils.hideProgress();
                int ErrorCode = errorResult.getErrorCode();
                int ClientErrorCode = -777;

                if (ErrorCode == ClientErrorCode) {
                    Toast.makeText(getApplicationContext(), "카카오톡 서버의 네트워크가 불안정합니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("TAG", "오류로 카카오로그인 실패 ");
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.d("TAG", "오류로 카카오로그인 실패 ");
            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                profileThumbnail = userProfile.getThumbnailImagePath();
                profilePic = userProfile.getProfileImagePath();
                String accessToken = Session.getCurrentSession().getAccessToken();
                userName = userProfile.getNickname();

                Map<String, String> authData = new HashMap<>();
                authData.put("access_token", accessToken);
                authData.put("id", String.valueOf(userProfile.getId()));
                logInWithkakao("kakao", authData);
            }

            @Override
            public void onNotSignedUp() {
                // 자동가입이 아닐경우 동의창
            }
        });
    }


    //kakao login
    private void logInWithAuthType(String authType, Map<String, String> authData) {


        bolts.Task<ParseUser> task = ParseUser.logInWithInBackground(authType, authData);

        task.continueWith((bolts.Task<ParseUser> task1) -> {
            runOnUiThread(CommonUtils::hideProgress);

            try {
                if (task1 != null) {
                    if (task1.getResult() != null) {
                        final ParseUser user = task1.getResult();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                user.setEmail(userEmail);
//                                user.setUsername(userName);
                                if (firstName != null && firstName.length() > 0) {
                                    user.put("name", firstName);
                                    if (lastName != null && lastName.length() > 0)
                                        user.put("surname", lastName);
                                } else if (userName != null && userName.contains(" ")) {
                                    String[] fullName = userName.split(" ");
                                    user.put("name", fullName[0]);
                                    user.put("surname", fullName[1]);
                                }
//                                user.getAuthData().get("google").get("email")
                                user.saveInBackground(e -> {
                                });
                                checkUserAgreement();
                            }
                        });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.error_message), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

            } catch (Exception e) {
                Log.d("EXCEPTION", e.toString());
            }

            return null;
        });
    }

    private void logInWithkakao(String authType, Map<String, String> authData) {


        bolts.Task<ParseUser> task = ParseUser.logInWithInBackground(authType, authData);

        task.continueWith((bolts.Task<ParseUser> task1) -> {
            runOnUiThread(CommonUtils::hideProgress);

            try {
                if (task1 != null) {
                    if (task1.getResult() != null) {
                        final ParseUser user = task1.getResult();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (userName != null)
                                    user.put("name", userName);
                                if (profilePic != null)
                                    user.put("profile", profilePic);
                                if (profileThumbnail != null)
                                    user.put("thumbnail", profileThumbnail);
                                user.saveInBackground(e -> {
                                });
                                checkUserAgreement();
                            }
                        });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.error_message), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

            } catch (Exception e) {
                Log.d("EXCEPTION", e.toString());
            }

            return null;
        });
    }

    private class KakaoSessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            Log.d("TAG", "세션 오픈됨");

            // 사용자 정보를 가져옴, 회원가입 미가입시 자동가입 시킴
            if (!kakaoLoginHit) {
                kakaoLoginHit = true;
                KakaorequestMe();
            }
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Log.d("TAG", exception.getMessage());
            }
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this, LocaleManager.getLanguage(this));
    }

    //Runtime Permissions


    private void getCurrentLocation() {
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        boolean network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

//        Location location;

        if (network_enabled) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_READ_CONTACT);
                return;
            }
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            longitude = (float) location.getLongitude();
                            latitude = (float) location.getLatitude();
                            UserPreferences.getInstance().save(Constant.LATITUDE, latitude);
                            UserPreferences.getInstance().save(Constant.LONGITUDE, longitude);
                        } else {
                            startLocationUpdates();
                        }
                    });
        } else {

            dialog = new LocationDialog(this, this);
            dialog.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACT) {// If request is cancelled, the couponModelResult arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, location -> {
                            if (location != null) {
                                longitude = (float) location.getLongitude();
                                latitude = (float) location.getLatitude();
                                UserPreferences.getInstance().save(Constant.LATITUDE, latitude);
                                UserPreferences.getInstance().save(Constant.LONGITUDE, longitude);
                            } else {
                                startLocationUpdates();
                            }
                        });
            }
        }
    }

    private void startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                null);
    }

    void launchHomeScreen() {
        Intent intent = new Intent(getApplicationContext(), TutorialScreen.class);
        intent.putExtra(Constant.ISSPLASH, true);
        intent.putExtra(Constant.ISLOGINPAGE, true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        SignInActivity.this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        SignInActivity.this.finish();
    }

    void launchTermsScreen() {
        Intent intent = new Intent(getApplicationContext(), UserAgreement.class);
        intent.putExtra(Constant.ISSPLASH, true);
        intent.putExtra(Constant.ISLOGINPAGE, true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        SignInActivity.this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        SignInActivity.this.finish();
    }


    public void checkUserAgreement() {
        String userId = ParseUser.getCurrentUser().getObjectId();
        map.put("user_id", userId);
        ParseCloud.callFunctionInBackground("termsStatus", map, (FunctionCallback<Map>) (objects, e) -> {
            if (e == null) {
                if (objects != null) {
                    isAggreement = (boolean) objects.get("status");
                    if (isAggreement) {
                        launchHomeScreen();
                    } else {
                        launchTermsScreen();
                    }
                }
            } else {
                Toast.makeText(SignInActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public void onAllowClicked() {
        startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 101);
        dialog.dismiss();
    }

    @Override
    public void onDenyClicked() {
        dialog.dismiss();
    }
}
