package com.appinnovates.campeat.bottomSheets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.appinnovates.campeat.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;


public class BottomSheetDealType extends BottomSheetDialogFragment implements View.OnClickListener {

    private Context context;

    private BottomSheetListener mListener;

    private TextView filters,location,discount;
    private ImageView icon_increment,icon_decrement;

    private String discountType = "0";
    private int minLimit = 1;
    private TextView count;
    private int peopleCount;
    TextView txtMinPeople;

    public BottomSheetDealType(Context context,int peopleCount) {
        this.peopleCount=peopleCount;
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
        View view = inflater.inflate(R.layout.bottom_sheet_discount_type, container, false);

        count =  view.findViewById(R.id.count);
        txtMinPeople=view.findViewById(R.id.min_people);
        ImageView plus = view.findViewById(R.id.plus);
        ImageView minus = view.findViewById(R.id.minus);
        Button getDeal = view.findViewById(R.id.get_deal);
        count.setText(String.valueOf(minLimit));
        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
        txtMinPeople.setText(context.getResources().getString(R.string.minimum_number_of_people_to_visit_together_must_be)+" "+peopleCount);
        MaterialCardView cardView = view.findViewById(R.id.materialCardView);
        cardView.setOnClickListener(view1 -> dismiss());
        getDeal.setOnClickListener(view1 -> {
            int limit=Integer.parseInt(count.getText().toString());
            if (limit<peopleCount){
                txtMinPeople.setBackgroundResource(R.drawable.border_unfill_red);
            } else {
                mListener.onButtonDeal(count.getText().toString());
                dismiss();
            }
        });

        return view;
    }
 
    public interface BottomSheetListener {
        void onButtonDeal(String count);
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

    private void setActiveInActiveBG(TextView active, TextView inactive) {
        active.setBackgroundResource(R.drawable.border_unfill_primary);
        inactive.setBackgroundResource(R.drawable.border_round_unfill_grey);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plus:
                int a = Integer.valueOf(count.getText().toString());
                a++;
                count.setText("" + a);
                break;

            case R.id.minus:
                int b = Integer.valueOf(count.getText().toString());
                if (b > minLimit) {
                    b--;
                    count.setText("" + b);
                }
                break;
        }

    }

}