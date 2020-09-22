package com.appinnovates.campeat.views.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.appinnovates.campeat.AgreementTerms;
import com.appinnovates.campeat.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Agreement extends Fragment {


    public Agreement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_agreement, container, false);

        TextView agreementDetails=view.findViewById(R.id.agreement_details);
        agreementDetails.setOnClickListener(v -> {
            if (getFragmentManager() != null) {
                getFragmentManager().beginTransaction().add(R.id.layout_fragment, new AgreementTerms()).addToBackStack("").commit();
            }
        });

        return view;
    }

}
