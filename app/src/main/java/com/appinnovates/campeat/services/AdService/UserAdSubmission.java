package com.appinnovates.campeat.services.AdService;

import com.parse.ParseObject;

public class UserAdSubmission {
    public ParseObject userId,adId;
    public int points;

    public UserAdSubmission(ParseObject userId, ParseObject adId, double points) {
        this.userId = userId;
        this.adId = adId;
        this.points = (int) points;
    }
}
