package com.appinnovates.campeat.services.AdService;

import java.util.ArrayList;

public interface QuestionDelegate {
    void questions(ArrayList<Question> questions);
    void failure(String message);
}
