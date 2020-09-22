package com.appinnovates.campeat.services.AdService;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Date;

public class Ad implements Parcelable {
    public String id,success_message,failure_message,name,attachment_link;
    public AdType type;
    public ParseObject client;
    public int dailyBudget,user_impressions,user_responses,points;
    public Date startDay,endDay;
    public ParseObject object;
    public Bitmap bitmap;
    public int seconds = 0;
    public ParseFile attachment;
    public ArrayList<Question> questionList;

    public Ad(String id,ParseObject client, String success_message, String failure_message
            , String name, String attachment_link, AdType type
            , Date startDay, Date endDay,ParseObject object,ParseFile attachment,double dailyBudget,double user_impressions,double user_responses,
              double points) {
        this.id = id;
        this.success_message = success_message;
        this.failure_message = failure_message;
        this.name = name;
        this.client = client;
        this.attachment_link = attachment_link;
        this.type = type;
        this.dailyBudget = (int) dailyBudget;
        this.user_impressions = (int) user_impressions;
        this.user_responses = (int) user_responses;
        this.points = (int) points;
        this.startDay = startDay;
        this.endDay = endDay;
        this.object = object;
        this.attachment = attachment;
        boolean load = false;
        switch (type.name){
            case "Survey":
                load = true;
                break;
            case "Quiz":
                load = true;
                break;
            case "Puzzle":
                load = false;
                break;
        }

        if (load){
            new QuestionService().questions(object, new QuestionDelegate() {
                @Override
                public void questions(ArrayList<Question> questions) {
                    questionList = questions;
                }

                @Override
                public void failure(String message) {

                }
            });
        }

    }

    public Ad(){}

    protected Ad(Parcel in) {
        id = in.readString();
        success_message = in.readString();
        failure_message = in.readString();
        name = in.readString();
        attachment_link = in.readString();
        client = in.readParcelable(ParseObject.class.getClassLoader());
        dailyBudget = in.readInt();
        user_impressions = in.readInt();
        user_responses = in.readInt();
        points = in.readInt();
        object = in.readParcelable(ParseObject.class.getClassLoader());
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
        seconds = in.readInt();
        attachment = in.readParcelable(ParseFile.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(success_message);
        dest.writeString(failure_message);
        dest.writeString(name);
        dest.writeString(attachment_link);
        dest.writeParcelable(client, flags);
        dest.writeInt(dailyBudget);
        dest.writeInt(user_impressions);
        dest.writeInt(user_responses);
        dest.writeInt(points);
        dest.writeParcelable(object, flags);
        dest.writeParcelable(bitmap, flags);
        dest.writeInt(seconds);
        dest.writeParcelable(attachment, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Ad> CREATOR = new Creator<Ad>() {
        @Override
        public Ad createFromParcel(Parcel in) {
            return new Ad(in);
        }

        @Override
        public Ad[] newArray(int size) {
            return new Ad[size];
        }
    };
}
