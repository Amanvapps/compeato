package com.appinnovates.campeat.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.BookingDealModel;
import com.appinnovates.campeat.model.HomeBranchDeals.BranchDealModel;
import com.appinnovates.campeat.utils.Constant;
import com.appinnovates.campeat.views.activities.ScannerActivity;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScanQrFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ScanQrFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public ScanQrFragment() {
        // Required empty public constructor
    }

    private BranchDealModel branchDealModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scan_qr_code, container, false);
        LinearLayout linearLayout = view.findViewById(R.id.linear_layout);
        if (getArguments() != null) {
            branchDealModel = getArguments().getParcelable(Constant.DEAL_DETAIL);
        }
        linearLayout.setOnClickListener(v -> {
            openScannerActivity();
            onButtonPressed();
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    private void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }

    private void openScannerActivity() {
        Intent intent = new Intent(getActivity(), ScannerActivity.class);
        if (branchDealModel != null) {
            BookingDealModel model = branchDealModel.getDeal().getBookingDealModel();
            intent.putExtra(Constant.BookingDealModel, model);
        }
        startActivity(intent);
        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction().remove(Objects.requireNonNull(getFragmentManager().findFragmentById(R.id.coordinatorLayout))).commit();
        }


    }
}
