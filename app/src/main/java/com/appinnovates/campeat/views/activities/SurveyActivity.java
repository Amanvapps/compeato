package com.appinnovates.campeat.views.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.adapter.QuestionAdapter;
import com.appinnovates.campeat.bottomSheets.BottomSheetSurveyResponse;
import com.appinnovates.campeat.services.AdService.Ad;
import com.appinnovates.campeat.services.AdService.AdGlobal;
import com.appinnovates.campeat.services.AdService.AdService;
import com.appinnovates.campeat.services.AdService.AddPointsDelegate;
import com.appinnovates.campeat.services.AdService.Option;
import com.appinnovates.campeat.services.AdService.PointService;
import com.appinnovates.campeat.services.AdService.PointsDelegate;
import com.appinnovates.campeat.services.AdService.Question;
import com.appinnovates.campeat.services.AdService.QuestionDelegate;
import com.appinnovates.campeat.services.AdService.QuestionService;
import com.appinnovates.campeat.services.AdService.SettingType;
import com.appinnovates.campeat.services.AdService.TADPEntry;
import com.appinnovates.campeat.services.ImageService.ImageService;
import com.appinnovates.campeat.services.ImageService.ImageServiceViewInterface;
import com.appinnovates.campeat.utils.CommonUtils;

import java.util.ArrayList;

public class SurveyActivity extends AppCompatActivity implements BottomSheetSurveyResponse.BottomSheetListener, BottomSheetSurveyResponse.OnSurveyInterface {
    private TextView txtPoints;
    private TextView txtTotalPoints;
    private TextView txtName;
    private TextView txtTime;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private Button btnSubmit;
    private ArrayList<Question> questionList = new ArrayList<>();
    private QuestionAdapter adapter;
    private Ad ad;
    private TextView points;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        points=findViewById(R.id.txt_total_points);
        Toolbar toolbar=findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.arrow_back_black);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        initViews();
        setUpData();
        setUpRecyclerView();
        getPoints();
        setListeners();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getTotalPoints();
    }

    private void getTotalPoints() {
        new PointService().points(new PointsDelegate() {
            @Override
            public void onSuccess(ArrayList<TADPEntry> tadpEntries, ArrayList<TADPEntry> tadEntries, int totalTADPPoints, int totalTADPoints) {
                points.setText(totalTADPPoints + " "+getString(R.string.tadp));
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    private void setListeners(){
        btnSubmit.setOnClickListener(v -> {

            if (questionList.size() == 0){
                return;
            }

            for (Question question:questionList){
                boolean isSelected = false;
                for (Option option : question.optionList) {
                    if (option.isSelected || (question.answer != null && !question.answer.equalsIgnoreCase("")) ) {
                        isSelected = true;
                        break;
                    }

                }
                if (!isSelected){
                    String error = getResources().getString(R.string.select_answers);
                    CommonUtils.showToast(SurveyActivity.this,error);
                    return;
                }
            }

            AdService.instance.addSurveys(questionList);

            addPoints();
        });
    }

    private void setUpData(){
        ad = AdGlobal.instance().getAd();

        ad.object.put("user_impressions",ad.user_impressions + 1);
        ad.object.saveInBackground();
        setTime(ad.seconds,txtTime);
        String points = ad.points + " "+getString(R.string.tadp);
        txtPoints.setText(points);
        txtName.setText(ad.name);

        new QuestionService().questions(ad.object, new QuestionDelegate() {
            @Override
            public void questions(ArrayList<Question> questions) {
                questionList.clear();
                questionList.addAll(questions);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(String message) {

            }
        });
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
                        ad.bitmap = bitmap;
                        imageView.setImageBitmap(bitmap);
                    }
                }

                @Override
                public void onImageServiceFailureForRow(String message, int position) {

                }
            });
            imageService.fetchImageForRow(ad.attachment,0, imageView);


        } else {
            imageView.setImageBitmap(ad.bitmap);
        }

/*        try{

            if (ad.bitmap != null){
                imageView.setImageBitmap(ad.bitmap);
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/
    }

    private void getPoints(){
        new PointService().points(new PointsDelegate() {
            @Override
            public void onSuccess(ArrayList<TADPEntry> tadpEntries,ArrayList<TADPEntry> tadEntries,int totalTADPPoints,int totalTADPoints) {
                txtTotalPoints.setText(totalTADPPoints+ " "+getString(R.string.tadp));
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    private void addPoints(){
        CommonUtils.showProgress(SurveyActivity.this);

        int points = ad.points == 0 ? Integer.parseInt(AdService.instance.settings.get(SettingType.PER_SURVEY)) : ad.points;

        new PointService().addPoints(points,ad.client,null,ad.object,false, new AddPointsDelegate() {
            @Override
            public void success() {
                CommonUtils.hideProgress();
                getPoints();
/*                String message = getResources().getString(R.string.points_success,String.valueOf(ad.points));
                Toast.makeText(SurveyActivity.this, message, Toast.LENGTH_LONG).show();*/
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(Toast.LENGTH_LONG);
                            surveyResponse();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void failure(String message) {
                CommonUtils.hideProgress();
                CommonUtils.showToast(SurveyActivity.this,message);
            }
        });
    }

    void surveyResponse(){
        BottomSheetSurveyResponse bottomSheetSurveyResponse=new BottomSheetSurveyResponse(this,this,ad.points);
        bottomSheetSurveyResponse.setCancelable(false);
        bottomSheetSurveyResponse.show(getSupportFragmentManager(),"surveyFragment");
    }

    private void setTime(int sec,TextView txtTime) {

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

    private void setUpRecyclerView(){
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        adapter = new QuestionAdapter(getApplicationContext(),questionList,true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void initViews(){
/*        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/
        txtPoints = findViewById(R.id.txt_points);
        txtTotalPoints = findViewById(R.id.txt_total_points);
        txtName = findViewById(R.id.txt_title);
        txtTime = findViewById(R.id.txt_time);
        recyclerView = findViewById(R.id.recycler_view);
        btnSubmit = findViewById(R.id.btn_submit);
        imageView = findViewById(R.id.img);
    }

    @Override
    public void onSurveyClicked() {
        finish();
    }

    @Override
    public void onButtonCancelDeal(String text) {

    }
}
