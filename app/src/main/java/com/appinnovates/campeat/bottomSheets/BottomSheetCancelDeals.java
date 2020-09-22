package com.appinnovates.campeat.bottomSheets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.appinnovates.campeat.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;


public class BottomSheetCancelDeals extends BottomSheetDialogFragment {

    private Context context;

    private BottomSheetListener mListener;

    private OnCancelDealInterface onCancelDealInterface;
    private TextView cancelDeal;
    private TextView dismiss;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

    }


    public BottomSheetCancelDeals(Context context,OnCancelDealInterface onCancelDealInterface) {
        this.context = context;
        this.onCancelDealInterface=onCancelDealInterface;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_cancel_deal, container, false);
        cancelDeal=v.findViewById(R.id.cancel);
        dismiss=v.findViewById(R.id.dismiss);
        cancelDeal.setOnClickListener(view -> {
            onCancelDealInterface.onCancelDealClicked();
            dismiss();
        });
        dismiss.setOnClickListener(view -> dismiss());
        MaterialCardView cardView = v.findViewById(R.id.materialCardView);
        cardView.setOnClickListener(view -> dismiss());
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

    public interface OnCancelDealInterface{
        void onCancelDealClicked();
    }
}