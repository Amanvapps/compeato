package com.appinnovates.campeat.services.ImageService;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

public class ImageService {
    private ImageServiceViewInterface imageServiceViewDelegate;

    public ImageService(ImageServiceViewInterface imageServiceViewDelegate){
        this.imageServiceViewDelegate = imageServiceViewDelegate;
    }

    public void fetchImage(ParseFile imageFile){
        imageFile.getDataInBackground((data, e) -> {
            if (e == null){
                //getting image data
                Bitmap bitmap = BitmapFactory
                        .decodeByteArray(
                                data, 0,
                                data.length);
                imageServiceViewDelegate.onImageServiceSuccess(bitmap);
            }else {
                imageServiceViewDelegate.onImageServiceFailure(e.getMessage());
            }
        });
    }

    public void fetchImageForRow(ParseFile imageFile, final int position, final ImageView imageView){
        if (imageFile == null)
            return;
        imageFile.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, ParseException e) {
                if (e == null){
                    if (data!= null && data.length>0) {
                        //getting image data
                        Bitmap bitmap = BitmapFactory
                                .decodeByteArray(
                                        data, 0,
                                        data.length);
                        imageServiceViewDelegate.onImageServiceSuccessForRow(bitmap, position, imageView);
                    } else {
                        imageServiceViewDelegate.onImageServiceFailureForRow("",position);
                    }
                }else {
                    imageServiceViewDelegate.onImageServiceFailureForRow(e.getMessage(),position);
                }
            }
        });
    }
}
