package com.appinnovates.campeat.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
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
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.HomeBranchDeals.BranchDealModel;
import com.appinnovates.campeat.model.getAllDealsModel.DealModel;
import com.appinnovates.campeat.utils.Constant;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BranchDealsAdapter extends RecyclerView.Adapter<BranchDealsAdapter.ViewHolder> {

    private List<BranchDealModel> data;
    private OnItemClickListener mListener;
    private Context context;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh : mm : ss");


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public BranchDealsAdapter(List<BranchDealModel> data, Context context) {
        this.data = data;
        this.context=context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewDiscountBannerCoupons;
        ImageView dealsImage;
        TextView discount;
        TextView dealname;
        TextView dealLeft2;
        ImageView dealleft_img;
        ImageView dealleft_img2;
        ImageView coupon_img;
        TextView getdiscount;
        TextView groupDiscount;
        TextView dealTime;
        TextView groupPeoples;
        private ConstraintLayout constraintLayout;
        CardView timeLayout;
        ImageView tag;
        ImageView standard;
        TextView dealLeft;
        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            timeLayout = itemView.findViewById(R.id.time_layout);
            dealLeft = itemView.findViewById(R.id.dealleft);
            dealLeft2 = itemView.findViewById(R.id.dealleft_2);
            dealleft_img = itemView.findViewById(R.id.deal_left_img);
            dealleft_img2 = itemView.findViewById(R.id.deal_left_image);
            coupon_img= itemView.findViewById(R.id.coupon_img);
            dealname = itemView.findViewById(R.id.resturant_names);
            standard = itemView.findViewById(R.id.standard_image);
            dealsImage = itemView.findViewById(R.id.imageView4);
            discount = itemView.findViewById(R.id.discount_text);
            tag=itemView.findViewById(R.id.imageView9);
            dealsImage = itemView.findViewById(R.id.imageView4);
            dealLeft=itemView.findViewById(R.id.dealleft);



            groupDiscount=itemView.findViewById(R.id.groupdiscount);
            dealTime=itemView.findViewById(R.id.time_of_deal);
            constraintLayout=itemView.findViewById(R.id.constraintLayout);

            textViewDiscountBannerCoupons = itemView.findViewById(R.id.coupons);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }


    @NonNull
    @Override
    public BranchDealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_of_deals,parent,false);
        ViewHolder vh=new ViewHolder(view,mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull BranchDealsAdapter.ViewHolder holder, int position) {
        DealModel dealModel=data.get(position).getDeal();
        Long dealLeft=dealModel.getDealsLeft();

        String dealStatus = dealModel.getGetDeal() != null ? dealModel.getGetDeal() : "Get Deal";
        String minLimit = "0";
        if (dealModel.getMinPersonRequired() != null) {
            minLimit = dealModel.getMinPersonRequired();
        }
        if (dealLeft > 0) {
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
            if (dealStatus.equalsIgnoreCase(context.getResources().getString(R.string.cancel_deal))) {
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
            holder.discount.setText(dealModel.getDiscountRate().toString() + context.getString(R.string.off));
            holder.discount.setBackgroundColor(context.getResources().getColor(R.color.green_light));
            holder.groupDiscount.setText(R.string.standard_discount);
            holder.standard.setImageDrawable(context.getResources().getDrawable(R.drawable.standard_coupon));
            holder.groupDiscount.setTextColor(context.getResources().getColor(R.color.green_light));
            if (dealStatus.equalsIgnoreCase(context.getResources().getString(R.string.cancel_deal))) {
                holder.constraintLayout.setBackgroundColor(context.getResources().getColor(R.color.green_light));
                holder.dealTime.setTextColor(context.getResources().getColor(R.color.white_color));
            }
        }

        new CountDownTimer(dealModel.getLeftTime() - System.currentTimeMillis(), 1000) { // adjust the milli seconds here
            @SuppressLint("DefaultLocale")
            public void onTick(long millisUntilFinished) {
                Log.i("---UntilFinished:- ", String.valueOf(millisUntilFinished));
                holder.dealTime.setText(String.format("%02d : %02d : %02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                holder.timeLayout.setVisibility(View.GONE);
            }
        }.start();

        holder.dealname.setText(dealModel.getDealName());
        holder.discount.setText(dealModel.getDiscountRate().toString() + "% off");
        Glide.with(context)
                .load(dealModel.getDealUrl().getUrl())
                .override(400, 300)
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
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
