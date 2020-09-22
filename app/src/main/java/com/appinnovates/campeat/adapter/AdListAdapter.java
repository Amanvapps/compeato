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
import com.appinnovates.campeat.services.AdService.Ad;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class AdListAdapter extends RecyclerView.Adapter<AdListAdapter.ViewHolder> {


    private Context context;
    ArrayList<Ad> data;
    private OnItemClickInterface actionListener;

    public AdListAdapter( Context context, ArrayList<Ad> data,OnItemClickInterface actionListener) {

        this.actionListener=actionListener;
        this.context = context;
        this.data = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivYoutube,imageView;
        TextView txtTime,txtTitle,txtPoints,krw;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            krw=itemView.findViewById(R.id.krw_points);
            imageView = itemView.findViewById(R.id.img);
            ivYoutube = itemView.findViewById(R.id.iv_youtube);
            txtTime = itemView.findViewById(R.id.txt_time);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtPoints = itemView.findViewById(R.id.txt_points);
        }
    }

    @NonNull
    @Override
    public AdListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ad_list_item_layout, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdListAdapter.ViewHolder holder, int position) {

         Ad ad = data.get(position);
//        holder.ivYoutube.setVisibility(isSurvey ? View.GONE : View.VISIBLE);

//        int adpoints = ad.points == 0 ? Integer.parseInt(AdService.instance.settings.get(isSurvey ? SettingType.PER_SURVEY : SettingType.PER_QUIZ)) : ad.points;
        int adpoints = ad.points;

        String points = adpoints + " "+context.getString(R.string.tadp);
        holder.txtPoints.setText(points);
        holder.krw.setText(adpoints+context.getString(R.string.krw));
        holder.txtTitle.setText(ad.name);

/*         {
            Picasso.get().load(item.getPicture().getUrl()).centerCrop().resize(50, 50).into(holder.image);
        }*/
        if (ad.bitmap != null) {
            Glide.with(context)
                    .load(ad.bitmap)
                    .override(150, 150)
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
                    .into(holder.imageView);
            int sec=ad.seconds;
            setTime(sec,holder.txtTime);
        }

        if (ad.seconds == 0){
                int sec = ad.questionList != null ? ad.questionList.size() * 20 : 0;
                ad.seconds = sec;

        }else{
            setTime(ad.seconds,holder.txtTime);
        }
        holder.itemView.setOnClickListener(view -> actionListener.onItemClickListener(ad));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnItemClickInterface {
        void onItemClickListener(Ad ad);
    }

    private String getTime(int sec){
        int seconds = sec%60;
        int minutes = sec/60;

        String secondsStr = String.valueOf(seconds);
        String minutesStr = String.valueOf(minutes);
        String text = "";
        if (seconds == 0 && minutes == 0){
            text = secondsStr + " " + context.getResources().getString(R.string.sec);
        }else if (minutes == 0){
            text = secondsStr + " " + context.getResources().getString(R.string.sec);
        }else if (seconds == 0){
            text = minutesStr + " " + context.getResources().getString(R.string.mins);
        }else{
            text = minutesStr + " " + context.getResources().getString(R.string.mins) + ", " + secondsStr + " " + context.getResources().getString(R.string.sec);
        }
        return text;
    }

    private void setTime(int sec,TextView txtTime){

        String text = getTime(sec);

        txtTime.setText(text);

//        txtPoints.setText(" " + points + " TADP");
//        this.txtTime.setText(" "+ getTime(time) + " ");

    }

}
