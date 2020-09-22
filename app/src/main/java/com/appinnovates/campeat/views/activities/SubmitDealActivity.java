package com.appinnovates.campeat.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.Toolbar;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.BookingDealModel;
import com.appinnovates.campeat.model.HomeBranchDeals.BranchDealModel;
import com.appinnovates.campeat.model.RedeemCoupon.Result;
import com.appinnovates.campeat.model.ReviewModel;
import com.appinnovates.campeat.model.getAllDealsModel.BranchModel;
import com.appinnovates.campeat.model.getAllDealsModel.DealModel;
import com.appinnovates.campeat.services.ReviewService.ReviewService;
import com.appinnovates.campeat.services.ReviewService.ReviewServiceInterface;
import com.appinnovates.campeat.utils.AnalyticUtil;
import com.appinnovates.campeat.utils.CommonUtils;
import com.appinnovates.campeat.utils.Constant;
import com.appinnovates.campeat.utils.DateFormatUtil;
import com.appinnovates.campeat.utils.LocaleManager;
import com.appinnovates.campeat.utils.PermissionsUtil;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

import static com.appinnovates.campeat.views.activities.CouponMenu.couponModelResult;

public class SubmitDealActivity extends Activity implements View.OnClickListener, ReviewServiceInterface {
    private Button relativeLayoutSubmit;
    private BookingDealModel bookingDealModel;
    private AppCompatRatingBar ratingBar;
    private TextView textViewDealName;
    private EditText editTextComment;
//    private Result result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_deal);
        Toolbar toolbar = findViewById(R.id.toolbar_item_details);
        toolbar.setNavigationIcon(R.drawable.arrow_back_black);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
//        if (couponModelResult != null)
//            result = couponModelResult;


        initField();
        initView();
        setListener();
        setService();
        setData();

    }

    private void setService() {
        ReviewService reviewService = ReviewService.getInstance();
        reviewService.setDelegate(this, this);
    }

    private void setData() {
        if (bookingDealModel != null) {
            String discountRate;
            DealModel dealsModel = bookingDealModel.getBranchDealModel().getDeal();
            if (dealsModel.getType() != null && dealsModel.getType().equalsIgnoreCase(Constant.STANDARD)) {
                discountRate = dealsModel.getDiscountRate() != null ? dealsModel.getDiscountRate().toString() + "%" + " " + getString(R.string.discount) : "0" + "%" + " " + getString(R.string.discount);
            } else {
                String minLimit = "0";
                if (dealsModel.getMinPersonRequired() != null) {
                    if (!dealsModel.getMinPersonRequired().equals("null")) {
                        minLimit = dealsModel.getMinPersonRequired();
                    }
                }
                if (bookingDealModel.getNo_of_people().length() > 0
                        && Integer.parseInt(bookingDealModel.getNo_of_people()) > Integer.parseInt(minLimit)) {
                    discountRate = dealsModel.getGroupDiscountRate().toString() + " " + "%" + " " + getString(R.string.discount);
                } else {
                    discountRate = dealsModel.getGroupDiscountRate().toString() != null ? dealsModel.getGroupDiscountRate().toString() + "%" + " " + getString(R.string.discount) : "0" + "%" + " " + getString(R.string.discount);
                }
            }

            String name;
            BranchDealModel branchModel = bookingDealModel.getBranchDealModel();
            if (branchModel.getBranch().getRestaurantId() != null) {
                name = branchModel.getBranch().getRestaurantId().getRestaurantName() + " - " + branchModel.getBranch().getBranchName() + "\n" + discountRate;
            } else {
                name = branchModel.getBranch().getBranchName() + "\n" + discountRate;
            }

            textViewDealName.setText(name);
            if (!PermissionsUtil.isNetworkAvailable(this)) {
                CommonUtils.showToast(this, getString(R.string.no_internet_available));
                return;
            }
        }

//        else if (result != null) {
//            String discountRate;
//            String name;
//            if (result.getType() == 1) {
//                discountRate = result.getValue().toString() + getString(R.string.off);
//            } else {
//                discountRate = getString(R.string.won_symbol) + result.getValue().toString() + " " + getString(R.string.won);
//            }
//            BranchModel branchModel = result.getBranch();
//            if (branchModel.getRestaurantId() != null) {
//                name = branchModel.getRestaurantId().getRestaurantName() + " - " + branchModel.getBranchName() + "\n" + discountRate;
//            } else {
//                name = branchModel.getBranchName() + "\n" + discountRate;
//            }
//            textViewDealName.setText(name);
//        }


    }

    private void initField() {
        bookingDealModel = getIntent().getParcelableExtra(Constant.BookingDealModel);
    }

    private void setListener() {
        relativeLayoutSubmit.setOnClickListener(this);
    }

    private void initView() {
        relativeLayoutSubmit = findViewById(R.id.relative_layout_submit);
        textViewDealName = findViewById(R.id.text_view_deal_name);
        editTextComment = findViewById(R.id.edit_text_comment);
//        textViewDate = findViewById(R.id.text_view_date);
        ratingBar = findViewById(R.id.rating);
        ratingBar.setIsIndicator(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToMyDealsActivity();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.relative_layout_submit:
                if (!PermissionsUtil.isNetworkAvailable(this)) {
                    CommonUtils.showToast(this, getString(R.string.no_internet_available));
                    return;
                }
                submitReview();
                break;
        }
    }

    private void submitReview() {

        if (TextUtils.isEmpty(editTextComment.getText().toString())) {
            CommonUtils.showToast(this, getString(R.string.please_comment));
            return;
        }
        CommonUtils.showProgress(this);
        ParseObject object = new ParseObject("RestaurantReview");
        if (bookingDealModel != null) {
            AnalyticUtil.setCommentsEvent(this, bookingDealModel.getBranchDealModel().getBranch().getRestaurantId().getObjectId());
            object.put("deal_id", bookingDealModel.getBranchDealModel().getDeal().getDealPointer());
            object.put("branch_id", bookingDealModel.getBranchDealModel().getBranch().getBranchPointer());
        }
//        else if (result != null) {
//            AnalyticUtil.setCommentsEvent(this, result.getBranch().getRestaurantId().getObjectId());
//            object.put("branch_id", result.getBranch().getBranchPointer());
//            if (result.getCouponPointer() != null)
//                object.put("coupon_id", result.getCouponPointer());
//        }
        object.put("customer_id", ParseUser.getCurrentUser());
        object.put("review_point", (int) ratingBar.getRating());
        object.put("review", editTextComment.getText().toString());
        object.put("review_date", DateFormatUtil.getCurrentDate());
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    CommonUtils.hideProgress();
                    goToMyDealsActivity();
                } else {
                    CommonUtils.hideProgress();
                    CommonUtils.showToast(SubmitDealActivity.this, e.getMessage() + "");
                }
            }
        });
    }

    private void goToMyDealsActivity() {
        Intent intent = new Intent(getApplicationContext(), HomePage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (bookingDealModel != null) {
            intent.putExtra(Constant.DEAL, Constant.DEAL);
            startActivity(intent);
        }
//        else if (result != null) {
//            intent.putExtra(Constant.FREE_COUPON, Constant.FREE_COUPON);
//            startActivity(intent);
//        }
    }

    @Override
    public void onReviewsSuccess(List<ReviewModel> reviewList) {
        if (reviewList.get(0) != null) {
            editTextComment.setText(reviewList.get(0).getReview());
            ratingBar.setRating(reviewList.get(0).getReview_point());
        }
    }

    @Override
    public void onNoReviews() {

    }

    @Override
    public void onReviewsFailure(String message) {

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this, LocaleManager.getLanguage(this));
    }
}
