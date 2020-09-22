package com.appinnovates.campeat.services.ReviewService;

import com.appinnovates.campeat.model.ReviewModel;

import java.util.List;

/**
 * Created by reetu on 26/6/18.
 */

public interface ReviewServiceInterface {
    void onReviewsSuccess(List<ReviewModel> reviewList);
    void onNoReviews();
    void onReviewsFailure(String message);
}
