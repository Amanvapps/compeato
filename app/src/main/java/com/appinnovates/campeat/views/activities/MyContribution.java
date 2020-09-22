package com.appinnovates.campeat.views.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.adapter.ContributionAdapter;
import com.appinnovates.campeat.adapter.MultiSelectedAdapter;
import com.appinnovates.campeat.model.charity.getCharities.Result;
import com.appinnovates.campeat.services.CharityService.CharityInterface;
import com.appinnovates.campeat.services.CharityService.CharityService;
import com.appinnovates.campeat.utils.CommonUtils;
import com.appinnovates.campeat.utils.CustomContributionDialog;
import com.appinnovates.campeat.utils.LocaleManager;
import com.appinnovates.campeat.utils.PermissionsUtil;
import com.appinnovates.campeat.utils.ShareUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class MyContribution extends AppCompatActivity implements CharityInterface, ContributionAdapter.OnCategoryItemInterface, CustomContributionDialog.OnCloseInterface {

    private TextView textViewCount;
    private String myContribution = "1";
    private RecyclerView spinner;
    private List<Result> charityList;
    private TextView textViewHappyPeople;
    private ImageView share;
    private CharityService charityService;
    private ContributionAdapter contributionAdapter;
    private CardView descDialog;
    private ImageView crossButton;
    private ImageView logo;
    private TextView title;
    private TextView desc;
    private TextView link;
    private Button submit;
    private Button submitall;
    ArrayList<String> charatiesId;
    int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contribution);
        initView();
        setCharityService();
        setDonationSpinner();
        getMyDeals();
        getUserCharity();
        descDialog.setVisibility(View.GONE);
        findViewById(R.id.back).setOnClickListener(view -> onBackPressed());
        share.setOnClickListener(view -> {
            ShareUtil.contribution(this);
        });
        crossButton.setOnClickListener(v -> descDialog.setVisibility(View.GONE));
        submit.setOnClickListener(v -> {
            contributionAdapter.setSelected(position);
            submitCharity();
        });
        submitall.setOnClickListener(v -> {
            if (charatiesId.size() != 0)
                submitCharity();
            else
                Toast.makeText(this, "Please select charity", Toast.LENGTH_SHORT).show();
        });
    }

    private void setCharityService() {
        charityService = new CharityService();
        charityService.setDelegateAndContext(this);
        charityService.getCharityList(this);
    }

    private void setHappyPeopleCount(int count) {
        String total = String.valueOf(count * 100);
        String text = getString(R.string.you_contributed, total);
        Spannable spannable = new SpannableString(text);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                if (getResources().getConfiguration().getLocales().toLanguageTags().substring(0, 2).equalsIgnoreCase("en")) {
                    int end = text.length();
                    spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary))
                            , 16
                            , end
                            , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    int end = count + 4;
                    spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary))
                            , 0
                            , end
                            , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                }
            } catch (Exception e) {
                Log.i("Exception ", "Error :- " + e.getMessage());
            }

        }
        textViewHappyPeople.setText(spannable, TextView.BufferType.SPANNABLE);

    }

    private void setDonationSpinner() {
        contributionAdapter = new ContributionAdapter(charityList, this, this);
        spinner.setLayoutManager(new LinearLayoutManager(this));
        spinner.setAdapter(contributionAdapter);
    }

    private void getMyDeals() {
        if (!PermissionsUtil.isNetworkAvailable(this)) {
            CommonUtils.showToast(this, getString(R.string.no_internet_available));
            return;
        }
        CommonUtils.showProgress(this);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("BranchBooking");
        query.whereEqualTo("customer_id", ParseUser.getCurrentUser());
        query.whereEqualTo("has_fulfilled_yn", "Y");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                CommonUtils.hideProgress();
                if (e == null) {
                    myContribution = String.valueOf(objects.size());
                }
                int contributionCount = Integer.valueOf(myContribution);
                textViewCount.setText("" + contributionCount * 100);
                textViewCount.setVisibility(View.VISIBLE);
                setHappyPeopleCount(contributionCount);

            }
        });

    }

    private void initView() {
        charatiesId = new ArrayList<>();
        charityList = new ArrayList<>();
        textViewCount = findViewById(R.id.text_view_number);
        textViewHappyPeople = findViewById(R.id.text_view_count_people_happy);
        spinner = findViewById(R.id.recyclerview);
        share = findViewById(R.id.imageView13);
        descDialog = findViewById(R.id.desc_dialog);
        crossButton = findViewById(R.id.cross);
        title = findViewById(R.id.txt_title);
        desc = findViewById(R.id.agreement_details);
        logo = findViewById(R.id.imageView17);
        link = findViewById(R.id.charity_link);
        submit = findViewById(R.id.btn_submit);
        submitall = findViewById(R.id.button3);
    }

    @Override
    public void onBackPressed() {
        if (descDialog.getVisibility() == View.VISIBLE) {
            descDialog.setVisibility(View.GONE);
        } else
            super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this, LocaleManager.getLanguage(this));
    }

    @Override
    public void onSuccess(List<Result> charityModelList) {
        charityList.clear();
        Result model = new Result();
        model.setCharityName("All Charities");
        charityList.add(model);
        charityList.addAll(charityModelList);
        contributionAdapter.notifyDataSetChanged();
        getUserCharity();
    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public void onCategoryItemClick(int position, Result submitResult, String charityIds) {
/*        CustomContributionDialog dialog = CustomContributionDialog.getInstance(submitResult, position);
        dialog.show(getSupportFragmentManager(), "this");*/
        charatiesId.clear();
        charatiesId.add(charityIds);
        descDialog.setVisibility(View.VISIBLE);
        setData(submitResult);
        this.position = position;
    }

    @Override
    public void getAllIds(ArrayList<String> allCharityIds) {
        charatiesId.clear();
        charatiesId.addAll(allCharityIds);
    }

    @Override
    public void onItemClicked(ArrayList<String> charityIds) {
        charatiesId.clear();
        charatiesId.addAll(charityIds);
    }

    @Override
    public void onClose() {
        Intent intent = new Intent(this, HomePage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void setData(Result charity) {
        if (charity != null) {
            title.setText(charity.getCharityName().trim());
            link.setText(charity.getLink());
            desc.setText(charity.getDescription());
            if (charity.getLogo() != null)
                Glide.with(this)
                        .load(charity.getLogo().getUrl())
                        .override(300, 250)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                // log exception
                                Log.e("TAG", "Error loading image", e);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(logo);
        }
    }

    void submitCharity() {
        Map<String, Object> ids = new HashMap<>();
        ids.put("charities", charatiesId);
        ParseCloud.callFunctionInBackground("subscribeToCharity", ids, (FunctionCallback<Map>) (objects, e) -> {
            if (e == null) {
                if (objects != null && (Boolean) objects.get("success")) {
                    showSuccessDialog();
                }
            } else {
                Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void showSuccessDialog() {
        CustomContributionDialog dialog = CustomContributionDialog.getInstance(this);
        dialog.show(getSupportFragmentManager(), "this");
    }

    void getUserCharity() {
        ParseCloud.callFunctionInBackground("getUserCharity", new HashMap<>(), (FunctionCallback<List<Object>>) (objects, e) -> {
            if (e == null) {
                ArrayList<String> objectId=new ArrayList<>();
                if (objects != null) {
                    for (int i=0 ; i<objects.size();i++){
                        objectId.add(((HashMap) objects.get(i)).get("objectId").toString());
                    }
                    contributionAdapter.setObjectId(objectId);
                }
            } else
                Log.i("Error","Error:-"+e.getMessage());
        });
    }
}

