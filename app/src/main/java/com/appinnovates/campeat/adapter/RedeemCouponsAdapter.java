package com.appinnovates.campeat.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.RedeemCoupon.Result;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.parse.ParseObject;

import java.util.List;


public class RedeemCouponsAdapter extends RecyclerView.Adapter<RedeemCouponsAdapter.RedeemViewHolder>{

    List<Result> list;
    Context context;
    private CouponInterface couponInterface;

    public RedeemCouponsAdapter(Context context, List<Result> list,CouponInterface couponInterface) {
        this.list = list;
        this.context=context;
        this.couponInterface=couponInterface;
    }

    @NonNull
    @Override
    public RedeemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.coupons_list_layout,parent,false);
        return new RedeemViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RedeemViewHolder holder, int position) {
        Result result = list.get(position);
        String branchName=result.getBranch().getBranchName();
        String restaurantName = result.getBranch().getRestaurantId().getRestaurantName();
        branchName = branchName.trim();
        restaurantName = restaurantName.trim();
        String restName = restaurantName.replaceAll("\\s+$", "");
        String branName = branchName.replaceAll("\\s+$", "");
        holder.dealname.setText(result.getCouponName()!=null?result.getCouponName():context.getString(R.string.not_specified));
        holder.textViewRestaurantName.setText(String.format("%s-%s", restName, branName));
        String address = result.getBranch().getStreetAddress();
        address = address.replaceAll("[a-zA-Z]+", "");
        address = address.replaceAll("[,()]", "");
        String koreanAddress = "";
        String[] streetAddress = address.split(" ");
        for (String place : streetAddress) {
            if (place.length()>0) {
                if (!Character.isDigit(place.charAt(0)) || !Character.isDigit(place.charAt(place.length() - 1))) {
                    place = place.replaceAll("[-]", "");
                }
                koreanAddress = koreanAddress.concat(place + " ");
            }
        }
        holder.streetAddress.setText(koreanAddress);

        holder.ratingBar.setText(String.valueOf(result.getBranch().getRestaurantId().getAverageRating()!=null?result.getBranch().getRestaurantId().getAverageRating():0));
        String distance = result.getBranch().getDistance()==null ?context.getString(R.string.not_specified): String.format("%.2f", result.getBranch().getDistance()) + context.getString(R.string.kms);
        holder.distance.setText(distance);
        Glide.with(context)
                .load(result.getPicture().getUrl())
                .override(700,600)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // log exception
                        Log.e("TAG", "Error loading image", e);
                        return false; // important to return false so the error placeholder can be placed
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.dealsImage);

        if (result.getType()==1){
            holder.discount.setText(result.getValue().toString() + context.getString(R.string.off));
        } else if (result.getType()==2){
            holder.discount.setText(context.getString(R.string.won_symbol)+result.getValue().toString() + " "+context.getString(R.string.won));
        }
        holder.itemView.setOnClickListener(v -> couponInterface.onCouponClick(result));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RedeemViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewRestaurantName;

        private TextView ratingBar;
        private TextView streetAddress;
        TextView distance;
        ImageView dealsImage;
        TextView dealname;
        TextView discount;

        RedeemViewHolder(@NonNull View itemView) {
            super(itemView);
            distance = itemView.findViewById(R.id.splash_txt_distance);
            textViewRestaurantName = itemView.findViewById(R.id.resturant_names);
            ratingBar = itemView.findViewById(R.id.rest_rating);
            streetAddress = itemView.findViewById(R.id.splash_txt_location);
            dealsImage = itemView.findViewById(R.id.imageView4);
            dealname = itemView.findViewById(R.id.deal_name);
            discount = itemView.findViewById(R.id.discount_text);
       }
    }

    public interface CouponInterface{
        void onCouponClick(Result result);
    }

}
