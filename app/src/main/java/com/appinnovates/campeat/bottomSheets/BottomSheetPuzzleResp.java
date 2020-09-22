package com.appinnovates.campeat.bottomSheets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.services.AdService.Ad;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;


public class BottomSheetPuzzleResp extends BottomSheetDialogFragment {

    private Context context;

    private BottomSheetListener mListener;

    private OnEarnPointInterface onEarnPointInterface;
    private Button addpoints;
    private TextView restaurant;
    private Ad ad;

    public BottomSheetPuzzleResp(Context context, OnEarnPointInterface onEarnPointInterface, Ad ad) {
        this.context = context;
        this.onEarnPointInterface=onEarnPointInterface;
        this.ad=ad;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_puzzle, container, false);
        addpoints =v.findViewById(R.id.get_deal);
        restaurant=v.findViewById(R.id.rest_tags);

        MaterialCardView cardView = v.findViewById(R.id.materialCardView);
        cardView.setOnClickListener(view -> dismiss());
        addpoints.setOnClickListener(view -> {
            onEarnPointInterface.onPointsClicked();
            dismiss();
        });
        if (ad!=null){
            restaurant.setText(ad.name +" "+getString(R.string.is_waiting_for_you));
            addpoints.setText("+"+ad.points+" "+getString(R.string.tadp));
        }
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

    public interface OnEarnPointInterface{

        void onPointsClicked();
    }
}