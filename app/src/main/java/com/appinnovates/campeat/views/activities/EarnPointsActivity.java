package com.appinnovates.campeat.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.adapter.PointsAdapter;
import com.appinnovates.campeat.adapter.PuzzleAdapter;
import com.appinnovates.campeat.adapter.SurveyAdapter;
import com.appinnovates.campeat.listeners.AdListener;
import com.appinnovates.campeat.bottomSheets.BottomSheetEarnPoints;
import com.appinnovates.campeat.services.AdService.Ad;
import com.appinnovates.campeat.services.AdService.AdGlobal;
import com.appinnovates.campeat.services.AdService.AdService;
import com.appinnovates.campeat.services.AdService.AddPointsDelegate;
import com.appinnovates.campeat.services.AdService.AdsDelegate;
import com.appinnovates.campeat.services.AdService.PointService;
import com.appinnovates.campeat.services.AdService.PointsDelegate;
import com.appinnovates.campeat.services.AdService.TADPEntry;
import com.appinnovates.campeat.utils.CommonUtils;
import com.appinnovates.campeat.utils.Constant;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.ArrayList;

public class EarnPointsActivity extends AppCompatActivity implements AdListener, SurveyAdapter.SurveyListener, PointsAdapter.GoogleRewardClickInterface, BottomSheetEarnPoints.OnEarnPointInterface,BottomSheetEarnPoints.BottomSheetListener {

    ArrayList<Ad> puzzleList = new ArrayList<>();
    ArrayList<Ad> surveyList = new ArrayList<>();
    ArrayList<Ad> quizList = new ArrayList<>();
    TextView txtPoints;
    private ArrayList<Ad> quizs = new ArrayList<>();
    private PointsAdapter points_adapter;
    private ImageView quiz, survey, puzzle;
    private ConstraintLayout constraintLayout;
    ContentLoadingProgressBar progressBar;
    private RewardedAd rewardedAd;

    public void setQuizs(ArrayList<Ad> quizs) {
        try {
            this.quizs.clear();
//            this.quizs.add(0,new Ad());
            this.quizs.addAll(quizs);
            points_adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private ArrayList<Ad> surveys = new ArrayList<>();
    private SurveyAdapter survey_adapter;

    public void setSurveys(ArrayList<Ad> surveys) {
        try {
            this.surveys.clear();
            this.surveys.addAll(surveys);
            survey_adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earn_points);

        loadRewardedAd();

        Toolbar toolbar = findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.arrow_back_black);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        constraintLayout = findViewById(R.id.constraintLayout);
        txtPoints = findViewById(R.id.txt_earn_points);
        quiz = findViewById(R.id.imageView_quiz);
        puzzle = findViewById(R.id.imageView_puzzle);
        survey = findViewById(R.id.imageView_survey);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.show();
        getAds();
        setListeners();

/*
        List<String> testDeviceIds = Arrays.asList(getString(R.string.device_id));
        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);
*/


        RecyclerView recyclerView_puzzle = findViewById(R.id.recycler_view);
        recyclerView_puzzle.setHasFixedSize(true);
        puzzle_adapter = new PuzzleAdapter(this, puzzles, R.layout.item_puzzle);
        recyclerView_puzzle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView_puzzle.setAdapter(puzzle_adapter);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_quiz);
        points_adapter = new PointsAdapter(this, false, quizs, this, R.layout.earn_points_item_layout,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(points_adapter);

        RecyclerView recyclerView_survey = findViewById(R.id.recycler_view_survey);
        survey_adapter = new SurveyAdapter(this, true, surveys, this, R.layout.earn_points_survey_layout);
        recyclerView_survey.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView_survey.setAdapter(survey_adapter);


    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTotalPoints();
    }

    private void getAds() {

        AdService.instance.getAds(new AdsDelegate() {
            @Override
            public void puzzles(ArrayList<Ad> puzzles) {
                constraintLayout.setVisibility(View.VISIBLE);
                progressBar.hide();
                puzzleList.clear();
                puzzleList.addAll(puzzles);
                setPuzzles(puzzleList);
/*                if (isFirst){
                    loadFragment(puzzleFragment);
                    isFirst = false;
                }*/
            }

            @Override
            public void quizs(ArrayList<Ad> quizs) {
                constraintLayout.setVisibility(View.VISIBLE);
                progressBar.hide();
                quizList.clear();
                quizList.addAll(quizs);
                setQuizs(quizList);
            }

            @Override
            public void surveys(ArrayList<Ad> surveys) {
                constraintLayout.setVisibility(View.VISIBLE);
                progressBar.hide();
                surveyList.clear();
                surveyList.addAll(surveys);
                setSurveys(surveyList);
            }

            @Override
            public void failure(String message) {

            }
        });
    }


    private void getTotalPoints() {
        new PointService().points(new PointsDelegate() {
            @Override
            public void onSuccess(ArrayList<TADPEntry> tadpEntries, ArrayList<TADPEntry> tadEntries, int totalTADPPoints, int totalTADPoints) {
                txtPoints.setText(totalTADPPoints + " "+getString(R.string.tadp));
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    private ArrayList<Ad> puzzles = new ArrayList<>();
    private PuzzleAdapter puzzle_adapter;

    public void setPuzzles(ArrayList<Ad> puzzles) {
        try {
            this.puzzles.clear();
            this.puzzles.addAll(puzzles);
            puzzle_adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(Ad ad) {
        AdGlobal.instance().setAd(ad);
        startActivity(new Intent(this, QuizActivity.class));


    }

    @Override
    public void onSurveyClick(Ad ad) {
        AdGlobal.instance().setAd(ad);
        startActivity(new Intent(this, SurveyActivity.class));
    }

    void setListeners() {
        quiz.setOnClickListener(v -> goToListPage(Constant.QUIZ));
        puzzle.setOnClickListener(v -> goToListPage(Constant.PUZZLE));
        survey.setOnClickListener(v -> goToListPage(Constant.SURVEY));
    }

    void goToListPage(String type) {
        Intent intent = new Intent(this, AdListActivity.class);
        intent.putExtra(Constant.TYPE, type);
        startActivity(intent);
    }


    private void showRewardedVideo() {
        if (rewardedAd.isLoaded()) {
            RewardedAdCallback adCallback =
                    new RewardedAdCallback() {
                        @Override
                        public void onRewardedAdOpened() {
                        }

                        @Override
                        public void onRewardedAdClosed() {
                            EarnPointsActivity.this.loadRewardedAd();
                        }

                        @Override
                        public void onUserEarnedReward(@NonNull com.google.android.gms.ads.rewarded.RewardItem rewardItem) {
                            addPoints(rewardItem.getAmount());
                        }

                        @Override
                        public void onRewardedAdFailedToShow(int errorCode) {

                        }
                    };
            rewardedAd.show(this, adCallback);
        } else {
            Toast.makeText(this, "Unable to open video,please try after sometime", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadRewardedAd() {
        if (rewardedAd == null || !rewardedAd.isLoaded()) {
            rewardedAd = new RewardedAd(this, getString(R.string.ads_id));
            rewardedAd.loadAd(
                    new AdRequest.Builder()/*.addTestDevice(getString(R.string.device_id))*/.build(),
                    new RewardedAdLoadCallback() {
                        @Override
                        public void onRewardedAdLoaded() {
                            Log.i("Loaded","AdLoaded");
                        }

                        @Override
                        public void onRewardedAdFailedToLoad(int errorCode) {
                            Log.i("Loaded","AdLoadedFailed");
                        }
                    });
        }
    }

    private void addPoints(int points) {
        new PointService().addPoints(points, null, null, null, false, new AddPointsDelegate() {
            @Override
            public void success() {
                successSheet();
            }
            @Override
            public void failure(String message) {
                CommonUtils.showToast(EarnPointsActivity.this, message);
            }
        });
    }

    void successSheet(){
        BottomSheetEarnPoints bottomSheetEarnPoints=new BottomSheetEarnPoints(EarnPointsActivity.this,this,"congratulations",1000,null);
        bottomSheetEarnPoints.show(getSupportFragmentManager(),"this");
    }

    @Override
    public void googleRewardClick() {
        showRewardedVideo();
    }

    @Override
    public void onPointsClicked() {

    }

    @Override
    public void onButtonCancelDeal(String text) {

    }
}
