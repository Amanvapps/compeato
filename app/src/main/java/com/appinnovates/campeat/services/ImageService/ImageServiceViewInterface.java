package com.appinnovates.campeat.services.ImageService;

import android.graphics.Bitmap;
import android.widget.ImageView;

public interface ImageServiceViewInterface {
    void onImageServiceSuccess(Bitmap bitmap);
    void onImageServiceFailure(String message);
    void onImageServiceSuccessForRow(Bitmap bitmap,int position,ImageView imageView);
    void onImageServiceFailureForRow(String message,int position);
}
