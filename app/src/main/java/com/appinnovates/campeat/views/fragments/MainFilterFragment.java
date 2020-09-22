package com.appinnovates.campeat.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.adapter.MenuTypeAdapter;
import com.appinnovates.campeat.model.BranchMenuModel;
import com.appinnovates.campeat.model.BranchMenuType;
import com.appinnovates.campeat.model.MenuTypeModel;
import com.appinnovates.campeat.services.MenuService.BranchMenuService;
import com.appinnovates.campeat.services.MenuService.BranchMenuServiceInterface;
import com.appinnovates.campeat.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class MainFilterFragment extends Fragment implements View.OnClickListener {

    public LinearLayout priceLayout, foodTypeLayout, distanceLayout, dealTypeLayout;
    ActionListener actionListener;
    List<MenuTypeModel> menuTypeModelList;
    private View view;
    private TextView textViewDone;
    private TextView textViewStandard;
    private TextView textViewGroup;
    private TextView txtDistance, txtReset;/* txtNew, txtPopular*/
    private SeekBar seekBar;
    private boolean isAscending = true;
    private int distance = 500;
    private boolean isDiscount = false;
    private String discountType = "0";
    private boolean isPopular;
    private String dealType = "0";
    private TextView txtFilter;
    private RecyclerView recyclerViewMenuType;
    private MenuTypeAdapter menuTypeAdapter;
    private ArrayList<String> menuTypeIds ;
    private String price = "0";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_filter, container, false);
        initView();
        setListener();
        /*setMeuTypeAdapter();*/
        getMenuType();
        return view;
    }

    private void getMenuType() {
        /*BranchMenuService.getInstance().getMenuType();*/
        BranchMenuService.getInstance().setDelegate(new BranchMenuServiceInterface() {
            @Override
            public void onBranchMenuSuccess(List<BranchMenuModel> branchMenuList
                    , List<BranchMenuType> branchMenuTypeList) {

            }

            @Override
            public void onMenuTypeSuccess(List<MenuTypeModel> menuTypeModels) {
                menuTypeModelList.clear();
                menuTypeModelList.addAll(menuTypeModels);
                menuTypeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNoBranchMenu() {

            }

            @Override
            public void onBranchMenuFailure(String message) {

            }
        });
    }

/*    private void setMeuTypeAdapter() {
        menuTypeAdapter = new MenuTypeAdapter(menuTypeModelList);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerViewMenuType.setLayoutManager(layoutManager);
        recyclerViewMenuType.setAdapter(menuTypeAdapter);
    }*/

    private void setListener() {
        textViewDone.setOnClickListener(this);
        textViewStandard.setOnClickListener(this);
        textViewGroup.setOnClickListener(this);
        txtReset.setOnClickListener(this);
        txtFilter.setOnClickListener(this);
/*        txtPopular.setOnClickListener(this);
        txtNew.setOnClickListener(this);*/
      /*  checkBoxAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (MenuTypeModel menuTypeModel : menuTypeModelList) {
                        menuTypeModel.setSelected(true);
                    }
                } else {
                    for (MenuTypeModel menuTypeModel : menuTypeModelList) {
                        menuTypeModel.setSelected(false);
                    }
                }
                menuTypeAdapter.notifyDataSetChanged();
            }
        });*/

    }

    private void initView() {
        menuTypeModelList = new ArrayList<>();
        menuTypeIds = new ArrayList<>();
/*        txtNew = view.findViewById(R.id.text_view_new);
        txtPopular = view.findViewById(R.id.text_view_popular);
        //foodTypeLayout = view.findViewById(R.id.food_type_layout);
        distanceLayout = view.findViewById(R.id.distance_layout);
        dealTypeLayout = view.findViewById(R.id.deal_type_layout);*/
        txtReset = view.findViewById(R.id.text_view_reset);
        //priceLayout = view.findViewById(R.id.price_layout);
        textViewDone = view.findViewById(R.id.text_view_done);
        textViewStandard = view.findViewById(R.id.text_view_standard);
        textViewGroup = view.findViewById(R.id.text_view_group);
        seekBar = view.findViewById(R.id.seek_bar);
        txtDistance = view.findViewById(R.id.txt_distance);
        txtFilter = view.findViewById(R.id.text_view_filters);
/*        checkBoxAll = view.findViewById(R.id.check_box_all);
        recyclerViewMenuType = view.findViewById(R.id.recycler_view_menu_type);
        LinearLayout linearLayoutSort = view.findViewById(R.id.linear_layout_sort);
        linearLayoutSort.setVisibility(View.GONE);*/
        ScrollView scrollBar = view.findViewById(R.id.scrollbar_advance);
        scrollBar.setVisibility(View.VISIBLE);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distance = progress;
                txtDistance.setText(distance + " KM");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_view_done:
                actionListener.onActionListener(isAscending, price, isDiscount, discountType, distance
                        , dealType);
                break;
            case R.id.text_view_reset:
                reset();
                break;
            case R.id.text_view_filters:
                actionListener.onCloseListener();
                break;
            case R.id.text_view_standard:
                setActiveInActiveBG(textViewStandard, textViewGroup);
                isDiscount = true;
                discountType = Constant.STANDARD;
                break;
            case R.id.text_view_group:
                setActiveInActiveBG(textViewGroup, textViewStandard);
                isDiscount = true;
                discountType = Constant.GROUP;
                break;
/*            case R.id.text_view_popular:
                setActiveInActiveBG(txtPopular, txtNew);
                isPopular = true;
                dealType = Constant.POPULAR;
                break;

            case R.id.text_view_new:
                setActiveInActiveBG(txtNew, txtPopular);
                isPopular = true;
                dealType = Constant.NEW;
                break;*/
        }
    }

    private ArrayList<String> setMenuTypeArray() {
        menuTypeIds.clear();
        for (MenuTypeModel menuTypeModel : menuTypeModelList) {
            if (menuTypeModel.isSelected()) {
                menuTypeIds.add(menuTypeModel.getId());
            }
        }
        return menuTypeIds;
    }

    private void reset() {
        distance = 500;
        seekBar.setProgress(500);
        isDiscount = false;
        isAscending = false;
        isPopular = false;
        for (MenuTypeModel menuTypeModel : menuTypeModelList) {
            if (menuTypeModel.isSelected()) {
                menuTypeModel.setSelected(false);
            }
        }
        /*checkBoxAll.setChecked(false);*/
        menuTypeAdapter.notifyDataSetChanged();
        textViewStandard.setBackgroundResource(R.drawable.border_round_unfill_grey);
        textViewGroup.setBackgroundResource(R.drawable.border_round_unfill_grey);
/*        txtNew.setBackgroundResource(R.drawable.border_round_unfill_grey);
        txtPopular.setBackgroundResource(R.drawable.border_round_unfill_grey);*/

    }

    private void setActiveInActiveBG(TextView active, TextView inactive) {
        active.setBackgroundResource(R.drawable.border_unfill_primary);
        inactive.setBackgroundResource(R.drawable.border_round_unfill_grey);
    }

    public void setActionListener(ActionListener homeActivity) {
        actionListener = homeActivity;
    }

    public interface ActionListener {
        void onActionListener(boolean isAscending, String price, boolean isDiscounted, String discountType
                , int distance, String dealType);

        void onCloseListener();
    }
}
