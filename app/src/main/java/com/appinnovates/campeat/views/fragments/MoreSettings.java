package com.appinnovates.campeat.views.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.appinnovates.campeat.MyReceipts;
import com.appinnovates.campeat.R;
import com.appinnovates.campeat.RedeemCodeFragment;
import com.appinnovates.campeat.bottomSheets.BottomSheetLanguage;
import com.appinnovates.campeat.utils.CommonUtils;
import com.appinnovates.campeat.views.activities.ContactActivity;
import com.appinnovates.campeat.views.activities.HomePage;
import com.appinnovates.campeat.views.activities.MyWalletActivity;
import com.appinnovates.campeat.views.activities.SignInActivity;
import com.appinnovates.campeat.views.activities.TutorialScreen;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseUser;

import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoreSettings extends Fragment implements BottomSheetLanguage.OnLanguageInterface {


    private Context context;
    private int flag = 1;
    private String currentLang;
    private LinearLayout mywallet, howToUse, linearLayout, redeemCode, logout, contact, receipt;
    private TextView name;
    private CircleImageView profile;
    private boolean status;
    private SwitchCompat switchCompat;

    public MoreSettings() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_settings, container, false);
        initViews(view);
        setListeners();
//        getNotificationStatus();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String[] langCodes = getResources().getConfiguration().getLocales().toLanguageTags().split("-");
            currentLang = langCodes[0];
        }
        if (ParseUser.getCurrentUser().get("name") != null) {
            name.setText(ParseUser.getCurrentUser().get("name").toString());
        }
        if (ParseUser.getCurrentUser() != null) {
            Glide.with(context)
                    .load(ParseUser.getCurrentUser().get("thumbnail"))
                    .error(R.drawable.my_profile)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false; // important to return false so the error placeholder can be placed
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(profile);
        }

//        mywallet.setOnClickListener(view1 -> startActivity(new Intent(getActivity(), MyWalletActivity.class)));
        howToUse.setOnClickListener(v -> startActivity(new Intent(getActivity(), TutorialScreen.class).putExtra("isHowToUse", true)));

        LinearLayout logout = view.findViewById(R.id.logout_button);
        logout.setOnClickListener(view1 -> {
            ParseUser.logOut();
            signOut();
            kakaoSignOut();
            CommonUtils.showToast(getActivity(), getString(R.string.successfully_logout));
            Intent i = new Intent(getActivity(), SignInActivity.class);
            getActivity().finish();
            startActivity(i);
        });
        linearLayout.setOnClickListener(view1 -> {
            if (currentLang.equalsIgnoreCase("en")) {
                flag = 1;
            } else {
                flag = 2;
            }
            BottomSheetLanguage bottomSheet = new BottomSheetLanguage(context, this, flag);
            assert getFragmentManager() != null;
            bottomSheet.show(getFragmentManager(), "bottomSheetlang");

        });
        redeemCode.setOnClickListener(v -> {
            if (getFragmentManager() != null) {
                getFragmentManager().beginTransaction().add(R.id.fragment_container, new RedeemCodeFragment(), "RedeemCodeFragment").addToBackStack("").commit();
            }
        });
        return view;
    }

    private void signOut() {
        // Firebase sign out
        try {
            FirebaseAuth.getInstance().signOut();
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
            mGoogleSignInClient.signOut();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void kakaoSignOut() {
        try {
            UserManagement.requestUnlink(new UnLinkResponseCallback() {
                @Override
                public void onSessionClosed(ErrorResult errorResult) {

                }

                @Override
                public void onNotSignedUp() {

                }

                @Override
                public void onSuccess(Long result) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnglishClick(String english) {
        flag = 1;
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(english.toLowerCase())); // API 17+ only.
        res.updateConfiguration(conf, dm);
        startActivity(new Intent(getActivity(), HomePage.class));
        getActivity().finish();
    }

    @Override
    public void onKoreanClick(String korean) {
        flag = 2;
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(korean.toLowerCase())); // API 17+ only.
        res.updateConfiguration(conf, dm);
        startActivity(new Intent(getActivity(), HomePage.class));
        getActivity().finish();


    }

    private void initViews(View view) {
        switchCompat = view.findViewById(R.id.switch1);
        receipt = view.findViewById(R.id.receipts);
        name = view.findViewById(R.id.name);
        profile = view.findViewById(R.id.circleImageView);
        //mywallet = view.findViewById(R.id.my_wallet);
        howToUse = view.findViewById(R.id.how_to_use);
        linearLayout = view.findViewById(R.id.linearLayout_more);
        redeemCode = view.findViewById(R.id.radeem_code);
        logout = view.findViewById(R.id.logout_button);
        contact = view.findViewById(R.id.contact_us);
    }

    private void setListeners() {
        receipt.setOnClickListener(v -> {
            if (getFragmentManager() != null) {
                getFragmentManager().beginTransaction().add(R.id.home_activity, new MyReceipts(), "Receipt").addToBackStack("this").commit();
            }
        });
        contact.setOnClickListener(view1 -> startActivity(new Intent(getActivity(), ContactActivity.class)));
        logout.setOnClickListener(view1 -> {
            ParseUser.logOut();
            signOut();
            kakaoSignOut();
            CommonUtils.showToast(getActivity(), getString(R.string.successfully_logout));
            Intent i = new Intent(getActivity(), SignInActivity.class);
            getActivity().finish();
            startActivity(i);
        });
        linearLayout.setOnClickListener(view1 -> {
            if (currentLang.equalsIgnoreCase("en")) {
                flag = 1;
            } else {
                flag = 2;
            }
            BottomSheetLanguage bottomSheet = new BottomSheetLanguage(context, this, flag);
            assert getFragmentManager() != null;
            bottomSheet.show(getFragmentManager(), "bottomSheetlang");
        });
        redeemCode.setOnClickListener(v -> {
            if (getFragmentManager() != null) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new RedeemCodeFragment()).addToBackStack("").commit();
            }
        });
//        mywallet.setOnClickListener(view1 -> startActivity(new Intent(getActivity(), MyWalletActivity.class)));
        howToUse.setOnClickListener(v -> startActivity(new Intent(getActivity(), TutorialScreen.class).putExtra("isHowToUse", true)));
        switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.i("check", "setListeners: " + isChecked);
        });
    }

/*    private void getNotificationStatus() {
        ParseCloud.callFunctionInBackground("getNotificationStatus", new HashMap<>(), (FunctionCallback<Boolean>) (objects, e) -> {
            if (e == null) {
                switchCompat.setChecked(objects);
            }
        });
    }*/

    private void setNotificationStatus() {
        ParseCloud.callFunctionInBackground("updateNotificationStatus", new HashMap<>(), (FunctionCallback<Boolean>) (objects, e) -> {
            if (e == null) {
                if (objects != null) {

                }
            }
        });
    }
}
