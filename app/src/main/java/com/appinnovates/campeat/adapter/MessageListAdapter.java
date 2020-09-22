package com.appinnovates.campeat.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.listeners.ItemListener;
import com.appinnovates.campeat.model.HomeBranchDeals.HomeBranchDealsModel;
import com.appinnovates.campeat.services.ImageService.ImageService;
import com.appinnovates.campeat.services.ImageService.ImageServiceViewInterface;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder>
        implements ImageServiceViewInterface {

    private final Context context;
    private int itemLayout;
    private List<HomeBranchDealsModel> branchDealModelList;
    private ImageService imageService;
    private ItemListener itemListener;

    public MessageListAdapter(Context context, int itemLayout, List<HomeBranchDealsModel> dealsModelList
            , ItemListener  itemListener) {
        this.itemLayout = itemLayout;
        this.context = context;
        this.branchDealModelList = dealsModelList;
        imageService = new ImageService(this);
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        /*if (branchDealModelList != null){
            List<DealModel> dealsModel = branchDealModelList.get(position).getDealModel();
            BranchDealsModel branchModel = branchDealModelList.get(position).getBranchDealModels();
            imageService.fetchImageForRow(dealsModel.getImageFile(),position,holder.image);
            if (branchModel.getRestaurantModel() != null) {
                holder.txtName.setText(branchModel.getRestaurantModel().getRestaurant_name());
                holder.txtDescription.setText(branchModel.getRestaurantModel().getDescription());
            }
            String discount = dealsModel.getDiscount_rate()+"% "+context.getResources().getString(R.string.discount)+" from"+ dealsModel.getStart_time()+"-"+ dealsModel.getEnd_time();
            holder.txtDiscount.setText(discount);
            holder.txtTime.setText(DateFormatUtil.getTime(dealsModel.getCreatedAt()));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemListener.onItemClicked(position);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        if (branchDealModelList !=null){
        return branchDealModelList.size();
        }else{
            return 0;
        }
    }

    @Override
    public void onImageServiceSuccess(Bitmap bitmap) {

    }

    @Override
    public void onImageServiceFailure(String message) {

    }

    @Override
    public void onImageServiceSuccessForRow(Bitmap bitmap, int position, ImageView imageView) {
        if (bitmap!=null && imageView !=null)
            imageView.setImageBitmap(bitmap);
    }

    @Override
    public void onImageServiceFailureForRow(String message, int position) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView txtName,txtDiscount,txtDescription,txtTime;
        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.text_view_name);
            txtDescription = itemView.findViewById(R.id.text_view_desc);
            txtDiscount = itemView.findViewById(R.id.text_view_discount);
            txtTime = itemView.findViewById(R.id.text_view_time);
            image = itemView.findViewById(R.id.image_item);
        }
    }
}