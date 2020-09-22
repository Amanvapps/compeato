package com.appinnovates.campeat;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.appinnovates.campeat.adapter.ReceiptAdapter;
import com.appinnovates.campeat.model.receiptModel.ReceiptData;
import com.appinnovates.campeat.model.receiptModel.Result;
import com.appinnovates.campeat.services.CloudNetwork.ApiClientWithSessionId;
import com.appinnovates.campeat.services.CloudNetwork.ApiInterface;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyReceipts extends Fragment {

    @BindView(R.id.receipts_recyclerview) RecyclerView recyclerView;
    private List<Result> receiptList;
    @BindView(R.id.progressBar2)  ContentLoadingProgressBar progressBar;
    @BindView(R.id.textView26)
    TextView notFound;
    public MyReceipts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_receipts, container, false);
        ButterKnife.bind(this,view);
        Toolbar toolbar = view.findViewById(R.id.toolbar_item_details);
        toolbar.setNavigationIcon(R.drawable.arrow_back_black);
        toolbar.setNavigationOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
        getReceiptdata();
        return view;
    }

    private void setRecyclerView(){
        ReceiptAdapter adapter=new ReceiptAdapter(getActivity(),receiptList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
    private void getReceiptdata(){
        ApiInterface apiInterface= ApiClientWithSessionId.getClientWithSessionToken().create(ApiInterface.class);
        Call<ReceiptData> call=apiInterface.getUserPayment();
        call.enqueue(new Callback<ReceiptData>() {
            @Override
            public void onResponse(@NonNull Call<ReceiptData> call, @NonNull Response<ReceiptData> response) {
                if (response.body()!=null){
                    progressBar.setVisibility(View.GONE);
                    receiptList =response.body().getResult();
                    Collections.reverse(receiptList);
                    setRecyclerView();
                } else
                    notFound.setVisibility(View.VISIBLE);

            }
            @Override
            public void onFailure(@NonNull Call<ReceiptData> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
