package com.appinnovates.campeat.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.adapter.PointsAdapter;
import com.appinnovates.campeat.adapter.PuzzleAdapter;
import com.appinnovates.campeat.adapter.SurveyAdapter;
import com.appinnovates.campeat.listeners.AdListener;
import com.appinnovates.campeat.services.AdService.Ad;
import com.appinnovates.campeat.services.AdService.AdGlobal;
import com.appinnovates.campeat.services.AdService.AdService;
import com.appinnovates.campeat.services.AdService.AdsDelegate;
import com.appinnovates.campeat.services.AdService.PointService;
import com.appinnovates.campeat.services.AdService.PointsDelegate;
import com.appinnovates.campeat.services.AdService.TADPEntry;
import com.appinnovates.campeat.utils.Constant;

import java.util.ArrayList;

public class AdListActivity extends AppCompatActivity implements SurveyAdapter.SurveyListener, AdListener, PointsAdapter.GoogleRewardClickInterface {

    RecyclerView recyclerView;
    private String type;
    private TextView txtPoints;
    ArrayList<Ad> puzzleList = new ArrayList<>();
    ArrayList<Ad> surveyList = new ArrayList<>();
    ArrayList<Ad> quizList = new ArrayList<>();
    private SurveyAdapter survey_adapter;
    private PuzzleAdapter puzzle_adapter;
    private PointsAdapter points_adapter;
    private ArrayList<Ad> puzzles = new ArrayList<>();
    private ArrayList<Ad> surveys = new ArrayList<>();
    private ArrayList<Ad> quizs = new ArrayList<>();
    private ContentLoadingProgressBar progressBar;
    private TextView name,title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_list);
        getAds();
        type=getIntent().getStringExtra(Constant.TYPE);

        progressBar=findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.show();
        txtPoints=findViewById(R.id.txt_total_points);
        name=findViewById(R.id.name);
        title=findViewById(R.id.textView5);
        name.setText(type);
        title.setText(type);

        recyclerView=findViewById(R.id.list_item_view);
        Toolbar toolbar = findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.arrow_back_black);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        setRecyclerView();

    }

    void setRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        if (type.equalsIgnoreCase(Constant.PUZZLE)){
            puzzle_adapter = new PuzzleAdapter(this, puzzles,R.layout.ad_list_item_layout);
            recyclerView.setAdapter(puzzle_adapter);
        }
        if (type.equalsIgnoreCase(Constant.SURVEY)){
            survey_adapter = new SurveyAdapter(this,true,surveys,this,R.layout.ad_list_item_layout);
            recyclerView.setAdapter(survey_adapter);
        }
        if (type.equalsIgnoreCase(Constant.QUIZ)){
            points_adapter = new PointsAdapter(  this, false, quizs, this,R.layout.ad_list_item_layout,this);
            recyclerView.setAdapter(points_adapter);
        }
    }

    public void setPuzzles(ArrayList<Ad> puzzles) {
        try {
            this.puzzles.clear();
            this.puzzles.addAll(puzzles);
            puzzle_adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setQuizs(ArrayList<Ad> quizs) {
        try {
            this.quizs.clear();
            this.quizs.addAll(quizs);
            points_adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setSurveys(ArrayList<Ad> surveys) {
        try {
            this.surveys.clear();
            this.surveys.addAll(surveys);
            survey_adapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onSurveyClick(Ad ad) {
        AdGlobal.instance().setAd(ad);
        startActivity(new Intent(this, SurveyActivity.class));
    }

    @Override
    public void onClick(Ad ad) {
        AdGlobal.instance().setAd(ad);
        startActivity(new Intent(this, QuizActivity.class));
    }
    private void getAds() {
        AdService.instance.getAds(new AdsDelegate() {
            @Override
            public void puzzles(ArrayList<Ad> puzzles) {
                progressBar.hide();
                puzzleList.clear();
                puzzleList.addAll(puzzles);
                setPuzzles(puzzleList);
            }

            @Override
            public void quizs(ArrayList<Ad> quizs) {
                progressBar.hide();
                quizList.clear();
                quizList.addAll(quizs);
                setQuizs(quizList);
            }

            @Override
            public void surveys(ArrayList<Ad> surveys) {
                progressBar.hide();
                surveyList.clear();
                surveyList.addAll(surveys);
                setSurveys(surveyList);
            }

            @Override
            public void failure(String message) {
                Toast.makeText(AdListActivity.this, ""+message, Toast.LENGTH_SHORT).show();
            }
        });
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
                txtPoints.setText(totalTADPPoints + " " + getString(R.string.tadp));
            }
            @Override
            public void onFailure(String message) {

            }
        });
    }

    @Override
    public void googleRewardClick() {

    }
}
