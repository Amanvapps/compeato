package com.appinnovates.campeat.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.listeners.AdListener;
import com.appinnovates.campeat.services.AdService.Ad;
import com.appinnovates.campeat.services.AdService.AdService;
import com.appinnovates.campeat.services.AdService.SettingType;
import com.appinnovates.campeat.services.ImageService.ImageService;
import com.appinnovates.campeat.services.ImageService.ImageServiceViewInterface;

import java.util.ArrayList;
import java.util.HashMap;

public class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.PointsVH> {

    private ArrayList<Ad> ads;
    private Context context;
    private boolean isSurvey = false;
    private AdListener listener;
    private int layout;
    private GoogleRewardClickInterface googleRewardClickInterface;

    public PointsAdapter(Context context, boolean isSurvey, ArrayList<Ad> ads, AdListener listener, int layout,GoogleRewardClickInterface googleRewardClickInterface) {
        this.context = context;
        this.isSurvey = isSurvey;
        this.ads = ads;
        this.listener = listener;
        this.layout = layout;
        this.googleRewardClickInterface=googleRewardClickInterface;
    }

    @NonNull
    @Override
    public PointsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new PointsVH(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final PointsVH holder, int position) {
        try {
            if (position == 0) {
                String points = "500 " + context.getString(R.string.tadp);
                holder.txtPoints.setText(points);
                holder.itemView.setOnClickListener(v -> googleRewardClickInterface.googleRewardClick());
                holder.krw.setText("500" + context.getString(R.string.krw));
                holder.txtTitle.setText("Google Ads");
                holder.imageView.setImageDrawable(context.getDrawable(R.drawable.image_rewarded_ads));
                holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                holder.ivYoutube.setVisibility(View.GONE);
            } else {
                final Ad ad = ads.get(position);
                holder.ivYoutube.setVisibility(isSurvey ? View.GONE : View.VISIBLE);

                int adpoints = ad.points == 0 ? Integer.parseInt(AdService.instance.settings.get(isSurvey ? SettingType.PER_SURVEY : SettingType.PER_QUIZ)) : ad.points;
                String points = adpoints + " " + context.getString(R.string.tadp);
                holder.txtPoints.setText(points);
                holder.krw.setText(adpoints + " " + context.getString(R.string.krw));
                holder.txtTitle.setText(ad.name);

                holder.itemView.setOnClickListener(v -> listener.onClick(ad));
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
                                ads.get(position).bitmap = bitmap;
                                imageView.setImageBitmap(bitmap);
                            }
                        }

                        @Override
                        public void onImageServiceFailureForRow(String message, int position) {

                        }
                    });
                    imageService.fetchImageForRow(ad.attachment, position, holder.imageView);


                } else {
                    holder.imageView.setImageBitmap(ad.bitmap);
//                    int sec = ad.questionList.size() * 20;
//                    setTime(sec,holder.txtTime);
                }

                if (ad.seconds == 0) {
                    if (isSurvey) {
                        int sec = ad.questionList != null ? ad.questionList.size() * 20 : 0;
                        ad.seconds = sec;
                        setTime(sec, holder.txtTime);
                    } else {
                        new LongOperation(ad, holder.txtTime).execute(ad.attachment_link);
                    }
                } else {
                    setTime(ad.seconds, holder.txtTime);
                }
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void setTime(int sec, TextView txtTime) {
        String text = getTime(sec);

        txtTime.setText(text);
    }

    private String getTime(int sec) {
        int seconds = sec % 60;
        int minutes = sec / 60;

        String secondsStr = String.valueOf(seconds);
        String minutesStr = String.valueOf(minutes);
        String text = "";
        if (seconds == 0 && minutes == 0) {
            text = secondsStr + " " + context.getResources().getString(R.string.sec);
        } else if (minutes == 0) {
            text = secondsStr + " " + context.getResources().getString(R.string.sec);
        } else if (seconds == 0) {
            text = minutesStr + " " + context.getResources().getString(R.string.mins);
        } else {
            text = minutesStr + " " + context.getResources().getString(R.string.mins) + ", " + secondsStr + " " + context.getResources().getString(R.string.sec);
        }
        return text;
    }


    @Override
    public int getItemCount() {
        return ads == null ? 0 : ads.size();
    }

    class PointsVH extends RecyclerView.ViewHolder {

        ImageView ivYoutube, imageView;
        TextView txtTime, txtTitle, txtPoints, krw;

        PointsVH(View itemView) {
            super(itemView);
            krw = itemView.findViewById(R.id.krw_points);
            imageView = itemView.findViewById(R.id.img);
            ivYoutube = itemView.findViewById(R.id.iv_youtube);
            txtTime = itemView.findViewById(R.id.txt_time);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtPoints = itemView.findViewById(R.id.txt_points);
        }
    }


    private class LongOperation extends AsyncTask<String, Void, Ad> {

        private Ad ad;
        private TextView txtTime;

        public LongOperation(Ad ad, TextView txtTime) {
            this.ad = ad;
            this.txtTime = txtTime;
        }

        @Override
        protected Ad doInBackground(String... params) {
            MediaMetadataRetriever mediaMetadataRetriever = null;
            int seconds = 0;
            try {
                String videoPath = params[0];
                mediaMetadataRetriever = new MediaMetadataRetriever();

                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
//                bitmap = mediaMetadataRetriever.getFrameAtTime();
                String time = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                int timeInMillisec = Integer.parseInt(time);
                seconds = (timeInMillisec / 1000);
            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                if (mediaMetadataRetriever != null) {
                    mediaMetadataRetriever.release();
                }
            }
            ad.seconds = seconds;
            return ad;
        }

        @Override
        protected void onPostExecute(Ad ad) {
            setTime(ad.seconds, txtTime);
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    public interface GoogleRewardClickInterface{
        void googleRewardClick();
    }

}


