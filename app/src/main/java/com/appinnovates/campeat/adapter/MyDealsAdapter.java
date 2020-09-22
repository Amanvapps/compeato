package com.appinnovates.campeat.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.Testing.DealsAdapter;
import com.appinnovates.campeat.listeners.ItemListener;
import com.appinnovates.campeat.model.HomeBranchDeals.BranchDealModel;
import com.appinnovates.campeat.model.HomeBranchDeals.HomeBranchDealsModel;
import com.appinnovates.campeat.model.getAllDealsModel.RestaurantIdModel;
import com.appinnovates.campeat.services.ImageService.ImageService;
import com.appinnovates.campeat.services.ImageService.ImageServiceViewInterface;

import java.util.List;

/**
 * Created by neha on 5/15/18.
 */

public class MyDealsAdapter extends RecyclerView.Adapter<MyDealsAdapter.ViewHolder>{

    public Context context;
    private List<HomeBranchDealsModel> items;
    private ItemListener itemListener;
    private Fragment activity;

    public MyDealsAdapter(Context context, List<HomeBranchDealsModel> items, ItemListener itemListener, Fragment activity) {
        this.activity=activity;
        this.items = items;
        this.context = context;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.resturantscardlayout, parent, false);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(@NonNull MyDealsAdapter.ViewHolder holder, int position) {
        HomeBranchDealsModel homeBranchDealsModel = items.get(position);
        List<BranchDealModel> branch=homeBranchDealsModel.getBranchDealModel();
        RestaurantIdModel restaurantIdModel=homeBranchDealsModel.getmRestaurantIdModel();
        String branchName=homeBranchDealsModel.getBranchName();
        String restaurantName=restaurantIdModel.getRestaurantName();
        branchName=branchName.trim();
        restaurantName=restaurantName.trim();
        holder.textViewRestaurantName.setText(restaurantName+"-"+branchName);
        holder.streetAddress.setText(homeBranchDealsModel.getStreetAddress());
        holder.ratingBar.setText(String.valueOf(restaurantIdModel.getAverageRating()));
        DealsAdapter adapter = new DealsAdapter(branch,homeBranchDealsModel,context,activity,position);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerView.setAdapter(adapter);
        String distance = homeBranchDealsModel.getDistance().equals("Not Specified")?"Not Specified":String.format ("%.2f", homeBranchDealsModel.getDistance())+context.getResources().getString(R.string.kms);
        holder.distance.setText(distance);
        holder.itemView.setOnClickListener(v -> itemListener.onItemClicked(position));
    }



    @Override public int getItemCount() {
        return items.size();
    }

    //ImageService Response

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewRestaurantName;
        private TextView txtGetDeal;
        private ImageView image;
        private TextView ratingBar;
        private TextView timeLeft;
        private TextView streetAddress;
        TextView distance;
        RecyclerView recyclerView;


        public ViewHolder(View itemView) {
            super(itemView);
            //image = (ImageView) itemView.findViewById(R.id.img);
            distance=itemView.findViewById(R.id.splash_txt_distance);
            image = (ImageView) itemView.findViewById(R.id.img);
            textViewRestaurantName = (TextView) itemView.findViewById(R.id.resturant_names);
//            branchName=itemView.findViewById(R.id.branch_name);
/*            textViewDiscountBanner = (TextView) itemView.findViewById(R.id.text_view_discount_banner);
            textViewDiscountTime = (TextView) itemView.findViewById(R.id.text_view_discount_time);
            linearLayoutDiscountExpand = itemView.findViewById(R.id.linear_layout_discount_expand);
            textViewGroupDiscount = itemView.findViewById(R.id.text_view_percent);
            textViewPeople = itemView.findViewById(R.id.text_view_people);*/
            ratingBar = itemView.findViewById(R.id.rest_rating);
            recyclerView = itemView.findViewById(R.id.recyclerView_categories);
            streetAddress=itemView.findViewById(R.id.splash_txt_location);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
        }
    }

/*    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }*/
/*
    public interface ActionListener<HomeBranchDealsModel> {
        void onActionListener(HomeBranchDealsModel model);
    }*/
//    public interface ActionListener<TModel> {
//        void onActionListener(TModel model);
//    }
}