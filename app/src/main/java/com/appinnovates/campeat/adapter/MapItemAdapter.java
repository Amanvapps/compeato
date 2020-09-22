package com.appinnovates.campeat.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.Testing.DealsAdapter;
import com.appinnovates.campeat.listeners.ItemListener;
import com.appinnovates.campeat.model.HomeBranchDeals.HomeBranchDealsModel;
import com.appinnovates.campeat.model.getAllDealsModel.RestaurantIdModel;
import com.appinnovates.campeat.services.ImageService.ImageServiceViewInterface;

import java.util.List;

public class MapItemAdapter extends RecyclerView.Adapter<MapItemAdapter.ViewHolder> implements ImageServiceViewInterface {

    public Context context;
    private List<HomeBranchDealsModel> items;
    private ItemListener itemListener;
    Fragment activity;

    public MapItemAdapter(Context context, List<HomeBranchDealsModel> items, ItemListener itemListener,Fragment activity) {
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


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override public void onBindViewHolder(@NonNull final MapItemAdapter.ViewHolder holder, final int position) {
        HomeBranchDealsModel homeBranchDealsModel = items.get(position);
        List<com.appinnovates.campeat.model.HomeBranchDeals.BranchDealModel> branch=homeBranchDealsModel.getBranchDealModel();
        RestaurantIdModel restaurantIdModel=homeBranchDealsModel.getmRestaurantIdModel();
        String branchName=homeBranchDealsModel.getBranchName();
        String restaurantName=restaurantIdModel.getRestaurantName();
        branchName=branchName.trim();
        restaurantName=restaurantName.trim();
        holder.textViewRestaurantName.setText(restaurantName+"-"+branchName);
        holder.streetAddress.setText(homeBranchDealsModel.getStreetAddress());
        holder.streetAddress.setText(homeBranchDealsModel.getStreetAddress());
        holder.ratingBar.setText(String.valueOf(restaurantIdModel.getAverageRating()));
        DealsAdapter adapter = new DealsAdapter(branch,homeBranchDealsModel,context,activity,position);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerView.setAdapter(adapter);
        String distance = homeBranchDealsModel.getDistance().equals("Not Specified")?"Not Specified":String.format ("%.2f", homeBranchDealsModel.getDistance())+"Kms";
        holder.distance.setText(distance);
        holder.itemView.setOnClickListener(v -> itemListener.onItemClicked(position));
    }

    @Override public int getItemCount() {
        return items.size();
    }

    @Override
    public void onImageServiceSuccess(Bitmap bitmap) {

    }

    @Override
    public void onImageServiceFailure(String message) {

    }

    @Override
    public void onImageServiceSuccessForRow(Bitmap bitmap, int position, ImageView imageView) {
        if (bitmap!=null){
            imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onImageServiceFailureForRow(String message, int position) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        /*        private final LinearLayout linearLayoutDiscountExpand;
                private final TextView textViewDiscountBanner;*/
        private final TextView textViewRestaurantName;
        /*        private final TextView textViewDiscountTime;
                private final TextView textViewPeople;
                private final TextView textViewGroupDiscount;
                private final TextView txtStandardDiscount;*/
        private TextView txtGetDeal;
        private ImageView image;
        private TextView ratingBar;
        private TextView timeLeft;
        private TextView streetAddress;
        TextView distance;
        RecyclerView recyclerView;
        public ViewHolder(View itemView) {
            super(itemView);
            distance=itemView.findViewById(R.id.splash_txt_distance);
            image = (ImageView) itemView.findViewById(R.id.img);
            textViewRestaurantName = (TextView) itemView.findViewById(R.id.resturant_names);
            ratingBar = itemView.findViewById(R.id.rest_rating);
            recyclerView = itemView.findViewById(R.id.recyclerView_categories);
            streetAddress=itemView.findViewById(R.id.splash_txt_location);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
        }
    }
}