package com.appinnovates.campeat.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.services.BranchService.BranchDelegate;
import com.appinnovates.campeat.services.BranchService.BranchService;
import com.google.android.gms.vision.barcode.Barcode;
import com.parse.ParseObject;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;

public class EasyPayScannerActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener, View.OnClickListener {

    private BarcodeReader barCodeReader;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        initView();
        setListener();
    }

    private void initView() {
        barCodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);
        backButton = findViewById(R.id.back);
    }

    private void setListener() {
        backButton.setOnClickListener(this);
    }

    @Override
    public void onScanned(final Barcode barcode) {
        barCodeReader.playBeep();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (barcode.displayValue != null) {
                    checkIfBranchAvailable(barcode.displayValue);
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.scanner_error), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void checkIfBranchAvailable(String id){
        new BranchService().branches(id, new BranchDelegate() {
            @Override
            public void onSuccess(ParseObject model) {
                launchAmountScreen();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(getApplicationContext(), getString(R.string.scanner_error), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void launchAmountScreen(){
        Intent intent = new Intent(this, EasyPayAmountActivity.class);
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Error occurred while scanning " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}