package com.appinnovates.campeat.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.MenuItem;
import com.appinnovates.campeat.views.activities.DealsMenu;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.Objects;

public class CharityDoneDialog extends DialogFragment {

    private ImageView crossButton;
    private ImageView menuImage;
    private TextView textViewName;
    private TextView textViewDesc;
    private TextView price;
    private TextView discountedPrice;
    private ImageView left, right;
    private ArrayList<MenuItem> menuItemlist;
    private int position;
    private int length;

    public static CharityDoneDialog getInstance(MenuItem menuItem, ArrayList<MenuItem> menuItemList, int position) {
        CharityDoneDialog customMenuDialog = new CharityDoneDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable("menuItem", menuItem);
        bundle.putParcelableArrayList("menuItemlist", menuItemList);
        bundle.putInt("position", position);
        customMenuDialog.setArguments(bundle);
        return customMenuDialog;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_menu_dialog, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(view);
        if (getArguments() != null) {
            menuItemlist = getArguments().getParcelableArrayList("menuItemlist");
            position = getArguments().getInt("position");
        }
        length = menuItemlist.size();
        initView(view);
        setListener();
        setData(position);
        if (position==0){
            left.setVisibility(View.GONE);
        } else if (length==position+1){
            right.setVisibility(View.GONE);
        }
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
        price = view.findViewById(R.id.price);
        discountedPrice = view.findViewById(R.id.discounted_price);
        crossButton = view.findViewById(R.id.cross);
        menuImage = view.findViewById(R.id.image_view_menu);
        textViewName = view.findViewById(R.id.text_view_menu_name);
        textViewDesc = view.findViewById(R.id.text_view_menu_desc);
        left = view.findViewById(R.id.arrow_left);
        right = view.findViewById(R.id.arrow_right);
    }

    private void setListener() {
        crossButton.setOnClickListener(v -> dismiss());
        left.setOnClickListener(view -> {
            position--;
            if (length > position && position >= 0) {

                setData(position);
                right.setVisibility(View.VISIBLE);
            }
            if(position==0){
                left.setVisibility(View.GONE);
            }

        });

        right.setOnClickListener(view -> {
            position++;
            if (length > position && position >= 0) {
                setData(position);
                left.setVisibility(View.VISIBLE);
            }
            if (length==position+1){
                right.setVisibility(View.GONE);
            }
        });
    }

    private void setData(int position) {

        if (length > position) {
            assert menuItemlist != null;
            MenuItem menuItem = menuItemlist.get(position);
            if (menuItem != null) {
                textViewName.setText(menuItem.getMenu_name().trim());
                if (null != menuItem.getMenu_extra_details())
                    textViewDesc.setText(menuItem.getMenu_extra_details().trim());
                price.setText(getString(R.string.krw) +" "+ menuItem.getPrice());
                price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                int discount=menuItem.getPrice()/100*(100- DealsMenu.discountedPrice);
                discountedPrice.setText(getString(R.string.krw) +" "+discount);
                if (menuItem.getPicture() != null)
                    Glide.with(getActivity())
                            .load(menuItem.getPicture().getUrl())
                            .override(400, 300)
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    // log exception
                                    Log.e("TAG", "Error loading image", e);
                                    return false; // important to return false so the error placeholder can be placed
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    return false;
                                }
                            })
                            .into(menuImage);
            }
        }
    }

}
