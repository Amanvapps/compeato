package com.appinnovates.campeat.adapter;

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
import com.appinnovates.campeat.listeners.ItemListener;
import com.appinnovates.campeat.model.FavoriteRestaurantModel;
import com.appinnovates.campeat.services.ImageService.ImageService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class SubscribeAdapter extends RecyclerView.Adapter<SubscribeAdapter.ViewHolder> {

    private final Context mContext;
    private List<FavoriteRestaurantModel> items;
    private ItemListener itemListener;
    private int itemLayout;
    onSubscribeInterFace onSubscribeInterFace;
/*
    private ActionListener<FavoriteRestaurantModel> actionListener;
*/
    private ImageService imageService;

    public SubscribeAdapter(Context context, List<FavoriteRestaurantModel> items, int itemLayout, ItemListener itemListener,onSubscribeInterFace onSubscribeInterFace) {
        this.items = items;
        this.itemLayout = itemLayout;
        mContext = context;
        this.itemListener = itemListener;
        this.onSubscribeInterFace=onSubscribeInterFace;
    }

    @NonNull
    @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }
    @NonNull
    @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try
        {
            final FavoriteRestaurantModel item = items.get(position);
            holder.textViewName.setText(item.getRestaurantModel().getRestaurantName());
            holder.itemView.setTag(item);
            holder.ratingBar.setText(item.getRestaurantModel().getAverageRating().toString());
            holder.itemView.setOnClickListener(v -> itemListener.onItemClicked(position));
            holder.location.setText(item.getRestaurantModel().getCity());
            holder.address.setText(item.getRestaurantModel().getStreetAddress());
            String distance = item.getRestaurantModel().getDistance().equals("Not Specified")?"Not Specified":String.format ("%.2f", item.getRestaurantModel().getDistance())+mContext.getString(R.string.kms);
            holder.distance.setText(distance+" , ");
            holder.heart.setOnClickListener(view -> onSubscribeInterFace.onSubscribeClick(position,item));
//        if (item.getRestaurantModel().getLogo().getUrl()!=null)
//        Picasso.get().load(item.getRestaurantModel().getLogo().getUrl()).resize(100,100).centerCrop().into(holder.image);
            if (item.getRestaurantModel().getLogo() != null) {
                Glide.with(mContext)
                        .load(item.getRestaurantModel().getLogo().getUrl())
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
                        .into(holder.image);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override public int getItemCount() {
        return items.size();
    }

/*    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }*/




    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final TextView ratingBar;
        public ImageView image;
        private TextView location;
        private TextView address;
        private TextView distance;
        private ImageView heart;
        //public TextView textRemainingTime;

        public ViewHolder(View itemView) {
            super(itemView);
            heart=itemView.findViewById(R.id.imageView5);
            image =  itemView.findViewById(R.id.img);
            textViewName =  itemView.findViewById(R.id.text_view_title);
            //textRemainingTime = (TextView) itemView.findViewById(R.id.time_remain);
            ratingBar = itemView.findViewById(R.id.my_rating);
            location=itemView.findViewById(R.id.location_text);
            address=itemView.findViewById(R.id.location_text_address);
            distance=itemView.findViewById(R.id.distance_text);
        }
    }

    public interface onSubscribeInterFace{
        void onSubscribeClick(int position,FavoriteRestaurantModel item);
    }
}
