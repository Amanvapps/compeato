package com.appinnovates.campeat.views.activities;

import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.bottomSheets.BottomSheetCancelDeals;
import com.appinnovates.campeat.bottomSheets.BottomSheetDeals;
import com.appinnovates.campeat.bottomSheets.BottomSheetEarnPoints;
import com.appinnovates.campeat.bottomSheets.BottomSheetFewDealsLeft;
import com.appinnovates.campeat.bottomSheets.BottomSheetFilter;
import com.appinnovates.campeat.bottomSheets.BottomSheetFilterSubscribe;
import com.appinnovates.campeat.bottomSheets.BottomSheetLanguage;
import com.appinnovates.campeat.bottomSheets.BottomSheetSelectionCategories;
import com.appinnovates.campeat.model.HomeBranchDeals.HomeBranchDealsModel;
import com.appinnovates.campeat.utils.CommonUtils;
import com.appinnovates.campeat.utils.Constant;
import com.appinnovates.campeat.utils.LocaleManager;
import com.appinnovates.campeat.views.fragments.FabTasks;
import com.appinnovates.campeat.views.fragments.HomeContainer;
import com.appinnovates.campeat.views.fragments.MoreSettings;
import com.appinnovates.campeat.views.fragments.MyDeals;
import com.appinnovates.campeat.views.fragments.Subscriptions;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;
import static com.crashlytics.android.Crashlytics.log;


public class HomePage extends AppCompatActivity implements BottomSheetSelectionCategories.BottomSheetListener, BottomSheetDeals.BottomSheetListener, BottomSheetFilter.BottomSheetListener,
        BottomSheetFilterSubscribe.BottomSheetListener, BottomSheetLanguage.BottomSheetListener, BottomSheetCancelDeals.BottomSheetListener, BottomSheetFewDealsLeft.BottomSheetListener, BottomSheetEarnPoints.BottomSheetListener {

    int count = 1;
    @BindView(R.id.divider10) View v1;
    @BindView(R.id.divider11)View v2;
    @BindView(R.id.divider12)View v3;
    @BindView(R.id.divider13)View v4;
    @BindView(R.id.divider14)View v5;
    @BindView(R.id.floating_button) FloatingActionButton floatingActionButton;
    private Animation forward;
    private Animation backward;
    @BindView(R.id.navigation) BottomNavigationView navigation;
    private Boolean isClicked = false;
    String FRAGMENT_HOME = "home";
    String FRAGMENT_OTHER = "other";
    String noDeals;
    boolean isSplash;
    boolean isLogin;
    private int REQUEST_CODE = 111;
    private AppUpdateManager appUpdateManager;

    //r:e9356afa8397673d914c8961bb741082

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
//                Toast.makeText(HomePage.this, initializationStatus.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        CommonUtils.hideKeyboard(this);
        checkUpdates();
        forward = AnimationUtils.loadAnimation(this, R.anim.open_fab);
        backward = AnimationUtils.loadAnimation(this, R.anim.close_fab);
        List<HomeBranchDealsModel> list = getIntent().getParcelableArrayListExtra("data");
        isSplash = getIntent().getBooleanExtra(Constant.ISSPLASH, false);
        isLogin = getIntent().getBooleanExtra(Constant.ISLOGINPAGE, false);
        noDeals = getIntent().getStringExtra(Constant.NODEALS);
        v1.setBackgroundResource(R.color.colorAccent);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        checkUpdates();
        if (Constant.DEAL.equals(getIntent().getStringExtra(Constant.DEAL))) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new MyDeals()).commit();
            navigation.setSelectedItemId(R.id.cart);
        } else {
            Bundle bundle = new Bundle();
//            bundle.putParcelableArrayList("data", (ArrayList<? extends Parcelable>) list);
            bundle.putString(Constant.NODEALS, noDeals);
            bundle.putBoolean(Constant.ISSPLASH, isSplash);
            bundle.putBoolean(Constant.ISLOGINPAGE, isLogin);
            isSplash = false;
            isLogin = false;
            HomeContainer fragment = new HomeContainer();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

        }
        floatingActionButton.setOnClickListener(view -> {

            /*FabTasks test = (FabTasks) getSupportFragmentManager().findFragmentByTag("testID");
            if (test != null && test.isVisible()) {
                onBackPressed();
            }*/
//            setActiveInActiveBG(v3,v1,v5,v4,v2);
            if (count == 1) {
                getSupportFragmentManager().beginTransaction().add(R.id.home_activity, new FabTasks()).addToBackStack("fab").commit();
                floatingActionButton.startAnimation(backward);
                navigation.setOnNavigationItemSelectedListener(null);
                count++;
                isClicked = true;
            } else {
                onBackPressed();
            }
        });

        new BottomSheetFilter(this);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> instanceIdResultTask) {
                        instanceIdResultTask.isSuccessful();

                    }
                });
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.home:
                viewFragment(new HomeContainer(), FRAGMENT_HOME);
                setActiveInActiveBG(v1, v2, v3, v4, v5);
                return true;
            case R.id.cart:
                viewFragment(new MyDeals(), FRAGMENT_OTHER);
                setActiveInActiveBG(v2, v1, v3, v4, v5);
                return true;
            case R.id.search:
                viewFragment(new Subscriptions(), FRAGMENT_OTHER);
                setActiveInActiveBG(v4, v1, v3, v2, v5);
                return true;
            case R.id.profile:
                viewFragment(new MoreSettings(), FRAGMENT_OTHER);
                setActiveInActiveBG(v5, v1, v3, v4, v2);
                return true;
        }
        return false;
    };


    @Override
    public void onButtonSelected(String text) {
    }

    @Override
    public void onFilter(String text) {
    }

    @Override
    public void onFilterSubscribe(String text) {
    }

    @Override
    public void onButtonLanguage(String text) {
    }

    @Override
    public void onButtonCancelDeal(String text) {
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    private void setActiveInActiveBG(View active, View inactive1, View inactive2, View inactive3, View inactive4) {
        active.setBackgroundResource(R.color.colorAccent);
        inactive1.setBackgroundResource(R.color.navigation_icon_color);
        inactive2.setBackgroundResource(R.color.navigation_icon_color);
        inactive3.setBackgroundResource(R.color.navigation_icon_color);
        inactive4.setBackgroundResource(R.color.navigation_icon_color);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this, LocaleManager.getLanguage(this));
    }

    @Override
    public void onBackPressed() {
        if (isClicked) {
            super.onBackPressed();
            floatingActionButton.startAnimation(forward);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            count--;
            isClicked = false;
        } else {
            super.onBackPressed();
        }
    }


    private void viewFragment(Fragment fragment, String name) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        final int count = fragmentManager.getBackStackEntryCount();
        if (name.equals(FRAGMENT_OTHER)) {
            fragmentTransaction.addToBackStack(name);
        }
        fragmentTransaction.commit();
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (fragmentManager.getBackStackEntryCount() <= count) {
                    fragmentManager.popBackStack(FRAGMENT_OTHER, POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.removeOnBackStackChangedListener(this);
                    navigation.getMenu().getItem(0).setChecked(true);
                    setActiveInActiveBG(v1, v2, v3, v4, v5);
                }
            }
        });
    }

    void checkUpdates() {
        appUpdateManager = AppUpdateManagerFactory.create(this);
        appUpdateManager.registerListener(listener);
        com.google.android.play.core.tasks.Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                try {
                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, this, REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    Log.i("Exception", "Error :- " + e.getMessage());
                }
            }
        });

    }

    public InstallStateUpdatedListener listener = state -> {
        // Show module progress, log state, or install the update.
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            popupSnackbarForCompleteUpdate();
        } else {
            Log.i("Tag", "InstallStateUpdatedListener: state: " + state.installStatus());
        }
    };

    private void popupSnackbarForCompleteUpdate() {
        appUpdateManager.unregisterListener(listener);
        Snackbar snackbar =
                Snackbar.make(
                        findViewById(R.id.home_activity),
                        "An update has just been downloaded.",
                        Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("RESTART", view -> appUpdateManager.completeUpdate());
        snackbar.setActionTextColor(
                getResources().getColor(R.color.white_color));
        snackbar.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                log("Update flow failed! Result code: " + resultCode);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(appUpdateInfo -> {
                    if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                        popupSnackbarForCompleteUpdate();
                    }
                });
    }


}
