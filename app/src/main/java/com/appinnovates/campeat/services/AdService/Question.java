package com.appinnovates.campeat.services.AdService;


import com.parse.ParseObject;

import java.util.ArrayList;

public class Question {
    public String id,question;
    public ParseObject object;
    public ArrayList<Option> optionList = new ArrayList<>();
    public ParseObject adQuestion;
    public boolean isCorrect = false,isShortAnswer = false;
    public String answer;
    public Question(String id, String question,ParseObject object,ParseObject adQuestion,boolean isShortAnswer) {
        this.id = id;
        this.question = question;
        this.object = object;
        this.adQuestion = adQuestion;
        this.isShortAnswer = isShortAnswer;
    }
}
