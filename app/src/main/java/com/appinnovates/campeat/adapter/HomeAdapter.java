package com.appinnovates.campeat.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.Testing.DealsAdapter;
import com.appinnovates.campeat.listeners.ItemListener;
import com.appinnovates.campeat.model.HomeBranchDeals.BranchDealModel;
import com.appinnovates.campeat.model.HomeBranchDeals.HomeBranchDealsModel;
import com.appinnovates.campeat.model.getAllDealsModel.RestaurantIdModel;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    public Context context;
    private List<HomeBranchDealsModel> items;
    private ItemListener itemListener;
    private Fragment fragment;
    private Activity activity;
    private String activityName;

    public HomeAdapter(Context context, List<HomeBranchDealsModel> items, ItemListener itemListener, Fragment activity) {
        this.items = items;
        this.fragment = activity;
        this.context = context;
        this.itemListener = itemListener;
    }

    public HomeAdapter(Context context, List<HomeBranchDealsModel> items, ItemListener itemListener, Activity activity, String activityName) {
        this.items = items;
        this.activityName = activityName;
        this.activity = activity;
        this.context = context;
        this.itemListener = itemListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewRestaurantName;

        private TextView ratingBar;
        private TextView streetAddress;
        TextView distance;
        RecyclerView recyclerView;


        public ViewHolder(View itemView, Context context) {
            super(itemView);
            distance = itemView.findViewById(R.id.splash_txt_distance);
            textViewRestaurantName = itemView.findViewById(R.id.resturant_names);
            ratingBar = itemView.findViewById(R.id.rest_rating);
            recyclerView = itemView.findViewById(R.id.recyclerView_categories);
            streetAddress = itemView.findViewById(R.id.splash_txt_location);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.resturantscardlayout, parent, false);
        return new ViewHolder(v, context);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        HomeBranchDealsModel homeBranchDealsModel = items.get(position);
        List<BranchDealModel> branch = homeBranchDealsModel.getBranchDealModel();
        RestaurantIdModel restaurantIdModel = homeBranchDealsModel.getmRestaurantIdModel();
        String branchName = homeBranchDealsModel.getBranchName();
        String restaurantName = restaurantIdModel.getRestaurantName();
        branchName = branchName.trim();
        restaurantName = restaurantName.trim();
        String restName = restaurantName.replaceAll("\\s+$", "");
        String branName = branchName.replaceAll("\\s+$", "");

        holder.textViewRestaurantName.setText(String.format("%s-%s", restName, branName));
        String address = homeBranchDealsModel.getStreetAddress();

        address = address.replaceAll("[a-zA-Z]+", "");
        holder.streetAddress.setText(address);

        holder.ratingBar.setText(String.valueOf(homeBranchDealsModel.getRating()));
        DealsAdapter adapter;
        if (activity == null)
            adapter = new DealsAdapter(branch, homeBranchDealsModel, context, fragment, position);
        else
            adapter = new DealsAdapter(branch, homeBranchDealsModel, context, activity, position,activityName);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerView.setItemViewCacheSize(20);
        holder.recyclerView.setAdapter(adapter);
        String distance = homeBranchDealsModel.getDistance().equals("Not Specified") ? "Not Specified" : String.format("%.2f", homeBranchDealsModel.getDistance()) + context.getString(R.string.kms);
        holder.distance.setText(distance);

        holder.itemView.setOnClickListener(v -> itemListener.onItemClicked(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}