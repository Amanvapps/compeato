package com.appinnovates.campeat.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.HomeBranchDeals.HomeBranchDealsModel;
import com.appinnovates.campeat.utils.GetBranchTiming;

import java.util.ArrayList;
import java.util.List;

public class BranchInfoAdapter extends RecyclerView.Adapter<BranchInfoAdapter.BranchViewHolder> {

    private Context context;
    private List<HomeBranchDealsModel> homeBranchDealsModel;
    private String getAll = "";
    private int i1;

    public BranchInfoAdapter(Context context, List<HomeBranchDealsModel> homeBranchDealsModel) {
        this.homeBranchDealsModel = homeBranchDealsModel;
        this.context = context;
    }

    class BranchViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout infoClick;
        private View informationDetails;
        private AppCompatImageView infoIcon;
        private TextView branchName;
        private TextView openingHours;
        private TextView phone;
        Animation open, close, slide_up, slide_down;

        BranchViewHolder(@NonNull View itemView) {
            super(itemView);
            openingHours = itemView.findViewById(R.id.opening_hours_timing);
            infoClick = itemView.findViewById(R.id.info_click);
            infoIcon = itemView.findViewById(R.id.item_image);
            informationDetails = itemView.findViewById(R.id.information_details);
            branchName = itemView.findViewById(R.id.branch_name);
            phone = itemView.findViewById(R.id.phone_number);
        }
    }

    @NonNull
    @Override
    public BranchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.branch_info_layout, parent, false);
        return new BranchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BranchViewHolder holder, int position) {
        HomeBranchDealsModel data = homeBranchDealsModel.get(position);
        GetBranchTiming getBranchTiming=new GetBranchTiming(context);
        getAll=getBranchTiming.getBranchTime(data.getOperational());
        holder.openingHours.setText(getAll);
        holder.branchName.setText(data.getBranchName());
        holder.phone.setText(data.getPhone());
        holder.infoClick.setOnClickListener(v -> {
            holder.open = AnimationUtils.loadAnimation(context, R.anim.open_menu);
            holder.close = AnimationUtils.loadAnimation(context, R.anim.close_menu);
            holder.slide_up = AnimationUtils.loadAnimation(context, R.anim.slide_up);
            holder.slide_down = AnimationUtils.loadAnimation(context, R.anim.slide_down);
            if (holder.informationDetails.getVisibility() == View.VISIBLE) {
                holder.informationDetails.setAnimation(holder.slide_up);
                holder.infoIcon.setAnimation(holder.close);
                holder.informationDetails.setVisibility(View.GONE);
            } else {
                holder.infoIcon.setAnimation(holder.open);
                holder.informationDetails.setAnimation(holder.slide_down);
                holder.informationDetails.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeBranchDealsModel.size();
    }
}
