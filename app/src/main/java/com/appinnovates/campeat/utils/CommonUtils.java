package com.appinnovates.campeat.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.BranchMenuType;

import java.util.Comparator;



public class CommonUtils {

    // Notification
    public static void showToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();}

    // Progress bar
    private static ProgressDialog progressDialog;

    public static void showProgress(Context context) {
        if (context == null)
            return;
        progressDialog = ProgressDialog.show(context,null, context.getString(R.string.please_wait)
                , true, true);
    }

    public static void hideProgress() {
        if (progressDialog != null){
        progressDialog.dismiss();
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

//    public static Comparator<BranchMenuModel> MenuNameComparator = new Comparator<BranchMenuModel>() {
//
//        @Override
//        public int compare(BranchMenuModel menu1, BranchMenuModel menu2) {
//            String menuName1 = menu1.getMenu_type().getMenu_type_name().toUpperCase();
//            String menuName2 = menu2.getMenu_type().getMenu_type_name().toUpperCase();
//
//            //ascending order
//            return menuName1.compareTo(menuName2);
//        }};
    public static Comparator<BranchMenuType> BranchMenuComparator = new Comparator<BranchMenuType>() {

        @Override
        public int compare(BranchMenuType menu1, BranchMenuType menu2) {
            String menuName1 = menu1.getMenuType().toUpperCase();
            String menuName2 = menu2.getMenuType().toUpperCase();

            //ascending order
            return menuName1.compareTo(menuName2);
        }};

    public static void setActiveTab(Context context,TextView active, TextView inactive, TextView inactiveTV) {
        active.setBackgroundResource(R.drawable.square_orange_button);
        active.setTextColor(context.getResources().getColor(R.color.white_color));
        inactive.setBackground(null);
        inactive.setTextColor(context.getResources().getColor(R.color.textGreyColor));
        inactiveTV.setBackground(null);
        inactiveTV.setTextColor(context.getResources().getColor(R.color.textGreyColor));
    }
}
