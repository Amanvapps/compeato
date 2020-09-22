package com.appinnovates.campeat.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.services.AdService.Ad;
import com.appinnovates.campeat.services.AdService.AdGlobal;
import com.appinnovates.campeat.services.AdService.AdService;
import com.appinnovates.campeat.services.AdService.SettingType;
import com.appinnovates.campeat.services.ImageService.ImageService;
import com.appinnovates.campeat.services.ImageService.ImageServiceViewInterface;
import com.appinnovates.campeat.views.activities.PuzzleActivity;

import java.util.ArrayList;

public class PuzzleAdapter extends RecyclerView.Adapter<PuzzleAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Ad> puzzles;
    private int itemLayout;

    public PuzzleAdapter(Context context, ArrayList<Ad> puzzles,int itemLayout) {
        this.context = context;
        this.puzzles = puzzles;
        this.itemLayout=itemLayout;
    }

    private void setTime(int sec,TextView txtTime){
        String text = getTime(sec);
        txtTime.setText(text);
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(itemLayout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Ad ad = puzzles.get(position);

        int points = ad.points == 0 ? Integer.parseInt(AdService.instance.settings.get(SettingType.PER_PUZZLE)) : ad.points;
        holder.txtPoints.setText(points + " "+context.getString(R.string.tadp));
        holder.krw.setText(points +" "+ context.getString(R.string.krw));
        holder.title.setText(ad.name);
        setTime(ad.seconds,holder.time);

        if (ad.bitmap == null) {
            ImageService imageService = new ImageService(new ImageServiceViewInterface() {
                @Override
                public void onImageServiceSuccess(Bitmap bitmap) {

                }

                @Override
                public void onImageServiceFailure(String message) {

                }

                @Override
                public void onImageServiceSuccessForRow(Bitmap bitmap, int position, ImageView imageView) {
                    if (bitmap != null) {
                        puzzles.get(position).bitmap = bitmap;
                        imageView.setImageBitmap(bitmap);
                    }
                }

                @Override
                public void onImageServiceFailureForRow(String message, int position) {

                }
            });
            imageService.fetchImageForRow(ad.attachment, position, holder.iv);
        } else {
            holder.iv.setImageBitmap(ad.bitmap);
        }


        holder.itemView.setOnClickListener(v -> {
            AdGlobal.instance().setAd(ad);
            context.startActivity(new Intent(context, PuzzleActivity.class).putExtra(context.getResources().getString(R.string.points),ad.points));
        });


    }

    @Override
    public int getItemCount() {
        return puzzles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtPoints;
        ImageView iv;
        TextView krw;
        TextView title;
        TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            txtPoints = itemView.findViewById(R.id.txt_points);
            iv = itemView.findViewById(R.id.img);
            krw=itemView.findViewById(R.id.krw_points);
            title=itemView.findViewById(R.id.txt_title);
            time=itemView.findViewById(R.id.txt_time);
        }
    }
}
