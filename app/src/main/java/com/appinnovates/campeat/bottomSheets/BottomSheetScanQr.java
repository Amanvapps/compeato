package com.appinnovates.campeat.bottomSheets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.appinnovates.campeat.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;


public class BottomSheetScanQr extends BottomSheetDialogFragment {

    private Context context;

    private BottomSheetListener mListener;


    public BottomSheetScanQr(Context context) {
        this.context = context;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_scan_qr, container, false);
        MaterialCardView cardView = view.findViewById(R.id.materialCardView);
        cardView.setOnClickListener(view1 -> dismiss());
        LinearLayout qrCode;
        qrCode=view.findViewById(R.id.linear_layout);
        qrCode.setOnClickListener(view1 -> {
            mListener.onQrCodeClicked();
            dismiss();
        });

        return view;
    }
 
    public interface BottomSheetListener {
        void onQrCodeClicked();
    }
 
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
 
        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }
/*
    private void setListeners()
    {
        textViewGroup.setOnClickListener(view -> {
            setActiveInActiveBG(textViewGroup, textViewStandard);
            discountType = Constant.GROUP;
            groupLayout.setVisibility(View.VISIBLE);

        });

        textViewStandard.setOnClickListener(view -> {
            setActiveInActiveBG(textViewStandard, textViewGroup);
            discountType = Constant.STANDARD;
            groupLayout.setVisibility(View.GONE);
            count.setText("1");

        });
    }*/


}