package com.appinnovates.campeat.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.ReviewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private final List<ReviewModel> reviewModelList;
    private int itemLayout;
    private Context context;

    MaterialCardView cardView;

    public ReviewAdapter(int itemLayout, List<ReviewModel> reviewModelList, Context context) {
        this.itemLayout = itemLayout;
        this.reviewModelList = reviewModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ReviewModel reviewModel = reviewModelList.get(position);
        holder.textViewReview.setText(reviewModel.getReview());
        String name = reviewModel.getUserModel().getName();
/*        if (reviewModel.getBranch_Model() != null)
            holder.textViewBranch.setText(reviewModel.getBranch_Model().getBranchName());*/
        if (reviewModel.getUserModel().getSurname() != null) {
            name = reviewModel.getUserModel().getName() + " " + reviewModel.getUserModel().getSurname();
        }
        holder.textViewUserName.setText(name);
        holder.ratingBar.setRating(reviewModel.getReview_point());
/*        Glide.with(context)
                .load(reviewModel.getReview())
                .override(200, 200)
                .error(R.drawable.image_not_found)
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
                .into(holder.imageViewProfile);*/
    }

    @Override
    public int getItemCount() {
        return reviewModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final RatingBar ratingBar;
        private final TextView textViewReview;
        private final TextView textViewUserName;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewReview = itemView.findViewById(R.id.text_view_review);
            textViewUserName = itemView.findViewById(R.id.text_view_user_name);
            ratingBar = itemView.findViewById(R.id.rating_bar);
        }
    }
}