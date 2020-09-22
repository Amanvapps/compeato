package com.appinnovates.campeat.services.AdService;

import com.appinnovates.campeat.utils.CommonUtils;
import com.appinnovates.campeat.utils.DateFormatUtil;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdService {
    public static AdService instance = new AdService();
    public Map<String,String> settings = new HashMap<>();
    public void getSettings(final SettingsDelegate delegate){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Settings");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects != null && objects.size() > 0){
                    settings.clear();
                    for (ParseObject object:objects) {
                        Settings setting = fetchSetting(object);
                        settings.put(setting.name,setting.value);
                    }
                    delegate.success(settings);
                }
            }
        });
    }




    public void getAds(final AdsDelegate delegate){
        getSettings(new SettingsDelegate() {
            @Override
            public void success(final Map<String,String> settings) {
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Status");
                query.whereEqualTo("objectId", "6mPSTzoTPL");
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (objects != null && objects.size() > 0){
                            ads(objects.get(0),delegate,settings);
                        }else {
                            delegate.failure("");
                        }
                    }
                });
            }
        });
    }



    private void ads(ParseObject status, final AdsDelegate delegate, final Map<String,String> settings) {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Ads");
        query.include("type_id");
        query.include("status");
        query.include("ad_question_relation");
        query.whereEqualTo("status",  status);
        query.whereEqualTo("is_active",  true);
        query.whereGreaterThan("end_day", DateFormatUtil.getCurrentDate());
        query.whereLessThan("start_day", DateFormatUtil.getCurrentDate());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects != null && objects.size() > 0) {
                    ArrayList<Ad> ads = new ArrayList<>();
                    for (ParseObject object:objects) {
                        Ad ad = fetchAd(object);
                        ads.add(ad);
                    }
                    String setting = settings.get(SettingType.SHOW_ADS_AGAIN);
                    if (setting.equalsIgnoreCase("TRUE")){
                        sortAds(ads,delegate);
                    }else{
                        compareAds(ads,delegate,settings);
                    }
                }else {
                    CommonUtils.hideProgress();
                    delegate.failure("");
                }
            }
        });
    }


    private void compareAds(final ArrayList<Ad> ads, final AdsDelegate delegate, final Map<String,String> settings){
        Date currentDate = DateFormatUtil.getCurrentDate();
        currentDate.setTime(1000);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>( "UserAdSubmissions");
        query.whereEqualTo("user_id",  ParseUser.getCurrentUser());
        query.whereGreaterThan("createdAt", currentDate);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects != null && objects.size() > 0){
                    ArrayList<UserAdSubmission> submissions = new ArrayList<>();
                    for (ParseObject object :objects) {
                        submissions.add(fetchSubmission(object));
                    }
                    filterAds(ads,submissions,delegate,settings);
                }else{
                    sortAds(ads,delegate);
                }
            }
        });
    }

    private void filterAds(ArrayList<Ad> ads,ArrayList<UserAdSubmission> submissions,AdsDelegate delegate,Map<String,String> settings){
        ArrayList<Ad> filtered = new ArrayList<>();

        for (Ad ad:ads) {
            if (ad.type.name != null){
                int adPoints = 0;
                if (ad.points == 0){
                    switch (ad.type.name){
                        case "Survey":
                            adPoints = Integer.parseInt(settings.get(SettingType.PER_SURVEY));
                            break;
                        case "Quiz":
                            adPoints = Integer.parseInt(settings.get(SettingType.PER_QUIZ));
                            break;
                        case "Puzzle":
                            adPoints = Integer.parseInt(settings.get(SettingType.PER_PUZZLE));
                            break;
                    }
                }else{
                    adPoints = ad.points;
                }

                boolean isSubmitted = false;
                for (UserAdSubmission submission:submissions) {
                    if (submission.adId.getObjectId().equalsIgnoreCase(ad.id)){
                        isSubmitted = true;
                        break;
                    }else{
                        adPoints += submission.points;
                    }
                }

                if (!isSubmitted && adPoints < ad.dailyBudget){
                    filtered.add(ad);
                }

            }else {
                delegate.failure("");
            }
        }
        sortAds(filtered,delegate);
    }

    private void sortAds(ArrayList<Ad> ads, final AdsDelegate delegate){
        final ArrayList<Ad> puzzles = new ArrayList<>();
        final ArrayList<Ad> quizs = new ArrayList<>();
        final ArrayList<Ad> surveys = new ArrayList<>();
        for(Ad ad:ads){

            switch (ad.type.name){
                case "Survey":
                    surveys.add(ad);
                    break;
                case "Quiz":
                    quizs.add(ad);
                    break;
                case "Puzzle":
                    puzzles.add(ad);
                    break;
            }
        }


        delegate.puzzles(puzzles);
        delegate.quizs(quizs);
        delegate.surveys(surveys);


    }


    public void addSurveys(ArrayList<Question> questions){
        outer : for (Question question:questions) {
            ParseObject object = new ParseObject("UserSubmissions");
            object.put("ad_question_id",question.adQuestion);
            object.put("user_id",ParseUser.getCurrentUser());
            for (Option option:question.optionList) {
                if (option.isSelected){
                    object.put("selected_option_id",option.object);
                }else if (question.answer != null){
                    object.put("answer",question.answer);
                }
            }
            object.saveInBackground();
        }
    }


    Ad fetchAd(ParseObject object){
        AdType adType = fetchAdType(object.getParseObject("type_id"));
        ParseObject client = object.getParseObject("client_id");

        return new Ad(object.getObjectId()
                ,client
                ,object.getString("success_message")
                ,object.getString("failure_message")
                ,object.getString("name")
                ,object.getString("attachment_link")
                ,adType
                ,object.getDate("start_day")
                ,object.getDate("end_day")
                ,object,object.getParseFile("attachment")
                ,object.getDouble("daily_budget")
                ,object.getDouble("user_impressions")
                ,object.getDouble("user_responses")
                ,object.getDouble("points"));
    }

    private AdType fetchAdType(ParseObject object){
        return new AdType(object.getObjectId(),object.getString("name"));
    }

    private Settings fetchSetting(ParseObject object){
        return new Settings(object.getString("key"),object.getString("value"));
    }

    private UserAdSubmission fetchSubmission(ParseObject object){
        return new UserAdSubmission(object.getParseObject("user_id"),object.getParseObject("ad_id"),object.getDouble("points"));
    }



}


