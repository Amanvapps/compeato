package com.appinnovates.campeat.views.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.BookingDealModel;
import com.appinnovates.campeat.model.RedeemCoupon.Result;
import com.appinnovates.campeat.model.getAllDealsModel.BranchModel;
import com.appinnovates.campeat.model.getAllDealsModel.DealModel;
import com.appinnovates.campeat.services.BookingService.BookingNumberDelegate;
import com.appinnovates.campeat.services.BookingService.CreateBookingService;
import com.appinnovates.campeat.services.CloudNetwork.ApiClientWithSessionId;
import com.appinnovates.campeat.services.CloudNetwork.ApiInterface;
import com.appinnovates.campeat.utils.Constant;
import com.appinnovates.campeat.utils.PrefManager;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.appinnovates.campeat.views.activities.CouponMenu.couponModelResult;

public class ScannerActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener, View.OnClickListener {

    private BarcodeReader barCodeReader;
    private BookingDealModel bookingDealModel;
    private final int REQUEST_CODE = 202;

    private Result result;

    boolean isCoupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        if (couponModelResult != null)
            this.result = couponModelResult;
        isCoupon = PrefManager.isScan();
        Toolbar toolbar = findViewById(R.id.toolbar_item_details);
        toolbar.setNavigationIcon(R.drawable.arrow_back_black);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CODE);
        } else {
            initView();
        }
        bookingDealModel = getIntent().getParcelableExtra(Constant.BookingDealModel);
        initView();
    }

    private void initView() {
        barCodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);
    }


    @Override
    public void onScanned(final Barcode barcode) {
        barCodeReader.playBeep();

        runOnUiThread(() -> {
            BranchModel branchModel;
            if (bookingDealModel != null && bookingDealModel.getBranchDealModel() != null) {
                branchModel = bookingDealModel.getBranchDealModel().getBranch();
            } else {
                branchModel = result.getBranch();
            }
            if (barcode.displayValue != null && branchModel != null) {
                if (barcode.displayValue.equals(result.getObjectId()))
                    couponLaunch(barcode.displayValue);
                else if (barcode.displayValue.equals(branchModel.getObjectId()))
                    launch(barcode.displayValue);
                else
                    Toast.makeText(this, "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.scanner_error), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void launch(final String value) {

        try {
            if (bookingDealModel != null) {
                DealModel dealsModel = bookingDealModel.getBranchDealModel().getDeal();
                if (dealsModel != null) {
//                    if (dealsModel.getType().equalsIgnoreCase("free_coupon")) {
                    if (bookingDealModel.getBranchDealModel().getDeal().getFreeCouponDiscount() != null && bookingDealModel.getBranchDealModel().getDeal().getFreeCouponDiscount() != 0) {
                        CreateBookingService.getInsance().bookings(bookingDealModel.getBranchDealModel().getBranchDealPointer(), new BookingNumberDelegate() {
                            @Override
                            public void onSuccess(int number) {
                                try {
                                    int[] indices = bookingDealModel.getBranchDealModel().getDeal().getIndexes();
                                    if (indices != null) {
                                        boolean avail = false;
                                        for (int item : indices) {
                                            if (item == number + 1) {
                                                avail = true;
                                                break;
                                            }
                                        }
                                        launchAmountScreen(true, avail, value, number + 1);

                                    } else {
                                        launchAmountScreen(true, false, value, 0);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void message(String message) {
                                Log.i("message", message);
                            }
                        });
                    } else {
                        launchAmountScreen(false, false, value, 0);
                    }
/*                    } else {
                        launchAmountScreen(false, false, value, 0);
                    }*/
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void couponLaunch(final String value) {

        ApiInterface apiInterface = ApiClientWithSessionId.getClientWithSessionToken().create(ApiInterface.class);
        Call<Object> call = apiInterface.scanCoupons(result.getObjectId());
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                if (response.body() != null) {
                    launchAmountScreen(false,false,value,0);
                } else {
                    Toast.makeText(ScannerActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                Toast.makeText(ScannerActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void launchAmountScreen(boolean isFreeCoupon, boolean availFreeCoupon, String displayValue, int index) {
        Intent intent = new Intent(ScannerActivity.this, AmountActivity.class);
        if (bookingDealModel != null) {
            intent.putExtra(Constant.BookingDealModel, bookingDealModel);
            intent.putExtra("isFreeCoupon", isFreeCoupon);
            intent.putExtra("availFreeCoupon", availFreeCoupon);
        }
        intent.putExtra(Constant.CODE, displayValue);
        intent.putExtra("index", index);
        startActivity(intent);
        finish();
    }


    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(final String errorMessage) {
        runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error occurred while scanning " + errorMessage, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {// If request is cancelled, the couponModelResult arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(new Intent(this, ScannerActivity.class).putExtra(Constant.BookingDealModel, bookingDealModel));
                finish();
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    new AlertDialog.Builder(this).setTitle("Alert")
                            .setMessage("Please allow the permission to scan QR Code")
                            .setPositiveButton(getString(R.string.allow), (dialog, which) -> {
                                ActivityCompat.requestPermissions(this,
                                        new String[]{Manifest.permission.CAMERA},
                                        REQUEST_CODE);
                            })
                            .setNegativeButton(getString(R.string.deny), null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setCancelable(false)
                            .show();
                }
            }
        }
    }
}
