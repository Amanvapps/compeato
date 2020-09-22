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


public class BottomSheetLanguage extends BottomSheetDialogFragment {

    private Context context;

    private BottomSheetListener mListener;
    OnLanguageInterface onLanguageInterface;
    private int flag;

    private TextView filters, english, korean;



    public BottomSheetLanguage(Context context,OnLanguageInterface onLanguageInterface,int flag) {
        this.onLanguageInterface=onLanguageInterface;
        this.flag=flag;
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
        View v = inflater.inflate(R.layout.bottom_sheet_lang, container, false);

         english = v.findViewById(R.id.eng_lang);
         korean = v.findViewById(R.id.korean_lang);
        MaterialCardView cardView = v.findViewById(R.id.materialCardView);
        cardView.setOnClickListener(view -> dismiss());
         if (flag==1){
             english.setTextColor(context.getResources().getColor(R.color.colorAccent));
             english.setBackgroundColor(context.getResources().getColor(R.color.edittext_bg));
             korean.setTextColor(context.getResources().getColor(R.color.textcolordark));
             korean.setBackgroundColor(context.getResources().getColor(R.color.white_color));
         }else {
             korean.setTextColor(context.getResources().getColor(R.color.colorAccent));
             korean.setBackgroundColor(context.getResources().getColor(R.color.edittext_bg));
             english.setTextColor(context.getResources().getColor(R.color.textcolordark));
             english.setBackgroundColor(context.getResources().getColor(R.color.white_color));
         }


        english.setOnClickListener(view -> {
            onLanguageInterface.onEnglishClick("en");
            dismiss();
        });

//        korean.setOnClickListener(view ->  {
//            onLanguageInterface.onKoreanClick("ko");
//            dismiss();
//        });

        korean.setOnClickListener(view ->  {
            onLanguageInterface.onKoreanClick("ru");
            dismiss();
        });
 
        return v;
    }
 
    public interface BottomSheetListener {
        void onButtonLanguage(String text);
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


    public interface OnLanguageInterface
    {
        void onEnglishClick(String english);
        void onKoreanClick(String korean);
    }

}