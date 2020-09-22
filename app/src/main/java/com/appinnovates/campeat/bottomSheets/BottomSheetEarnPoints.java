package com.appinnovates.campeat.bottomSheets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.utils.Constant;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;


public class BottomSheetEarnPoints extends BottomSheetDialogFragment {

    private Context context;

    private BottomSheetListener mListener;

    private OnEarnPointInterface onEarnPointInterface;
    private Button cancelDeal;
    private TextView msg;
    private int code;
    private String message;
    private String coupon;

    public BottomSheetEarnPoints(Context context, OnEarnPointInterface onEarnPointInterface, String message,int code,String coupon) {
        this.context = context;
        this.message = message;
        this.code=code;
        this.coupon=coupon;
        this.onEarnPointInterface = onEarnPointInterface;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_points, container, false);
        cancelDeal = v.findViewById(R.id.get_deal);
        msg = v.findViewById(R.id.textView11);
        MaterialCardView cardView = v.findViewById(R.id.materialCardView);
        cardView.setOnClickListener(view -> dismiss());

        if (code==1000){
            msg.setText(getString(R.string.reward_earned));
            cancelDeal.setText(getString(R.string._500p));
        }
        if (coupon!=null && coupon.length()==0) {
            msg.setText(getString(R.string.enter_coupon));
            cancelDeal.setText(context.getString(R.string.try_again));
        }
        if (coupon!=null && coupon.length()>0 && coupon.length()<6){
            msg.setText(getString(R.string.enter_correct_coupon));
            cancelDeal.setText(context.getString(R.string.try_again));
        }
        if (message.equalsIgnoreCase(Constant.CouponNotCorrect)) {
            msg.setText(context.getString(R.string.coupon_not_correct));
            cancelDeal.setText(context.getString(R.string.try_again));
        } else if (message.equalsIgnoreCase(Constant.CouponAlreadyUsed)) {
            cancelDeal.setText(context.getString(R.string.try_again));
            msg.setText(context.getString(R.string.cocupon_already_used));
        } else if (message.equalsIgnoreCase(Constant.CouponNotAvailable)) {
            cancelDeal.setText(context.getString(R.string.try_again));
            msg.setText(context.getString(R.string.coupon_not_available));
        } else if (message.equalsIgnoreCase(Constant.CouponAlreadyUsedByYou)) {
            cancelDeal.setText(context.getString(R.string.try_again));
            msg.setText(context.getString(R.string.cocupon_already_used_by_you));
        } else if (code==111){
            cancelDeal.setText(context.getString(R.string.go_to_earn_points));
            msg.setText(context.getString(R.string.you_don_t_have_enough_tadp_please_go_to));
        }
        cancelDeal.setOnClickListener(view -> {
            if (code==111) {
                onEarnPointInterface.onPointsClicked();
                dismiss();
            } else {
                dismiss();
            }
        });

        return v;
    }

    public interface BottomSheetListener {
        void onButtonCancelDeal(String text);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }

    public interface OnEarnPointInterface {
        void onPointsClicked();
    }
}