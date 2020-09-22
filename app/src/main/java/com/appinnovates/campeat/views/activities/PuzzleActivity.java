package com.appinnovates.campeat.views.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.adapter.CustomAdapter;
import com.appinnovates.campeat.bottomSheets.BottomSheetPuzzleResp;
import com.appinnovates.campeat.model.PuzzlePiece;
import com.appinnovates.campeat.services.AdService.Ad;
import com.appinnovates.campeat.services.AdService.AdGlobal;
import com.appinnovates.campeat.services.AdService.AdService;
import com.appinnovates.campeat.services.AdService.AddPointsDelegate;
import com.appinnovates.campeat.services.AdService.PointService;
import com.appinnovates.campeat.services.AdService.PointsDelegate;
import com.appinnovates.campeat.services.AdService.SettingType;
import com.appinnovates.campeat.services.AdService.TADPEntry;
import com.appinnovates.campeat.utils.CommonUtils;
import com.appinnovates.campeat.utils.GestureDetectGridView;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


public class PuzzleActivity extends AppCompatActivity implements BottomSheetPuzzleResp.OnEarnPointInterface, BottomSheetPuzzleResp.BottomSheetListener {

    private static final int COLUMNS = 3;
    private static final int DIMENTIONS = COLUMNS * COLUMNS;


    private static GestureDetectGridView mGridView;

    private static int mColumnWidth, mColumnHeight;

    public static final String UP = "up";
    public static final String DOWN = "down";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    private static PuzzlePiece[] puzzlePieces;
    private Bitmap bitmap;
    private static Ad ad;
    private TextView points;
    private TextView name;
    private TextView time;
    private TextView totalPoints;
    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.arrow_back_black);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        totalPoints = findViewById(R.id.txt_total_points);
        name = findViewById(R.id.txt_title);
        time = findViewById(R.id.txt_time);
        cardView = findViewById(R.id.cardView5);

        points = findViewById(R.id.txt_points);
        try {

            ad = AdGlobal.instance().getAd();

            ad.object.put("user_impressions", ad.user_impressions + 1);
            ad.object.saveInBackground();

            if (ad != null) {
                bitmap = ad.bitmap;
                ImageView hintIV = findViewById(R.id.hint_iv);
                hintIV.setImageBitmap(bitmap);
            }
            init();
            splitImage();
            scramble();
            setDimentions();

        } catch (Exception e) {
            e.printStackTrace();
        }
        name.setText(ad.name);
        setTime(ad.seconds, time);
    }

    private void setTime(int sec, TextView txtTime) {
        int seconds = sec % 60;
        int minutes = sec / 60;

        String secondsStr = String.valueOf(seconds);
        String minutesStr = String.valueOf(minutes);
        String text = "";
        if (seconds == 0 && minutes == 0) {
            text = secondsStr + " " + getResources().getString(R.string.sec);
        } else if (minutes == 0) {
            text = secondsStr + " " + getResources().getString(R.string.sec);
        } else if (seconds == 0) {
            text = minutesStr + " " + getResources().getString(R.string.mins);
        } else {
            text = minutesStr + " " + getResources().getString(R.string.mins) + ", " + secondsStr + " " + getResources().getString(R.string.sec);
        }

        txtTime.setText(text);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTotalPoints();
    }

    private void getTotalPoints() {
        new PointService().points(new PointsDelegate() {
            @Override
            public void onSuccess(ArrayList<TADPEntry> tadpEntries, ArrayList<TADPEntry> tadEntries, int totalTADPPoints, int totalTADPoints) {
                totalPoints.setText(totalTADPPoints + " " + getString(R.string.tadp));
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setDimentions() {
        ViewTreeObserver vto = mGridView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int displayWidth = mGridView.getMeasuredWidth();
                int displayHeight = mGridView.getMeasuredHeight();

                int statusbarHeight = getStatusBarHeight(getApplicationContext());
                int requiredHeight = displayHeight - statusbarHeight;

                mColumnWidth = displayWidth / COLUMNS;
                mColumnHeight = requiredHeight / COLUMNS;

                display(getApplicationContext());
            }
        });
        if (ad.points != 0) {
            points.setText(ad.points + " " + getString(R.string.tadp));
            cardView.setVisibility(View.VISIBLE);
        }
    }

    private int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }

    private void display(Context context) {
        ArrayList<ImageView> imageViews = new ArrayList<>();
        ImageView imageView;

        for (PuzzlePiece puzzlePiece : puzzlePieces) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageBitmap(puzzlePiece.bitmap);

            imageViews.add(imageView);
        }

        mGridView.setAdapter(new CustomAdapter(imageViews, mColumnWidth, mColumnHeight));
    }

    private void splitImage() {

        try {

            int rows = 3;
            int cols = 3;

            ImageView imageView = findViewById(R.id.imageView);

            int[] dimensions = getBitmapPositionInsideImageView(imageView);
            int croppedImageWidth = dimensions[2];
            int croppedImageHeight = dimensions[3];


            Bitmap croppedBitmap = Bitmap.createScaledBitmap(bitmap, croppedImageWidth, croppedImageHeight, true);

            // Calculate the with and height of the pieces
            int pieceWidth = croppedImageWidth / cols;
            int pieceHeight = croppedImageHeight / rows;

            // Create each bitmap piece and add it to the resulting array
            int i = 0;
            int yCoord = 0;
            for (int row = 0; row < rows; row++) {
                int xCoord = 0;
                for (int col = 0; col < cols; col++) {
                    // calculate offset for each piece
                    int offsetX = 0;
                    int offsetY = 0;

                    // apply the offset to each piece
                    Bitmap pieceBitmap = Bitmap.createBitmap(croppedBitmap, xCoord - offsetX, yCoord - offsetY, pieceWidth + offsetX, pieceHeight + offsetY);
                    PuzzlePiece piece = puzzlePieces[i];

//                // this bitmap will hold our final puzzle piece image
                    Bitmap puzzlePiece = Bitmap.createBitmap(pieceWidth + offsetX, pieceHeight + offsetY, Bitmap.Config.ARGB_8888);
                    piece.bitmap = puzzlePiece;
//                // draw path
                    Canvas canvas = new Canvas(puzzlePiece);
                    Path path = new Path();
                    path.moveTo(offsetX, offsetY);
                    if (row == 0) {
                        // top side piece
                        path.lineTo(pieceBitmap.getWidth()
                                , offsetY);
                    } else {
                        // top bump
                        path.lineTo(offsetX + (pieceBitmap.getWidth() - offsetX) / 3, offsetY);
                        path.cubicTo(offsetX + (pieceBitmap.getWidth() - offsetX) / 6, offsetY, offsetX + (pieceBitmap.getWidth() - offsetX) / 6 * 5, offsetY, offsetX + (pieceBitmap.getWidth() - offsetX) / 3 * 2, offsetY);
                        path.lineTo(pieceBitmap.getWidth(), offsetY);
                    }

                    if (col == cols - 1) {
                        // right side piece
                        path.lineTo(pieceBitmap.getWidth(), pieceBitmap.getHeight());
                    } else {
                        // right bump
                        path.lineTo(pieceBitmap.getWidth(), offsetY + (pieceBitmap.getHeight() - offsetY) / 3);
                        path.cubicTo(pieceBitmap.getWidth(), offsetY + (pieceBitmap.getHeight() - offsetY) / 6, pieceBitmap.getWidth(), offsetY + (pieceBitmap.getHeight() - offsetY) / 6 * 5, pieceBitmap.getWidth(), offsetY + (pieceBitmap.getHeight() - offsetY) / 3 * 2);
                        path.lineTo(pieceBitmap.getWidth(), pieceBitmap.getHeight());
                    }

                    if (row == rows - 1) {
                        // bottom side piece
                        path.lineTo(offsetX, pieceBitmap.getHeight());
                    } else {
                        // bottom bump
                        path.lineTo(offsetX + (pieceBitmap.getWidth() - offsetX) / 3 * 2, pieceBitmap.getHeight());
                        path.cubicTo(offsetX + (pieceBitmap.getWidth() - offsetX) / 6 * 5, pieceBitmap.getHeight(), offsetX + (pieceBitmap.getWidth() - offsetX) / 6, pieceBitmap.getHeight(), offsetX + (pieceBitmap.getWidth() - offsetX) / 3, pieceBitmap.getHeight());
                        path.lineTo(offsetX, pieceBitmap.getHeight());
                    }

                    if (col == 0) {
                        // left side piece
                        path.close();
                    } else {
                        // left bump
                        path.lineTo(offsetX, offsetY + (pieceBitmap.getHeight() - offsetY) / 3 * 2);
                        path.cubicTo(offsetX, offsetY + (pieceBitmap.getHeight() - offsetY) / 6 * 5, offsetX, offsetY + (pieceBitmap.getHeight() - offsetY) / 6, offsetX, offsetY + (pieceBitmap.getHeight() - offsetY) / 3);
                        path.close();
                    }
//
                    // mask the piece
                    Paint paint = new Paint();
                    paint.setColor(0XFF000000);
                    paint.setStyle(Paint.Style.FILL);

                    canvas.drawPath(path, paint);
                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                    canvas.drawBitmap(pieceBitmap, 0, 0, paint);

                    // draw a white border
                    Paint border = new Paint();
                    border.setColor(0X80FFFFFF);
                    border.setStyle(Paint.Style.STROKE);
                    border.setStrokeWidth(2.0f);
                    canvas.drawPath(path, border);

                    // draw a black border
                    border = new Paint();
                    border.setColor(0X80000000);
                    border.setStyle(Paint.Style.STROKE);
                    border.setStrokeWidth(2.0f);
                    canvas.drawPath(path, border);

                    i++;
                    xCoord += pieceWidth;
                }
                yCoord += pieceHeight;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int[] getBitmapPositionInsideImageView(ImageView imageView) {
        int[] ret = new int[4];

        if (imageView == null || imageView.getDrawable() == null)
            return ret;

        // Get image dimensions
        // Get image matrix values and place them in an array
        float[] f = new float[9];
        imageView.getImageMatrix().getValues(f);

        // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)
        final float scaleX = f[Matrix.MSCALE_X];
        final float scaleY = f[Matrix.MSCALE_Y];

        // Get the drawable (could also get the bitmap behind the drawable and getWidth/getHeight)
        final Drawable d = imageView.getDrawable();
        final int origW = d.getIntrinsicWidth();
        final int origH = d.getIntrinsicHeight();

        // Calculate the actual dimensions
        final int actW = Math.round(origW * scaleX);
        final int actH = Math.round(origH * scaleY);

        ret[2] = actW;
        ret[3] = actH;

        // Get image position
        // We assume that the image is centered into ImageView
        int imgViewW = imageView.getWidth();
        int imgViewH = imageView.getHeight();

        int top = (int) (imgViewH - actH) / 2;
        int left = (int) (imgViewW - actW) / 2;

        ret[0] = left;
        ret[1] = top;

        return ret;
    }

    private void scramble() {
        int index;
        PuzzlePiece temp;
        Random random = new Random();

        for (int i = puzzlePieces.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            temp = puzzlePieces[index];
            puzzlePieces[index] = puzzlePieces[i];
            puzzlePieces[i] = temp;
        }
    }

    private void init() {
        mGridView = findViewById(R.id.grid);
        mGridView.setNumColumns(COLUMNS);
        mGridView.setDelegate(this::moveTiles);

        puzzlePieces = new PuzzlePiece[DIMENTIONS];
        for (int i = 0; i < DIMENTIONS; i++) {
            PuzzlePiece piece = new PuzzlePiece();
            piece.tile = String.valueOf(i);
            puzzlePieces[i] = piece;
        }
        System.out.print(puzzlePieces.length);
    }

    private void swap(Context context, int position, int swap) {
        try {
            PuzzlePiece newPosition = puzzlePieces[position + swap];
            puzzlePieces[position + swap] = puzzlePieces[position];
            puzzlePieces[position] = newPosition;
            display(context);
            if (isSolved()) {
                addPoints(context);
            }
        } catch (Exception e){
            Log.i("Exception","Error :- "+e.getMessage());
        }

    }

    private void addPoints(final Context context) {
        CommonUtils.showProgress(context);
        int points = ad.points == 0 ? Integer.parseInt(Objects.requireNonNull(AdService.instance.settings.get(SettingType.PER_PUZZLE))) : ad.points;

        new PointService().addPoints(points, ad.client, null, ad.object, false, new AddPointsDelegate() {
            @Override
            public void success() {
                CommonUtils.hideProgress();
//                String message = context.getResources().getString(R.string.points_success, String.valueOf(ad.points));
                bottomSheet(ad);
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        try {
//                            Thread.sleep(Toast.LENGTH_LONG);
//                            finish();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });

            }


            @Override
            public void failure(String message) {
                CommonUtils.hideProgress();
                CommonUtils.showToast(context, message);
            }
        });
    }

    private void bottomSheet(Ad ad) {
        BottomSheetPuzzleResp bottomSheet = new BottomSheetPuzzleResp(this, this, ad);
        bottomSheet.setCancelable(false);
        bottomSheet.show(getSupportFragmentManager(), "bottomSheetpuzzle");
    }

    private boolean isSolved() {
        boolean solved = false;

        for (int i = 0; i < puzzlePieces.length; i++) {
            if (puzzlePieces[i].tile.equals(String.valueOf(i))) {
                solved = true;
            } else {
                solved = false;
                break;
            }
        }

        return solved;
    }

    public void moveTiles(Context context, String direction, int position) {
        //upper-left-corner-tile
        if (position == 0) {
            if (direction.equals(RIGHT)) {
                swap(context, position, 1);
            } else if (direction.equals(DOWN)) {
                swap(context, position, COLUMNS);
            } else {
                Toast.makeText(context, getString(R.string.invalid_move), Toast.LENGTH_SHORT).show();
            }
            //upper-center-tile
        } else if (position > 0 && position < COLUMNS - 1) {
            switch (direction) {
                case LEFT:
                    swap(context, position, -1);
                    break;
                case DOWN:
                    swap(context, position, COLUMNS);
                    break;
                case RIGHT:
                    swap(context, position, 1);
                    break;
                default:
                    Toast.makeText(context, getString(R.string.invalid_move), Toast.LENGTH_SHORT).show();
                    break;
            }
            //upper-right-corner-tile
        } else if (position == COLUMNS - 1) {
            if (direction.equals(LEFT)) {
                swap(context, position, -1);
            } else if (direction.equals(DOWN)) {
                swap(context, position, COLUMNS);
            } else {
                Toast.makeText(context, getString(R.string.invalid_move), Toast.LENGTH_SHORT).show();
            }
            //left-side-tile
        } else if (position > COLUMNS - 1 && position < DIMENTIONS - COLUMNS && position % COLUMNS == 0) {
            switch (direction) {
                case UP:
                    swap(context, position, -COLUMNS);
                    break;
                case RIGHT:
                    swap(context, position, 1);
                    break;
                case DOWN:
                    swap(context, position, COLUMNS);
                    break;
                default:
                    Toast.makeText(context, getString(R.string.invalid_move), Toast.LENGTH_SHORT).show();
                    break;
            }
            //Right-side and bottom-right-corner-tiles
        } else if (position == COLUMNS * 2 - 1 || position == COLUMNS * 3 - 1) {
            switch (direction) {
                case UP:
                    swap(context, position, -COLUMNS);
                    break;
                case LEFT:
                    swap(context, position, -1);
                    break;
                case DOWN:
                    //right-corner-tile
                    if (position <= DIMENTIONS - COLUMNS - 1) {
                        swap(context, position, COLUMNS);
                    } else {
                        Toast.makeText(context, getString(R.string.invalid_move), Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    Toast.makeText(context, getString(R.string.invalid_move), Toast.LENGTH_SHORT).show();
                    break;
            }
            //Bottom-left-corner-tile
        } else if (position == DIMENTIONS - COLUMNS) {
            if (direction.equals(UP)) {
                swap(context, position, -COLUMNS);
            } else if (direction.equals(RIGHT)) {
                swap(context, position, 1);
            } else {
                Toast.makeText(context, getString(R.string.invalid_move), Toast.LENGTH_SHORT).show();
            }
            //Bottom-center-tile
        } else if (position < DIMENTIONS - 1 && position > DIMENTIONS - COLUMNS) {
            switch (direction) {
                case UP:
                    swap(context, position, -COLUMNS);
                    break;
                case RIGHT:
                    swap(context, position, 1);
                    break;
                case LEFT:
                    swap(context, position, -1);
                    break;
                default:
                    Toast.makeText(context, getString(R.string.invalid_move), Toast.LENGTH_SHORT).show();
                    break;
            }
            //center-tile
        } else {
            switch (direction) {
                case UP:
                    swap(context, position, -COLUMNS);
                    break;
                case RIGHT:
                    swap(context, position, 1);
                    break;
                case LEFT:
                    swap(context, position, -1);
                    break;
                default:
                    swap(context, position, COLUMNS);
                    break;
            }
        }
    }

    @Override
    public void onPointsClicked() {
        finish();
    }

    @Override
    public void onButtonCancelDeal(String text) {

    }
}