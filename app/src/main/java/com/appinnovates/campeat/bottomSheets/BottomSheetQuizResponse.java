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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;


public class BottomSheetQuizResponse extends BottomSheetDialogFragment {

    private Context context;

    private BottomSheetListener mListener;

    private OnEarnPointInterface onEarnPointInterface;
    private Button cancelDeal;
    private int correct;
    private int total;
    private TextView answer;
    private int points;
    public BottomSheetQuizResponse(Context context, OnEarnPointInterface onEarnPointInterface,int correct,int total,int points) {
        this.context = context;
        this.onEarnPointInterface=onEarnPointInterface;
        this.correct=correct;
        this.total=total;
        this.points=points;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_quiz, container, false);
        cancelDeal=v.findViewById(R.id.get_deal);
        answer=v.findViewById(R.id.answer);
        MaterialCardView cardView = v.findViewById(R.id.materialCardView);
        cardView.setOnClickListener(view -> dismiss());
        answer.setText(getString(R.string.you_got_1_correct_answer_out_of_2,String.valueOf(correct),String.valueOf(total)));
        cancelDeal.setText("+"+points+" "+getString(R.string.tadp));
        cancelDeal.setOnClickListener(view -> {
            onEarnPointInterface.onPointsClicked(points);
            dismiss();
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

    public interface OnEarnPointInterface{
        void onPointsClicked(int points);
    }
}