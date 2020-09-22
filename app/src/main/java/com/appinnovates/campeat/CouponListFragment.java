package com.appinnovates.campeat;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appinnovates.campeat.adapter.RedeemCouponsAdapter;
import com.appinnovates.campeat.model.RedeemCoupon.Result;
import com.appinnovates.campeat.utils.Constant;
import com.appinnovates.campeat.views.activities.CouponMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class CouponListFragment extends Fragment implements RedeemCouponsAdapter.CouponInterface {

    private List<Result> list;
    private TextView noCoupons;
    public CouponListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_coupon_list, container, false);
        list=new ArrayList<>();
        Toolbar toolbar = view.findViewById(R.id.toolbar_item_details);
        noCoupons=view.findViewById(R.id.no_coupons);
        toolbar.setNavigationIcon(R.drawable.arrow_back_black);
        toolbar.setNavigationOnClickListener(v -> Objects.requireNonNull(getActivity()).onBackPressed());
        if (getArguments() != null) {
            list=getArguments().getParcelableArrayList(Constant.LIST);
        }
        if (list!= null && list.size()==0){
            noCoupons.setVisibility(View.VISIBLE);
        }
        setRecyclerView(view);
        return view;
    }

    private void setRecyclerView(View view){
        RedeemCouponsAdapter adapter=new RedeemCouponsAdapter(getContext(),list,this);
        RecyclerView recyclerView = view.findViewById(R.id.redeem_lists);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCouponClick(Result result) {
        startActivity(new Intent(getActivity(), CouponMenu.class).putExtra("coupon_object",result).putExtra("picture",result.getPicture()));
    }
}
