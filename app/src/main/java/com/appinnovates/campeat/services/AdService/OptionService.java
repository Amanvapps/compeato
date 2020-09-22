package com.appinnovates.campeat.services.AdService;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class OptionService {

    public void options(ParseObject question, final OptionsDelegate delegate){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Options");
        query.whereEqualTo("question_id",question);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects != null){
                    if (objects.size() == 0){
                        delegate.failure("No options available");
                        return;
                    }
                    ArrayList<Option> options = new ArrayList<>();
                    for (ParseObject object:objects) {
                        options.add(fetchOption(object));
                    }
                    delegate.options(options);

                }else{
                    delegate.failure(e.getMessage());
                }
            }
        });
    }
    private Option fetchOption(ParseObject object){
        return new Option(object.getObjectId(),object.getString("name"),object,object.getBoolean("is_correct"));
    }

}
