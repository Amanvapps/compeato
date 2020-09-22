package com.appinnovates.campeat.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.adapter.TransactionAdapter;
import com.appinnovates.campeat.services.AdService.PointService;
import com.appinnovates.campeat.services.AdService.PointsDelegate;
import com.appinnovates.campeat.services.AdService.TADPEntry;

import java.util.ArrayList;

public class TADPFragment extends Fragment {

    private TextView txtPoints,txtKRW;
    private RecyclerView recyclerView;
    private View view;
    private ArrayList<TADPEntry> tadpEntryList = new ArrayList<>();
    private TransactionAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tadp_fragment,container,false);
        initViews();
        setUpRecyclerView();
        requestPoints();
        return view;
    }

    private void requestPoints() {
        new PointService().points(new PointsDelegate() {
            @Override
            public void onSuccess(final ArrayList<TADPEntry> tadpEntries,ArrayList<TADPEntry> tadEntries,int totalTADPPoints,int totalTADPoints) {
                txtPoints.setText(totalTADPPoints + " "+getResources().getString(R.string.tadp));
                txtKRW.setText(totalTADPPoints + " "+getResources().getString(R.string.krw));
                adapter.updateData(tadpEntries);
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    private void setUpRecyclerView() {
        adapter = new TransactionAdapter(getContext(),tadpEntryList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void initViews() {
        txtKRW = view.findViewById(R.id.txt_krw);
        txtPoints = view.findViewById(R.id.txt_points);
        recyclerView = view.findViewById(R.id.transaction_recycler_id);
    }

    @Override
    public void onResume() {
        super.onResume();
        initViews();
        setUpRecyclerView();
        requestPoints();
    }
}
