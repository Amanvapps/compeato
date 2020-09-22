package com.appinnovates.campeat.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.Banner.Result;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> implements LoopingPagerAdapter {
    Context context;
    private List<Result> list;
    private LayoutInflater layoutInflater;
    private OnBannerInterface onBannerInterface;


    public BannerAdapter(Context context, List<Result> list, OnBannerInterface onBannerInterface) {
        this.context = context;
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.onBannerInterface = onBannerInterface;
    }

   /* @Override
=======
    @Override
>>>>>>> b56eb97d8e5401c75425aa8948277d212fe5f235
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.banner_layout, container, false);

        Result couponModelResult = list.get(position);
<<<<<<< HEAD
        container.addView(itemView);
        ImageView imageView = itemView.findViewById(R.id.banner_image);
        if (couponModelResult.getBanner() != null)
            Glide.with(context)
                    .load(couponModelResult.getBanner().getUrl())
                    .override(400, 400)
=======

        ImageView imageView = itemView.findViewById(R.id.banner_image);

        container.addView(itemView);
        if (couponModelResult.getBanner() != null)
            Glide.with(context)
                    .load(couponModelResult.getBanner().getUrl())
                    .override(300, 250)
>>>>>>> b56eb97d8e5401c75425aa8948277d212fe5f235
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
                    .into(imageView);


        //listening to image click
        imageView.setOnClickListener(v -> {
            onBannerInterface.onBannerClicked(couponModelResult,position);
        });
<<<<<<< HEAD
=======

>>>>>>> b56eb97d8e5401c75425aa8948277d212fe5f235
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout) object);
<<<<<<< HEAD
    }*/

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.banner_image);
        }
    }

    public interface OnBannerInterface {
        void onBannerClicked(Result result, int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.banner_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list.size() != 0) {
            int index = position % list.size();
            Result result = list.get(index);
            if (result.getBanner() != null)
                Glide.with(context)
                        .load(result.getBanner().getUrl())
                        .override(700, 700)
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
                        .into(holder.imageView);

            holder.imageView.setOnClickListener(v -> {
                onBannerInterface.onBannerClicked(result, index);
            });
        }
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    @Override
    public int getRealCount() {
        return list.size();
    }
}