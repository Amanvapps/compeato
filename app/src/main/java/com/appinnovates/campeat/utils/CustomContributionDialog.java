package com.appinnovates.campeat.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.appinnovates.campeat.R;

import java.util.Objects;

public class CustomContributionDialog extends DialogFragment {
    private Button submit;
    private static OnCloseInterface closeInterface;

    public static CustomContributionDialog getInstance(OnCloseInterface onCloseInterface) {
        closeInterface=onCloseInterface;
        return new CustomContributionDialog();
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.contribution_success_dialog, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(view);
        if (getArguments() != null) {

        }
        initView(view);
        setListener();
        AlertDialog dialog = dialogBuilder.create();
        dialog.setCanceledOnTouchOutside(false);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setGravity(Gravity.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            dialog.getWindow().setElevation(R.dimen._10sdp);
        }
/*        WindowManager.LayoutParams p = getDialog().getWindow().getAttributes();
        p.width = ViewGroup.LayoutParams.MATCH_PARENT;
        p.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE;
        p.x = 200;
        getDialog().getWindow().setAttributes(p);*/
        return dialog;
    }

    private void initView(View view) {
        submit=view.findViewById(R.id.get_deal);
    }

    private void setListener() {
        submit.setOnClickListener(v -> {
            dismiss();
            closeInterface.onClose();
        });
    }
    public interface OnCloseInterface{
        void onClose();
    }
}
