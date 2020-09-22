package com.appinnovates.campeat.views.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.services.AdService.PointService;
import com.appinnovates.campeat.services.AdService.PointsDelegate;
import com.appinnovates.campeat.services.AdService.TADPEntry;

import java.util.ArrayList;

public class ConvertFragment extends Fragment {


    private View view;
    private TextView txtTadp,txtKRWTadp,txtTad,txtKRWTad;
    private EditText edtTad,edtTadp;
    private Button btnConvert;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.convert_fragment,container,false);
        initViews();
        requestPoints();
        return view;
    }

    private void initViews() {
        txtTad = view.findViewById(R.id.txt_tad);
        txtTadp = view.findViewById(R.id.txt_tadp);
        txtKRWTad = view.findViewById(R.id.txt_krw_tad);
        txtKRWTadp = view.findViewById(R.id.txt_krw_tadp);
        edtTad = view.findViewById(R.id.edt_tad);
        edtTadp = view.findViewById(R.id.edt_tadp);
        btnConvert = view.findViewById(R.id.btn_convert);
    }


    private void requestPoints() {
        new PointService().points(new PointsDelegate() {
            @Override
            public void onSuccess(ArrayList<TADPEntry> tadpEntries,ArrayList<TADPEntry> tadEntries,int totalTADPPoints,int totalTADPoints) {
                txtTadp.setText(totalTADPPoints + " "+getString(R.string.tadp));
                txtKRWTadp.setText(totalTADPPoints + " "+getString(R.string.krw));
                txtTad.setText(totalTADPoints + " "+getString(R.string.tad));
                txtKRWTad.setText(totalTADPoints + " "+getString(R.string.krw));
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

}
