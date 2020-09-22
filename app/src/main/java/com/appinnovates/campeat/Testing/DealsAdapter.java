package com.appinnovates.campeat.Testing;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.HomeBranchDeals.BranchDealModel;
import com.appinnovates.campeat.model.HomeBranchDeals.HomeBranchDealsModel;
import com.appinnovates.campeat.model.getAllDealsModel.DealModel;
import com.appinnovates.campeat.utils.Constant;
import com.appinnovates.campeat.views.activities.DealsMenu;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.ViewHolder> {


    private List<BranchDealModel> list;
    private Context context;
    private HomeBranchDealsModel homeBranchDealsModel;
    private Fragment fragment;
    private Activity activity;
    private int branchPosition;
    private String activityName = "activity";


    public DealsAdapter(List<BranchDealModel> list, HomeBranchDealsModel homeBranchDealsModel, Context context, Fragment fragment, int branchPosition) {
        this.list = list;
        this.branchPosition = branchPosition;
        this.fragment = fragment;
        this.context = context;
        this.homeBranchDealsModel = homeBranchDealsModel;
    }

    public DealsAdapter(List<BranchDealModel> list, HomeBranchDealsModel homeBranchDealsModel, Context context, Activity activity, int branchPosition, String activityName) {
        this.branchPosition = branchPosition;
        this.list = list;
        this.activity = activity;
        this.context = context;
        this.activityName = activityName;
        this.homeBranchDealsModel = homeBranchDealsModel;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDiscountBannerCoupons;
        ImageView dealsImage;
        TextView dealname;
        TextView dealLeft;
        TextView dealLeft2;
        TextView groupDiscount;
        TextView dealTime;
        TextView discount;
        ImageView standard;
        ImageView dealleft_img;
        ImageView dealleft_img2;
        ImageView coupon_img;
        ConstraintLayout constraintLayout;
        TextView groupPeoples;
        CardView timeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeLayout = itemView.findViewById(R.id.time_layout);
            dealLeft = itemView.findViewById(R.id.dealleft);
            dealLeft2 = itemView.findViewById(R.id.dealleft_2);
            dealleft_img = itemView.findViewById(R.id.deal_left_img);
            dealleft_img2 = itemView.findViewById(R.id.deal_left_image);
            coupon_img = itemView.findViewById(R.id.coupon_img);

            dealsImage = itemView.findViewById(R.id.imageView4);

            constraintLayout = itemView.findViewById(R.id.constraintLayout);
            dealname = itemView.findViewById(R.id.resturant_names);

            discount = itemView.findViewById(R.id.discount_text);
            standard = itemView.findViewById(R.id.standard_image);


            groupDiscount = itemView.findViewById(R.id.groupdiscount);
            dealTime = itemView.findViewById(R.id.time_of_deal);
            textViewDiscountBannerCoupons = itemView.findViewById(R.id.coupons);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_of_resturants, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BranchDealModel deal = list.get(position);
        DealModel dealModel = deal.getDeal();
        try {
            String dealStatus = dealModel.getGetDeal() != null ? dealModel.getGetDeal() : "Get Deal";
            String minLimit = "0";
            if (dealModel.getMinPersonRequired() != null) {
                minLimit = dealModel.getMinPersonRequired();
            }
            Long dealLeft = dealModel.getDealsLeft();
            if (dealLeft != null) {
                holder.dealLeft.setText(context.getResources().getString(R.string.deals_left) + dealLeft);
                holder.dealLeft2.setText(context.getResources().getString(R.string.deals_left) + dealLeft);
            }
            if (dealModel.getType() != null && dealModel.getType().equalsIgnoreCase(Constant.GROUP)) {
                holder.dealLeft2.setVisibility(View.VISIBLE);
                holder.dealleft_img.setVisibility(View.VISIBLE);
                holder.textViewDiscountBannerCoupons.setVisibility(View.GONE);
                holder.coupon_img.setVisibility(View.GONE);
                holder.dealLeft.setVisibility(View.GONE);
                holder.dealleft_img2.setVisibility(View.GONE);
                holder.textViewDiscountBannerCoupons.setText(context.getResources().getString(R.string.deals_left) + dealLeft);
                holder.textViewDiscountBannerCoupons.setTextColor(context.getResources().getColor(R.color.redColor));
                holder.groupDiscount.setText(R.string.group_disc);
                holder.groupDiscount.setTextColor(context.getResources().getColor(R.color.groupColor));
                holder.discount.setText(dealModel.getGroupDiscountRate().toString() + context.getString(R.string.off));
                holder.standard.setImageDrawable(context.getResources().getDrawable(R.drawable.free_coupon));
                holder.discount.setBackgroundColor(context.getResources().getColor(R.color.groupColor));
                if (dealStatus.equalsIgnoreCase(Constant.CANCELDEAL)) {
                    holder.constraintLayout.setBackgroundColor(context.getResources().getColor(R.color.groupColor));
                    holder.dealTime.setTextColor(context.getResources().getColor(R.color.white_color));
                }
            } else {
                if (dealModel.getFreeCouponDiscount() > 0) {
                    holder.textViewDiscountBannerCoupons.setVisibility(View.VISIBLE);
                    holder.coupon_img.setVisibility(View.VISIBLE);
                    holder.dealLeft.setVisibility(View.VISIBLE);
                    holder.dealleft_img2.setVisibility(View.VISIBLE);
                    holder.dealLeft2.setVisibility(View.GONE);
                    holder.dealleft_img.setVisibility(View.GONE);
                } else {
                    holder.dealLeft2.setVisibility(View.VISIBLE);
                    holder.dealleft_img.setVisibility(View.VISIBLE);
                }
                if (dealModel.getDiscountRate() == 0 && dealModel.getFreeCouponDiscount() != null) {
                    holder.discount.setText(context.getString(R.string._1_love));
                } else {
                    holder.discount.setText(dealModel.getDiscountRate().toString() + context.getString(R.string.off));
                }
                holder.discount.setBackgroundColor(context.getResources().getColor(R.color.green_light));
                holder.groupDiscount.setText(R.string.standard_discount);
                holder.standard.setImageDrawable(context.getResources().getDrawable(R.drawable.standard_coupon));
                holder.groupDiscount.setTextColor(context.getResources().getColor(R.color.green_light));
                if (dealStatus.equalsIgnoreCase(Constant.CANCELDEAL)) {
                    holder.constraintLayout.setBackgroundColor(context.getResources().getColor(R.color.green_light));
                    holder.dealTime.setTextColor(context.getResources().getColor(R.color.white_color));
                }
            }

            new CountDownTimer(dealModel.getLeftTime() - System.currentTimeMillis(), 1000) { // adjust the milli seconds here
                @SuppressLint("DefaultLocale")
                public void onTick(long millisUntilFinished) {
                    holder.dealTime.setText(String.format("%02d : %02d : %02d",
                            TimeUnit.MILLISECONDS.toHours(millisUntilFinished) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millisUntilFinished)),
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                }

                public void onFinish() {
                    holder.timeLayout.setVisibility(View.GONE);
                }
            }.start();
//                }
//            }
            holder.dealname.setText(dealModel.getDealName());
            Glide.with(context)
                    .load(dealModel.getDealUrl().getUrl())
                    .override(350, 250)
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
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.itemView.setOnClickListener(view -> {
            String dealImage = dealModel.getDealUrl().getUrl();
            Intent intent = new Intent(context, DealsMenu.class);
            Bundle bundle1 = new Bundle();
            bundle1.putInt("branch_index", branchPosition);
            bundle1.putInt("deal_index", position);
            bundle1.putParcelable("restaurants", homeBranchDealsModel);
            if (dealImage != null)
                intent.putExtra("img", dealImage);
            intent.putExtras(bundle1);
            intent.putExtra("deal_object", deal);
            if (activity == null)
                fragment.startActivityForResult(intent, 123);
            else
                activity.startActivityForResult(intent, 123);
            if (activityName.equalsIgnoreCase(Constant.MapsActivity)){
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
