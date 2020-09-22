package com.appinnovates.campeat.views.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.views.activities.EarnPointsActivity;
import com.appinnovates.campeat.views.activities.MyContribution;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class FabTasks extends Fragment {


    public FabTasks() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fab_tasks, container, false);

        FloatingActionButton fab_cont = view.findViewById(R.id.floating_button_contribution);
        fab_cont.setOnClickListener(view1 -> {
            startActivity(new Intent(getActivity(), MyContribution.class));
            Objects.requireNonNull(getActivity()).onBackPressed();
        });
        FloatingActionButton fabEarnPoints = view.findViewById(R.id.floating_button_tad);
        fabEarnPoints.setOnClickListener(view1 ->
        {
            startActivity(new Intent(getActivity(), EarnPointsActivity.class));
            Objects.requireNonNull(getActivity()).onBackPressed();
        });


        return view;
    }

}
