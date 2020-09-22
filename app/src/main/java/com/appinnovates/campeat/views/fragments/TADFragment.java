package com.appinnovates.campeat.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.adapter.TransactionAdapter;
import com.appinnovates.campeat.bottomSheets.BottomSheetReceiveTad;
import com.appinnovates.campeat.bottomSheets.BottomSheetSendTad;
import com.appinnovates.campeat.services.AdService.PointService;
import com.appinnovates.campeat.services.AdService.PointsDelegate;
import com.appinnovates.campeat.services.AdService.TADPEntry;

import java.util.ArrayList;
import java.util.Map;

public class TADFragment extends Fragment implements BottomSheetSendTad.SendtadInterface {


    private TextView txtPoints, txtKRW;
    private RecyclerView recyclerView;
    private View view;
    private Button btnSend, btnReceive;
    private ArrayList<TADPEntry> tadpEntryList = new ArrayList<>();
    private TransactionAdapter adapter;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tad_fragment, container, false);

        initViews();
        setUpRecyclerView();
        setClickListeners();

        requestPoints();

        return view;
    }

    private void requestPoints() {
        new PointService().points(new PointsDelegate() {
            @Override
            public void onSuccess(ArrayList<TADPEntry> tadpEntries, ArrayList<TADPEntry> tadEntries, int totalTADPPoints, int totalTADPoints) {
                adapter.updateData(tadEntries);
                txtPoints.setText(totalTADPoints +" "+ getString(R.string.tad));
                txtKRW.setText(totalTADPoints +" " + getResources().getString(R.string.krw));
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    private void setClickListeners() {
        btnSend.setOnClickListener(v ->
                {
                    BottomSheetSendTad bottomSheet = new BottomSheetSendTad(context,this);
                    assert getFragmentManager() != null;
                    bottomSheet.show(getFragmentManager(), "bottomSheetsend");
                }

        );

        btnReceive.setOnClickListener(v -> {
            BottomSheetReceiveTad bottomSheet = new BottomSheetReceiveTad(context);
            assert getFragmentManager() != null;
            bottomSheet.show(getFragmentManager(), "bottomSheetreceive");
        });
    }

    private void setUpRecyclerView() {
        adapter = new TransactionAdapter(getContext(), tadpEntryList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void initViews() {
        txtKRW = view.findViewById(R.id.txt_krw);
        txtPoints = view.findViewById(R.id.txt_points);
        recyclerView = view.findViewById(R.id.transaction_recycler_id);
        btnSend = view.findViewById(R.id.btn_send);
        btnReceive = view.findViewById(R.id.btn_recieve);
    }


    @Override
    public void sendTadListener(Map map) {

    }
}
