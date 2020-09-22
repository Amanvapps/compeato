package com.appinnovates.campeat.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.receiptModel.DealBranch;
import com.appinnovates.campeat.model.receiptModel.Result;
import com.appinnovates.campeat.utils.Constant;

import java.util.List;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ReceiptViewHolder> {

    private Context context;
    private List<Result> list;

    public ReceiptAdapter(Context context, List<Result> list) {
        this.context = context;
        this.list = list;
    }

    class ReceiptViewHolder extends RecyclerView.ViewHolder {
        TextView description;
        TextView time;
        CardView cardView;
        TextView dealTime;
        TextView discount;
        ConstraintLayout constraintLayout;

        ReceiptViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.description);
            time = itemView.findViewById(R.id.scan2);
            cardView = itemView.findViewById(R.id.time_layout);
            dealTime = itemView.findViewById(R.id.time_of_deal);
            discount = itemView.findViewById(R.id.discount);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }

    @NonNull
    @Override
    public ReceiptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.receipt_layout, parent, false);
        return new ReceiptViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReceiptViewHolder holder, int position) {
        Result result = list.get(position);
        String branchName = result.getBranchId().getBranchName();
        String restaurantName = result.getBranchId().getRestaurantId().getRestaurantName();
        branchName = branchName.trim();
        restaurantName = restaurantName.trim();
        String restName = restaurantName.replaceAll("\\s+$", "");
        String branName = branchName.replaceAll("\\s+$", "");
        holder.description.setText(String.format("%s-%s", restName, branName));
        if (result.getBookingId() != null) {
            String date = result.getBookingId().getBookingDate().getIso().substring(0, 10);
            String time = result.getBookingId().getBookingDate().getIso().substring(11, 16);
            holder.time.setText(date + " " + time);
            if (result.getBookingId().getDealBranch() != null) {
                DealBranch dealBranch = result.getBookingId().getDealBranch();
                if (dealBranch.getDeal().getType().equalsIgnoreCase(Constant.STANDARD_)) {
                    holder.discount.setText(dealBranch.getDeal().getDiscountRate().toString() + context.getString(R.string.off));
                    holder.constraintLayout.setBackgroundColor(context.getResources().getColor(R.color.green_light));
                    holder.dealTime.setText(context.getString(R.string.standard));
                } else {
                    holder.discount.setText(dealBranch.getDeal().getGroupDiscountRate().toString() + context.getString(R.string.off));
                    holder.constraintLayout.setBackgroundColor(context.getResources().getColor(R.color.groupColor));
                    holder.dealTime.setText(context.getString(R.string.group_discount_));
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
