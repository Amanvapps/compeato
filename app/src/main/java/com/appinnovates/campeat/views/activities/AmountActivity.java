package com.appinnovates.campeat.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.BookingDealModel;
import com.appinnovates.campeat.model.getAllDealsModel.DealModel;
import com.appinnovates.campeat.services.AdService.AdService;
import com.appinnovates.campeat.services.AdService.AddPointsDelegate;
import com.appinnovates.campeat.services.AdService.PointService;
import com.appinnovates.campeat.services.AdService.PointsDelegate;
import com.appinnovates.campeat.services.AdService.SettingType;
import com.appinnovates.campeat.services.AdService.TADPEntry;
import com.appinnovates.campeat.utils.CommonUtils;
import com.appinnovates.campeat.utils.Constant;
import com.appinnovates.campeat.utils.DateFormatUtil;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import static com.appinnovates.campeat.views.activities.CouponMenu.couponModelResult;

public class AmountActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextAmount;
    private String qrData = "";
    private Button buttonDiscount;
    private LinearLayout linearLayoutPayment;
    private Button btnEasyPay;
    private Button buttonPayCounter;
    private boolean paymentScreen = false;
    private TextView textView;
    private String userEnterAmount;
    private BookingDealModel bookingDealModel;
    private String finalAmount;
    boolean isFreeCoupon;
    boolean availFreeCoupon;
    private CardView congrats_layout;
    private TextView txtTitle, txtMiddle;
    private int index = 0, tadPoints = 0;
    private String IS_TAD_PAYMENT, TAD_LIMIT;
    private TextView txtmessage;
    private CardView badLuckLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back_black);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        initView();
        setData();
        setListener();
    }

    private void initView() {
        badLuckLayout = findViewById(R.id.badluck_layout);
        textView = findViewById(R.id.text_view_amount);
        editTextAmount = findViewById(R.id.edit_text_amount);
        buttonDiscount = findViewById(R.id.button_discount);
        btnEasyPay = findViewById(R.id.btn_easy_pay);
        buttonPayCounter = findViewById(R.id.button_pay_counter);
        linearLayoutPayment = findViewById(R.id.linear_layout_payment);
        congrats_layout = findViewById(R.id.congrats_layout);
        txtTitle = findViewById(R.id.txt_title);
        txtMiddle = findViewById(R.id.txt_middle);
        txtmessage = findViewById(R.id.txt_message_bad);
        editTextAmount.setSelection(1);
    }

    private void setData() {


        new PointService().points(new PointsDelegate() {
            @Override
            public void onSuccess(ArrayList<TADPEntry> tadpEntries, ArrayList<TADPEntry> tadEntries, int totalTADPPoints, int totalTADPoints) {
                tadPoints = totalTADPoints;
            }

            @Override
            public void onFailure(String message) {

            }
        });

        if (getIntent() != null) {
            bookingDealModel = getIntent().getParcelableExtra(Constant.BookingDealModel);
            qrData = getIntent().getStringExtra(Constant.CODE);
            isFreeCoupon = getIntent().getBooleanExtra("isFreeCoupon", false);
            availFreeCoupon = getIntent().getBooleanExtra("availFreeCoupon", false);
            index = getIntent().getIntExtra("index", 0);

            if (bookingDealModel == null) {
                return;
            }


            if (isFreeCoupon) {
                if (availFreeCoupon) {
                    congrats_layout.setVisibility(View.VISIBLE);
                    txtMiddle.setText(" ₩" + bookingDealModel.getBranchDealModel().getDeal().getFreeCouponDiscount() + " ");
                } else {
                    badLuckLayout.setVisibility(View.VISIBLE);
                    txtmessage.setText(Html.fromHtml(getResources().getString(R.string.you_didn_t_win_free_coupon_but_you_still_have_15_discount) + "<font color=#FF6E1E>" + " " + bookingDealModel.getBranchDealModel().getDeal().getDiscountRate() + "%" + getString(R.string.discount) + "</font><br><br>"));
                }

                if (availFreeCoupon) {
                    ParseObject object = bookingDealModel.getBranchDealModel().getDeal().getDealPointer();
                    List<Integer> indexes = new ArrayList<Integer>();
                    indexes.add(index);
                    if (object != null) {
                        object.addAllUnique("used_indexes", indexes);
                    }

                    if (object != null) {
                        object.saveInBackground(e -> {
                            if (e != null) {
                                Log.v(e.getMessage(), "");
                            }
                        });
                    }
                }
            }
        }
    }

    private void setListener() {
        buttonDiscount.setOnClickListener(this);
        buttonPayCounter.setOnClickListener(this);
        editTextAmount.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (buttonDiscount.getVisibility() == View.VISIBLE) {
                    buttonDiscount.performClick();
                }
            }
            return false;
        });

        btnEasyPay.setOnClickListener(view -> tadPayment());
    }

    private void tadPayment() {
        CommonUtils.showProgress(this);
        AdService.instance.getSettings(settings -> {
            IS_TAD_PAYMENT = settings.get(SettingType.IS_TAD_PAYMENT);
            TAD_LIMIT = settings.get(SettingType.TAD_LIMIT);
            String TAD_PER_KR = settings.get(SettingType.TAD_PER_KR);
            double TADVAlue = 0;
            if (TAD_PER_KR != null) {
                TADVAlue = Double.parseDouble(TAD_PER_KR);
            }

            if (IS_TAD_PAYMENT.equalsIgnoreCase("FAlSE")) {
                CommonUtils.hideProgress();
                CommonUtils.showToast(AmountActivity.this, getResources().getString(R.string.tad_disabled));
                return;
            }

            Float points = 0f;
            if (editTextAmount.getText() != null) {
                points = Float.parseFloat(editTextAmount.getText().toString().replace("₩", ""));
            }

            Float limit = Float.parseFloat(TAD_LIMIT);

            if (points > limit) {
                CommonUtils.hideProgress();
                CommonUtils.showToast(AmountActivity.this, getResources().getString(R.string.tad_limit));
                return;
            }

            double tadAmount = points / TADVAlue;

            if (tadAmount > tadPoints) {
                CommonUtils.hideProgress();
                CommonUtils.showToast(AmountActivity.this, getResources().getString(R.string.not_enough_tad_points));
                return;
            }

            new PointService().addPoints(-points, null, bookingDealModel.getBranchDealModel().getBranch().getBranchPointer(), null, true, new AddPointsDelegate() {
                @Override
                public void success() {
                    updateFulFillStatus();
                }

                @Override
                public void failure(String message) {
                    CommonUtils.hideProgress();
                    CommonUtils.showToast(AmountActivity.this, message);
                }
            });

        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                if (paymentScreen) {
                    goAmountScreen();
                } else {
                    onBackPressed();
                }
                break;
            case R.id.button_pay_counter:
                addPaymentEntry();
                break;
        }
    }

    private void addPaymentEntry() {
//        if (bookingDealModel != null && bookingDealModel.getBranchDealModel() != null) {
        int noOfGuest = 0;
        float amountRate = 0f;
        if (editTextAmount.getText().toString().length() < 2 || editTextAmount.getText() == null) {
            editTextAmount.setError(getResources().getString(R.string.enter_amount_please));
        } else {
            CommonUtils.hideKeyboard(this);
            amountRate = Float.parseFloat(editTextAmount.getText().toString().replace("₩", ""));
            ParseUser parseUser = ParseUser.getCurrentUser();
            String name = (String) parseUser.get("name");
            ParseObject parseObject = new ParseObject("Payment");
            if (name != null)
                parseObject.put("customer_name", name);
            if (bookingDealModel != null) {
                if (bookingDealModel.getNo_of_people() != null) {
                    noOfGuest = Integer.parseInt(bookingDealModel.getNo_of_people());
                }
                parseObject.put("branch_id", bookingDealModel.getBranchDealModel().getBranch().getBranchPointer());
                parseObject.put("booking_id", bookingDealModel.getBookingPointer());
            } else {
                parseObject.put("branch_id", couponModelResult.getBranch().getBranchPointer());
                parseObject.put("coupon_id", couponModelResult.getCouponPointer());
            }
            parseObject.put("guest", noOfGuest);
            parseObject.put("amount", amountRate);
            parseObject.put("status", "At counter");
            parseObject.put("customer_id", parseUser);
            parseObject.put("time", DateFormatUtil.getCurrentDate());
            parseObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        updateFulFillStatus();
                    } else {
                        buttonPayCounter.setEnabled(true);
                        CommonUtils.showToast(AmountActivity.this, e.getMessage());
                    }
                }
            });
        }
    }

    private void updateFulFillStatus() {
        if (bookingDealModel != null) {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("BranchBooking");
            query.getInBackground(bookingDealModel.getId(), new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject booking, ParseException e) {
                    buttonPayCounter.setEnabled(true);
                    if (e == null) {
                        booking.put("has_fulfilled_yn", "Y");
                        booking.put("fulfillment_date", DateFormatUtil.getCurrentDate());
                        booking.saveInBackground();
                        goToReviewScreen();
                    } else {
                        CommonUtils.showToast(AmountActivity.this, e.getMessage());
                    }
                }
            });
        } else {
            goToReviewScreen();
        }
    }

    private void goToReviewScreen() {
        Intent intent = new Intent(this, SubmitDealActivity.class);
        if (bookingDealModel != null)
            intent.putExtra(Constant.BookingDealModel, bookingDealModel);
        startActivity(intent);
    }

    private void calculateDiscount() {
        DealModel dealModel = null;
        if (bookingDealModel != null && bookingDealModel.getBranchDealModel() != null) {
            dealModel = bookingDealModel.getBranchDealModel().getDeal();
        }
        String discountRate = "0";
        String minLimit = "0";
        float discountMoney = 0;
        float finalMoney = 0;
        String amountRate = editTextAmount.getText().toString().replace("₩", "");
        if (dealModel != null) {
            String type = dealModel.getType();
            discountRate = String.valueOf(dealModel.getDiscountRate());
            if (type != null) {
                switch (type) {
                    case Constant.GROUP:
                        if (dealModel.getMinPersonRequired() != null) {
                            if (!dealModel.getMinPersonRequired().equals("null")) {
                                minLimit = dealModel.getMinPersonRequired();
                            }
                        }
                        String noOfGuest = bookingDealModel.getNo_of_people();
                        if (noOfGuest.length() > 0 && Integer.parseInt(noOfGuest) > Integer.parseInt(minLimit)) {
                            discountRate = String.valueOf(dealModel.getGroupDiscountRate());
                        }
                        discountMoney = (Float.parseFloat(amountRate) * Float.parseFloat(discountRate)) / 100;
                        break;
                    case "free_coupon":
                        discountMoney = availFreeCoupon ? dealModel.getFreeCouponDiscount() : (Float.parseFloat(amountRate) * Float.parseFloat(discountRate)) / 100;
                        break;
                    default:
                        discountMoney = (Float.parseFloat(amountRate) * Float.parseFloat(discountRate)) / 100;
                        break;
                }
            }
        }
        finalMoney = Float.parseFloat(amountRate) - discountMoney;
        finalMoney = finalMoney < 0 ? 0 : finalMoney;
        finalAmount = "₩" + String.valueOf(finalMoney);
        goPaymentScreen();
        buttonDiscount.setVisibility(View.GONE);
        buttonPayCounter.setVisibility(View.VISIBLE);

    }

    private void goAmountScreen() {
        congrats_layout.setVisibility(isFreeCoupon ? View.VISIBLE : View.GONE);
        paymentScreen = false;
        linearLayoutPayment.setVisibility(View.GONE);
        buttonDiscount.setVisibility(View.VISIBLE);
        textView.setText(getString(R.string.enter_amount));
        editTextAmount.setText(userEnterAmount);
        editTextAmount.setEnabled(true);
    }

    private void goPaymentScreen() {
        congrats_layout.setVisibility(View.GONE);
        paymentScreen = true;
        buttonDiscount.setVisibility(View.GONE);
        textView.setText(getString(R.string.final_amount));
        editTextAmount.setText(finalAmount);
        editTextAmount.setEnabled(false);
    }
}
