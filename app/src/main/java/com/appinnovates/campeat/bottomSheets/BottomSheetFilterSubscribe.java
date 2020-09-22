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
import com.google.android.material.card.MaterialCardView;


public class BottomSheetFilterSubscribe extends BottomSheetDialogFragment implements View.OnClickListener {

    private Context context;

    private BottomSheetListener mListener;


    private TextView textViewDone;
    private TextView txtDistance, txtReset;/* txtNew, txtPopular*/
    private SeekBar seekBar;
    private int distance = 500;
/*
    private boolean isPopular;
*/
    private TextView txtFilter;

    private OnClickFilterInterFace onClickFilterInterFace;
    private String reset;

    public BottomSheetFilterSubscribe(Context context,OnClickFilterInterFace onClickFilterInterFace,String reset) {
        this.context = context;
        this.onClickFilterInterFace=onClickFilterInterFace;
        this.reset=reset;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_filter_subscribe, container, false);
        initView(v);
        MaterialCardView cardView = v.findViewById(R.id.materialCardView);
        cardView.setOnClickListener(view -> dismiss());
        if (reset.equals(Constant.HOME)){
            reset();
        }

        return v;
    }

    public interface BottomSheetListener {
        void onFilterSubscribe(String text);
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
        seekBar = view.findViewById(R.id.seek_bar);
        txtDistance = view.findViewById(R.id.txt_distance);
        txtFilter = view.findViewById(R.id.text_view_filters);
        ScrollView scrollBar = view.findViewById(R.id.scrollbar_advance);
        scrollBar.setVisibility(View.VISIBLE);
        setListener();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distance = progress;
                txtDistance.setText(distance + context.getString(R.string.kms));
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
        int i = view.getId();
        if (i == R.id.text_view_done) {
            onClickFilterInterFace.onClickDone(distance);
            dismiss();
        } else if (i == R.id.text_view_reset) {
            onClickFilterInterFace.onClickReset();
            dismiss();
        } else if (i == R.id.text_view_filters) {
            dismiss();
        }
    }


    private void reset() {
        distance = 500;
        seekBar.setProgress(500);

    }


    public interface OnClickFilterInterFace {
        void onClickDone(int distance);
        void onClickReset();
    }

    private void setListener() {
        textViewDone.setOnClickListener(this);
        txtReset.setOnClickListener(this);
        txtFilter.setOnClickListener(this);
    }
}