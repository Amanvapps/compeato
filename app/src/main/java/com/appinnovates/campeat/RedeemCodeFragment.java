package com.appinnovates.campeat;


import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;

import com.appinnovates.campeat.bottomSheets.BottomSheetEarnPoints;
import com.appinnovates.campeat.model.RedeemCoupon.CouponList;
import com.appinnovates.campeat.model.RedeemCoupon.Result;
import com.appinnovates.campeat.services.CloudNetwork.ApiClientWithSessionId;
import com.appinnovates.campeat.services.CloudNetwork.ApiInterface;
import com.appinnovates.campeat.utils.Constant;
import com.appinnovates.campeat.utils.UserPreferences;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class RedeemCodeFragment extends Fragment {
    private EditText edit1, edit2, edit3, edit4, edit5, edit6;
    private String code="abc";
    private Button redeem;
    private MaterialCardView myRedeem;
    private ConstraintLayout constraintLayout;
    private ContentLoadingProgressBar progressBar;
    private Map<String,Object> map;
    private Float latitude = UserPreferences.getInstance().getFloat(Constant.LATITUDE);
    private Float longitude = UserPreferences.getInstance().getFloat(Constant.LONGITUDE);
    public RedeemCodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.submit_code_screen, container, false);
        map=new HashMap<>();
        map.put("lat", latitude);
        map.put("long", longitude);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back_black);
        toolbar.setNavigationOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
        initViews(view);
        handleEdittexts();
        getCode();
        return view;
    }

    private void getAllCoupons() {
        ApiInterface apiInterface = ApiClientWithSessionId.getClientWithSessionToken().create(ApiInterface.class);
        Call<CouponList> call = apiInterface.getCoupons(map);
        call.enqueue(new Callback<CouponList>() {
            @Override
            public void onResponse(@NonNull Call<CouponList> call, @NonNull Response<CouponList> response) {
                endLoading();
                if (response.body() != null) {
                    List<Result> list = response.body().getResult();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(Constant.LIST, (ArrayList<? extends Parcelable>) list);
                    CouponListFragment fragment = new CouponListFragment();
                    fragment.setArguments(bundle);
                    if (getFragmentManager() != null) {
                        getFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, "CouponListFragment").addToBackStack("CouponListFragment").commit();
                        getFragmentManager().beginTransaction().remove(Objects.requireNonNull(getFragmentManager().findFragmentByTag("RedeemCodeFragment"))).commit();
                    }
                } else {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CouponList> call, @NonNull Throwable t) {
                endLoading();
                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getResponse() {

        ApiInterface apiInterface = ApiClientWithSessionId.getClientWithSessionToken().create(ApiInterface.class);

        Call<CouponList> call = apiInterface.verifyUserPromo(code,map);
        call.enqueue(new Callback<CouponList>() {
            @Override
            public void onResponse(@NonNull Call<CouponList> call, @NonNull Response<CouponList> response) {
                endLoading();
                if (response.body() != null) {
                    List<Result> list = response.body().getResult();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(Constant.LIST, (ArrayList<? extends Parcelable>) list);
                    CouponListFragment fragment = new CouponListFragment();
                    fragment.setArguments(bundle);
                    if (getFragmentManager() != null) {
                        getFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, "CouponListFragment").addToBackStack("CouponListFragment").commit();
                        getFragmentManager().beginTransaction().remove(Objects.requireNonNull(getFragmentManager().findFragmentByTag("RedeemCodeFragment"))).commit();
                    }
                } else {
                    try {
                        JSONObject jObjError = null;
                        if (response.errorBody() != null) {
                            jObjError = new JSONObject(response.errorBody().string());
                        }
                        int errorCode = jObjError != null ? jObjError.getInt("code") : 0;
                        String errorMsg = jObjError != null ? jObjError.getString("error") : "null";
                        BottomSheetEarnPoints bottomSheetEarnPoints = new BottomSheetEarnPoints(getContext(), null,errorMsg,errorCode,code);
                        bottomSheetEarnPoints.show(getFragmentManager(), "sheet");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CouponList> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews(View view) {
        edit1 = view.findViewById(R.id.text_view_number);
        edit2 = view.findViewById(R.id.text_view_number2);
        edit3 = view.findViewById(R.id.text_view_number3);
        edit4 = view.findViewById(R.id.text_view_number4);
        edit5 = view.findViewById(R.id.text_view_number5);
        edit6 = view.findViewById(R.id.text_view_number6);
        redeem = view.findViewById(R.id.btn_next);
        myRedeem = view.findViewById(R.id.get_deal);
        constraintLayout = view.findViewById(R.id.redeem_layout);
        progressBar = view.findViewById(R.id.progressBar_redeem);
    }

    private void handleEdittexts() {

        edit2.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (edit2.getText().toString().equalsIgnoreCase("")) {
                    edit1.requestFocus();
                    edit2.setBackgroundTintList(getResources().getColorStateList(R.color.light_grey));
                }
            }
            return false;
        });
        edit3.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (edit3.getText().toString().equalsIgnoreCase("")) {
                    edit2.requestFocus();
                    edit3.setBackgroundTintList(getResources().getColorStateList(R.color.light_grey));
                }
            }
            return false;
        });
        edit4.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (edit4.getText().toString().equalsIgnoreCase("")) {
                    edit3.requestFocus();
                    edit4.setBackgroundTintList(getResources().getColorStateList(R.color.light_grey));
                }
            }
            return false;
        });
        edit5.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (edit5.getText().toString().equalsIgnoreCase("")) {
                    edit4.requestFocus();
                    edit5.setBackgroundTintList(getResources().getColorStateList(R.color.light_grey));
                }
            }
            return false;
        });
        edit6.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (edit6.getText().toString().equalsIgnoreCase("")) {
                    edit5.requestFocus();
                    edit6.setBackgroundTintList(getResources().getColorStateList(R.color.light_grey));
                }
            }
            return false;
        });
    }

    private void getCode() {
        edit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edit1.getText().toString().length() == 1)     //size as per your requirement
                {
                    edit2.requestFocus();
                    edit1.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                    edit2.setTextColor(getResources().getColor(R.color.white_color));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edit2.getText().toString().length() == 1)     //size as per your requirement
                {
                    edit3.requestFocus();
                    edit2.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                    edit3.setTextColor(getResources().getColor(R.color.white_color));

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edit3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edit3.getText().toString().length() == 1)     //size as per your requirement
                {
                    edit4.requestFocus();
                    edit3.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                    edit4.setTextColor(getResources().getColor(R.color.white_color));

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        edit4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edit4.getText().toString().length() == 1)     //size as per your requirement
                {
                    edit5.requestFocus();
                    edit4.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                    edit5.setTextColor(getResources().getColor(R.color.white_color));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        edit5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edit5.getText().toString().length() == 1)     //size as per your requirement
                {
                    edit6.requestFocus();
                    edit5.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                    edit6.setTextColor(getResources().getColor(R.color.white_color));

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        edit6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edit6.getText().toString().length() == 1)     //size as per your requirement
                {
                    edit6.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                    if (edit1.getText().length() == 1 && edit2.getText().length() == 1 && edit3.getText().length() == 1 && edit4.getText().length() == 1 && edit5.getText().length() == 1 && edit6.getText().length() == 1) {
                        code="";
                        code = edit1.getText().toString() + edit2.getText().toString() + edit3.getText().toString() + edit4.getText().toString() + edit5.getText().toString() + edit6.getText().toString();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        redeem.setOnClickListener(v -> {
            startLoading();
            getResponse();
        });

        myRedeem.setOnClickListener(v -> {
            startLoading();
            getAllCoupons();
        });
    }

    private void startLoading() {
        Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBar.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            constraintLayout.setForegroundTintList(getResources().getColorStateList(R.color.darktransparent));
        }
    }

    private void endLoading() {
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBar.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            constraintLayout.setForegroundTintList(null);
        }
    }

}
