package com.appinnovates.campeat.bottomSheets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.appinnovates.campeat.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;


public class BottomSheetNoDealLeft extends BottomSheetDialogFragment {

    private Context context;

    private BottomSheetListener mListener;

    private OnNoDealLeftInterface onEarnPointInterface;
    private Button cancelDeal;





    public BottomSheetNoDealLeft(Context context, OnNoDealLeftInterface onEarnPointInterface) {
        this.context = context;
        this.onEarnPointInterface=onEarnPointInterface;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_deal_left, container, false);
        cancelDeal=v.findViewById(R.id.get_deal);

        MaterialCardView cardView = v.findViewById(R.id.materialCardView);
        cardView.setOnClickListener(view -> dismiss());
        cancelDeal.setOnClickListener(view -> {
            onEarnPointInterface.onNoDealLeftClicked();
        });

        return v;
    }
 
    public interface BottomSheetListener {
        void onButtonCancelDeal(String text);
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

    public interface OnNoDealLeftInterface{
        void onNoDealLeftClicked();
    }
}