package com.appinnovates.campeat.bottomSheets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.appinnovates.campeat.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;


public class BottomSheetSelectionCategories extends BottomSheetDialogFragment{

    private Context context;


    private OnBottomSheetItemClicked onBottomSheetItemClicked;
    private int selectedItem;
    public BottomSheetSelectionCategories(Context context,OnBottomSheetItemClicked onBottomSheetItemClicked,int selectedItem) {
        this.context = context;
        this.onBottomSheetItemClicked=onBottomSheetItemClicked;
        this.selectedItem=selectedItem;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.selection_categories, container, false);

        TextView location = v.findViewById(R.id.location_textview);
        TextView discount = v.findViewById(R.id.discount_textview);
        TextView rating = v.findViewById(R.id.rating_textview);
        ImageView icon_location = v.findViewById(R.id.imageView_location_);
        ImageView icon_discount = v.findViewById(R.id.imageView_tag);
        ImageView icon_rating = v.findViewById(R.id.imageView_rating);
        ConstraintLayout discountLayout = v.findViewById(R.id.discount_layout);
        ConstraintLayout locationLayout = v.findViewById(R.id.location_layout);
        ConstraintLayout ratingLayout = v.findViewById(R.id.rating_layout);
        locationLayout.setBackgroundColor(getResources().getColor(R.color.edittext_bg));

        MaterialCardView cardView = v.findViewById(R.id.materialCardView);
        cardView.setOnClickListener(view -> dismiss());
         switch (selectedItem){
             case 1:
                 locationLayout.setBackgroundColor(getResources().getColor(R.color.edittext_bg));
                 location.setTextColor(getResources().getColor(R.color.colorAccent));
                 icon_location.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);

                 discountLayout.setBackgroundColor(getResources().getColor(R.color.white_color));
                 discount.setTextColor(getResources().getColor(R.color.textcolordark));
                 icon_discount.setColorFilter(ContextCompat.getColor(context, R.color.textcolordark), android.graphics.PorterDuff.Mode.SRC_IN);

                 ratingLayout.setBackgroundColor(getResources().getColor(R.color.white_color));
                 rating.setTextColor(getResources().getColor(R.color.textcolordark));
                 icon_rating.setColorFilter(ContextCompat.getColor(context, R.color.textcolordark), android.graphics.PorterDuff.Mode.SRC_IN);

                 break;
             case 2:
                 locationLayout.setBackgroundColor(getResources().getColor(R.color.white_color));
                 location.setTextColor(getResources().getColor(R.color.textcolordark));
                 icon_location.setColorFilter(ContextCompat.getColor(context, R.color.textcolordark), android.graphics.PorterDuff.Mode.SRC_IN);

                 discountLayout.setBackgroundColor(getResources().getColor(R.color.edittext_bg));
                 discount.setTextColor(getResources().getColor(R.color.colorAccent));
                 icon_discount.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);

                 ratingLayout.setBackgroundColor(getResources().getColor(R.color.white_color));
                 rating.setTextColor(getResources().getColor(R.color.textcolordark));
                 icon_rating.setColorFilter(ContextCompat.getColor(context, R.color.textcolordark), android.graphics.PorterDuff.Mode.SRC_IN);
                 break;
             case 3:
                 locationLayout.setBackgroundColor(getResources().getColor(R.color.white_color));
                 location.setTextColor(getResources().getColor(R.color.textcolordark));
                 icon_location.setColorFilter(ContextCompat.getColor(context, R.color.textcolordark), android.graphics.PorterDuff.Mode.SRC_IN);

                 discountLayout.setBackgroundColor(getResources().getColor(R.color.white_color));
                 discount.setTextColor(getResources().getColor(R.color.textcolordark));
                 icon_discount.setColorFilter(ContextCompat.getColor(context, R.color.textcolordark), android.graphics.PorterDuff.Mode.SRC_IN);

                 ratingLayout.setBackgroundColor(getResources().getColor(R.color.edittext_bg));
                 rating.setTextColor(getResources().getColor(R.color.colorAccent));
                 icon_rating.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
                 break;
         }


        location.setOnClickListener(view -> {
            onBottomSheetItemClicked.onLocationClicked();
            dismiss();
        });

        discount.setOnClickListener(view ->  {
            onBottomSheetItemClicked.onDiscountClicked();
            dismiss();
        });

        rating.setOnClickListener(view -> {
            onBottomSheetItemClicked.onRatingClicked();
            dismiss();
        });
        return v;
    }

    public interface BottomSheetListener {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            BottomSheetListener mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }


    public interface OnBottomSheetItemClicked{
        void onLocationClicked();
        void onRatingClicked();
        void onDiscountClicked();
    }

}