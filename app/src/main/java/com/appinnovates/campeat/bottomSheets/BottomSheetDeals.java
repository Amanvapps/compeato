package com.appinnovates.campeat.bottomSheets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.appinnovates.campeat.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;


public class BottomSheetDeals extends BottomSheetDialogFragment {

    private Context context;

    private BottomSheetListener mListener;

    private TextView active, inactive;
    private ImageView icon_location, icon_discount;
    private OnBottomSheetDealsClicked onBottomSheetDealsClicked;
    private int count = 0;


    public BottomSheetDeals(Context context, OnBottomSheetDealsClicked onBottomSheetDealsClicked, int count) {
        this.context = context;
        this.onBottomSheetDealsClicked = onBottomSheetDealsClicked;
        this.count = count;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.mydeals_active_inactive, container, false);

        active = v.findViewById(R.id.active_deals);
        inactive = v.findViewById(R.id.inactive_deals);
        icon_location = v.findViewById(R.id.imageView_active);
        icon_discount = v.findViewById(R.id.imageView_expired);
        MaterialCardView cardView = v.findViewById(R.id.materialCardView);
        cardView.setOnClickListener(view -> dismiss());
        if (count == 0) {
            active.setTextColor(getResources().getColor(R.color.colorAccent));
            icon_location.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);


            inactive.setTextColor(getResources().getColor(R.color.textcolordark));
            icon_discount.setColorFilter(ContextCompat.getColor(context, R.color.textcolordark), android.graphics.PorterDuff.Mode.SRC_IN);
        } else if (count == 1) {
            active.setTextColor(getResources().getColor(R.color.textcolordark));
            icon_location.setColorFilter(ContextCompat.getColor(context, R.color.textcolordark), android.graphics.PorterDuff.Mode.SRC_IN);

            inactive.setTextColor(getResources().getColor(R.color.colorAccent));
            icon_discount.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);

        }


        active.setOnClickListener(view -> {
            onBottomSheetDealsClicked.onActiveClicked();
            dismiss();
        });

        inactive.setOnClickListener(view -> {
            onBottomSheetDealsClicked.onInActiveClicked();
            dismiss();
        });


        return v;
    }

    public interface BottomSheetListener {
        void onButtonSelected(String text);
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


    public interface OnBottomSheetDealsClicked {
        void onActiveClicked();

        void onInActiveClicked();
    }

}