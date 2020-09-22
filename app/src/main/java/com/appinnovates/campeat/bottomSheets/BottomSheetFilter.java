package com.appinnovates.campeat.bottomSheets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.utils.Constant;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;


public class BottomSheetFilter extends BottomSheetDialogFragment implements View.OnClickListener {

    private Context context;

    private BottomSheetListener mListener;

    private TextView textViewDone;
    private TextView textViewStandard;
    private TextView textViewGroup;
    private TextView txtDistance, txtReset;
    private SeekBar seekBar;
    private boolean isAscending = true;
    private int distance = 500;
    private boolean isDiscount = false;
    private String discountType = "0";
    /*
        private boolean isPopular;
    */
    private String dealType = "0";
    private TextView txtFilter;
    private String reset = "";
    private String type;
    private int state;
    private OnClickDoneInterface onClickDoneInterface;

    public BottomSheetFilter (Context context){}
    public BottomSheetFilter(Context context, OnClickDoneInterface onClickDoneInterface, String reset, String type, int state) {
        this.context = context;
        this.reset = reset;
        this.onClickDoneInterface = onClickDoneInterface;
        this.type = type;
        this.state = state;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_filter, container, false);
        initView(v);


        return v;
    }

    public interface BottomSheetListener {
        void onFilter(String text);
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

    private void initView(View view) {
        txtReset = view.findViewById(R.id.text_view_reset);
        textViewDone = view.findViewById(R.id.text_view_done);
        textViewStandard = view.findViewById(R.id.text_view_standard);
        textViewGroup = view.findViewById(R.id.text_view_group);
        seekBar = view.findViewById(R.id.seek_bar);
        txtDistance = view.findViewById(R.id.txt_distance);
        txtFilter = view.findViewById(R.id.text_view_filters);
        ScrollView scrollBar = view.findViewById(R.id.scrollbar_advance);
        scrollBar.setVisibility(View.VISIBLE);
        setListener();
        try {
            if (reset.equals(Constant.HOME)) {
                reset();
            }

            if (type.equalsIgnoreCase(Constant.STANDARD)) {
                discountType= Constant.STANDARD_;
                setActiveInActiveBG(textViewStandard,textViewGroup);
            } else if (type.equalsIgnoreCase(Constant.GROUP)) {
                discountType= Constant.GROUP_;
                setActiveInActiveBG(textViewGroup, textViewStandard);
            }

            textViewDone.setOnClickListener(view1 -> {
                onClickDoneInterface.onClickDoneListener(isDiscount, discountType, distance);
                dismiss();
            });
            if (state>0){
                seekBar.setProgress(state);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distance = progress;
                txtDistance.setText(distance + getString(R.string.kms));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_view_reset:
                onClickDoneInterface.filterReset();
                dismiss();
                break;
            case R.id.text_view_filters:
                dismiss();
                break;
            case R.id.text_view_standard:
                setActiveInActiveBG(textViewStandard, textViewGroup);
                isDiscount = true;
                discountType = Constant.STANDARD_;
                break;
            case R.id.text_view_group:
                setActiveInActiveBG(textViewGroup, textViewStandard);
                isDiscount = true;
                discountType = Constant.GROUP_;
                break;
        }
    }

    private void reset() {
        distance = 500;
        seekBar.setProgress(500);
        isDiscount = false;
        isAscending = false;
        textViewStandard.setBackgroundResource(R.drawable.border_round_unfill_grey);
        textViewGroup.setBackgroundResource(R.drawable.border_round_unfill_grey);
    }

    private void setActiveInActiveBG(TextView active, TextView inactive) {
        active.setBackgroundResource(R.drawable.border_unfill_primary);
        inactive.setBackgroundResource(R.drawable.border_round_unfill_grey);
    }

    private void setListener() {
        textViewDone.setOnClickListener(this);
        textViewStandard.setOnClickListener(this);
        textViewGroup.setOnClickListener(this);
        txtReset.setOnClickListener(this);
        txtFilter.setOnClickListener(this);
    }

    public interface OnClickDoneInterface {
        void onClickDoneListener(boolean isDiscounted, String discountType
                , int distance);

        void filterReset();
    }
}