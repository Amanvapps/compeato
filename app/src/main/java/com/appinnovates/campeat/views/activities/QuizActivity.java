package com.appinnovates.campeat.views.activities;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.adapter.QuestionAdapter;
import com.appinnovates.campeat.bottomSheets.BottomSheetQuizResponse;
import com.appinnovates.campeat.services.AdService.Ad;
import com.appinnovates.campeat.services.AdService.AdGlobal;
import com.appinnovates.campeat.services.AdService.AddPointsDelegate;
import com.appinnovates.campeat.services.AdService.Option;
import com.appinnovates.campeat.services.AdService.PointService;
import com.appinnovates.campeat.services.AdService.PointsDelegate;
import com.appinnovates.campeat.services.AdService.Question;
import com.appinnovates.campeat.services.AdService.QuestionDelegate;
import com.appinnovates.campeat.services.AdService.QuestionService;
import com.appinnovates.campeat.services.AdService.TADPEntry;
import com.appinnovates.campeat.utils.CommonUtils;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity implements BottomSheetQuizResponse.OnEarnPointInterface, BottomSheetQuizResponse.BottomSheetListener {

    private TextView txtPoints, txtTotalPoints, txtName, txtTime;
    private VideoView videoView;
    private RecyclerView recyclerView;
    private Button btnSubmit;
    private ArrayList<Question> questionList = new ArrayList<>();
    private QuestionAdapter adapter;
    private Ad ad;
    private TextView points;
    private int number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.arrow_back_black);
        points = findViewById(R.id.txt_total_points);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        initViews();
        setData();
        setUpRecyclerView();
        setUpVideo();
        getPoints();
        setListeners();

    }



/*
    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(getString(R.string.admobs_id),
                new AdRequest.Builder().build());
    }
*/

    @Override
    protected void onResume() {
        super.onResume();
        getTotalPoints();
    }

    private void getTotalPoints() {
        new PointService().points(new PointsDelegate() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(ArrayList<TADPEntry> tadpEntries, ArrayList<TADPEntry> tadEntries, int totalTADPPoints, int totalTADPoints) {
                points.setText(totalTADPPoints + " " + getString(R.string.tadp));
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }


    private void setListeners() {
        btnSubmit.setOnClickListener(v -> {

            if (questionList.size() == 0) {
                return;
            }

            for (int index = 0; index < questionList.size(); index++) {
                Question question = questionList.get(index);
                for (Option option : question.optionList) {
                    question.isCorrect = false;
                    if (option.isCorrect) {
                        question.isCorrect = option.isSelected;
                        break;
                    }
                }

                if (!question.isCorrect) {
                    number = index + 1;
                    /*String error = getResources().getString(R.string.question_error,number);
                    CommonUtils.showToast(QuizActivity.this,error);*/
                }

            }
            int size = questionList.size();


            int correct = size - number;

            int getPoints = (ad.points / size) * correct;
//            addPoints(getPoints);

            bottomsheetquiz(correct, size, getPoints);
            number = 0;

        });
    }

    void bottomsheetquiz(int number, int size, int points) {
        BottomSheetQuizResponse bottomSheetQuizResponse = new BottomSheetQuizResponse(this, this, number, size, points);
        bottomSheetQuizResponse.show(getSupportFragmentManager(), "bottomsheetquiz");
    }

    private void setData() {

        ad = AdGlobal.instance().getAd();

        ad.object.put("user_impressions", ad.user_impressions + 1);
        ad.object.saveInBackground();

        setTime(ad.seconds, txtTime);
        String points = ad.points + " " + getString(R.string.tadp);
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
    }

    private void setUpRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        adapter = new QuestionAdapter(getApplicationContext(), questionList, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void setUpVideo() {
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        //specify the location of media file
        Uri uri = Uri.parse(ad.attachment_link);

        //Setting MediaController and URI, then starting the videoView
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
    }

    private void getPoints() {
        new PointService().points(new PointsDelegate() {
            @Override
            public void onSuccess(ArrayList<TADPEntry> tadpEntries, ArrayList<TADPEntry> tadEntries, int totalTADPPoints, int totalTADPoints) {
                txtTotalPoints.setText(totalTADPPoints + getString(R.string.tadp));
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    private void addPoints(int points) {
        CommonUtils.showProgress(QuizActivity.this);
//        int points = ad.points == 0 ? Integer.parseInt(AdService.instance.settings.get(SettingType.PER_QUIZ)) : ad.points;

        new PointService().addPoints(points, ad.client, null, ad.object, false, new AddPointsDelegate() {
            @Override
            public void success() {
                //CommonUtils.hideProgress();
                getPoints();
                String message = getResources().getString(R.string.points_success, String.valueOf(points));
                Toast.makeText(QuizActivity.this, message, Toast.LENGTH_LONG).show();
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(Toast.LENGTH_LONG);
                            finish();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }

            @Override
            public void failure(String message) {
                CommonUtils.hideProgress();
                CommonUtils.showToast(QuizActivity.this, message);
            }
        });
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

    private void initViews() {
        txtPoints = findViewById(R.id.txt_points);
        txtTotalPoints = findViewById(R.id.txt_total_points);
        txtName = findViewById(R.id.txt_title);
        txtTime = findViewById(R.id.txt_time);
        recyclerView = findViewById(R.id.recycler_view);
        btnSubmit = findViewById(R.id.btn_submit);
        videoView = findViewById(R.id.video_view);
    }

    @Override
    public void onButtonCancelDeal(String text) {

    }

    @Override
    public void onPointsClicked(int points) {
        addPoints(points);
        finish();
    }

    //Admobs Listeners


}
