package com.appinnovates.campeat.services.AdService;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;

public class QuestionService {

    public void questions(ParseObject ad, final QuestionDelegate delegate){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("AdQuestion");
        query.include("question_id");
        query.whereEqualTo("ad_id",ad);
        query.findInBackground((objects, e) -> {
            if (objects != null){
                if (objects.size() == 0) {
                    delegate.failure("No Ads available");
                    return;
                }
                ArrayList<Question> questions = new ArrayList<>();
                for (ParseObject obj:objects){
                    questions.add(fetchQuestion(obj.getParseObject("question_id"),obj));
                }
                delegate.questions(questions);

            }else{
                delegate.failure(e.getMessage());
            }
        });
    }

    private Question fetchQuestion(ParseObject object,ParseObject adQuestion){
        return new Question(object.getObjectId(),object.getString("question"),object,adQuestion,object.getBoolean("is_short_answer"));
    }

}
