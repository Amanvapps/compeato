package com.appinnovates.campeat.bottomSheets;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.getAllDealsModel.DealModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;


public class BottomSheetStandardType extends BottomSheetDialogFragment {

    private Context context;

    private BottomSheetListener mListener;
    private TextView msg;
    private TextView discount;
    private DealModel dealModel;

    public BottomSheetStandardType(Context context, DealModel dealModel) {
        this.dealModel=dealModel;
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_standard_type, container, false);
        MaterialCardView cardView = view.findViewById(R.id.materialCardView);
        cardView.setOnClickListener(view1 -> dismiss());
        Button getDeal = view.findViewById(R.id.get_deal);
        msg=view.findViewById(R.id.textView_msg);
        String text=getString(R.string.lucky_coupons);
        discount=view.findViewById(R.id.textView17);

        msg.setText(Html.fromHtml(text + "<font color=#FF6E1E>" + " "+dealModel.getFreeCouponDiscount()+" "+getString(R.string.won) + "</font><br><br>"));
        discount.setText(dealModel.getDiscountRate()+"%"+getString(R.string.discount));
        getDeal.setOnClickListener(view1 -> {
            mListener.onButtonStandard();
            dismiss();
        });

        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

    }
    public interface BottomSheetListener {
        void onButtonStandard();
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

    private void setActiveInActiveBG(TextView active, TextView inactive) {
        active.setBackgroundResource(R.drawable.border_unfill_primary);
        inactive.setBackgroundResource(R.drawable.border_round_unfill_grey);
    }

}