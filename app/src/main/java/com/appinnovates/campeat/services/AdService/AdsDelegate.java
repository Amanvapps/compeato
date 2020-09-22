package com.appinnovates.campeat.services.AdService;

import java.util.ArrayList;

public interface AdsDelegate {
    void puzzles(ArrayList<Ad> puzzles);
    void quizs(ArrayList<Ad> quizs);
    void surveys(ArrayList<Ad> surveys);
    void failure(String message);
}
