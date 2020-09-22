package com.appinnovates.campeat.services.ReviewService;


import android.content.Context;
import android.util.Log;

import com.appinnovates.campeat.utils.Constant;
import com.parse.FindCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by reetu on 26/6/18.
 */

public class ReviewService {

    private static ReviewService reviewService = null;
    private ReviewServiceInterface delegate = null;
    private Context context;

    public static ReviewService getInstance() {
        if (reviewService == null) {
            reviewService = new ReviewService();
        }
        return reviewService;
    }

    public void setDelegate(ReviewServiceInterface delegate, Context context) {
        this.delegate = delegate;
        this.context = context;
    }

    public void getReviews(final Context context, List<ParseObject> branchPointers) {
        final ReviewStore reviewStore = new ReviewStore();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("RestaurantReview");
        query.include("customer_id");
        query.include("deal_id");
        query.include("branch_id");
        query.whereContainedIn("branch_id", branchPointers);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() == 0) {
                        delegate.onNoReviews();
                        return;
                    }
                    delegate.onReviewsSuccess(reviewStore.saveData(objects, context,""));
                } else {
                    delegate.onReviewsFailure(e.getMessage());
                }
            }
        });
    }

    public void getDealMyReviews(final Context context, ParseObject dealObject, ParseObject branchObject) {
        final ReviewStore reviewStore = new ReviewStore();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("RestaurantReview");
        query.include("customer_id");
        query.include("deal_id");
        query.include("branch_id");
        query.whereEqualTo("customer_id", ParseUser.getCurrentUser());
        query.whereEqualTo("deal_id", dealObject);
        query.whereEqualTo("branch_id", branchObject);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() == 0) {
                        delegate.onNoReviews();
                        return;
                    }
                    delegate.onReviewsSuccess(reviewStore.saveData(objects, context,""));
                } else {
                    delegate.onReviewsFailure(e.getMessage());
                }
            }
        });
    }

    public void getDealReviews(final Context context, ParseObject dealObject, ParseObject branchObject) {
        final ReviewStore reviewStore = new ReviewStore();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("RestaurantReview");
//        query.include("customer_id");
        query.include("deal_id");
        query.include("branch_id");
        query.whereEqualTo("deal_id", dealObject);
        query.whereEqualTo("branch_id", branchObject);
        query.findInBackground((objects, e) -> {
            if (e == null) {
                if (objects.size() == 0) {
                    delegate.onNoReviews();
                    return;
                }
                Log.i("reviews", objects.size() + "aa");

                delegate.onReviewsSuccess(reviewStore.saveData(objects, context,""));
            } else {
                delegate.onReviewsFailure(e.getMessage());
            }
        });
    }

    public void getReviewsByRestaurant(final Context context, String restaurantId) {
        final ReviewStore reviewStore = new ReviewStore();
        Map<String, String> map = new HashMap<>();
        map.put("restaurant", restaurantId);
        ParseCloud.callFunctionInBackground("AllReviews", map, (Map<String, Object> objects, ParseException e) -> {
            if (e == null) {
                if (objects.isEmpty()) {
                    delegate.onNoReviews();
                    return;
                }
                delegate.onReviewsSuccess(reviewStore.saveData((List<ParseObject>) objects.get("reviews"), context, Constant.Restaurant));
            } else {
                delegate.onReviewsFailure(e.getMessage());
            }
        });
    }

    public void getReviewsByBranch(final Context context, ParseObject branchObject) {
        final ReviewStore reviewStore = new ReviewStore();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("RestaurantReview");
        query.include("deal_id");
        query.include("branch_id");
        query.include("customer_id");
        query.whereEqualTo("branch_id", branchObject);
        query.findInBackground((objects, e) -> {
            if (e == null) {
                if (objects.size() == 0) {
                    delegate.onNoReviews();
                    return;
                }
                Log.i("reviews", objects.size() + "aa");

                delegate.onReviewsSuccess(reviewStore.saveData(objects, context,""));
            } else {
                delegate.onReviewsFailure(e.getMessage());
            }
        });

    }
}
