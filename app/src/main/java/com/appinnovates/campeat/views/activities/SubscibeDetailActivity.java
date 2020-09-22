package com.appinnovates.campeat.views.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.adapter.BranchInfoAdapter;
import com.appinnovates.campeat.adapter.HomeAdapter;
import com.appinnovates.campeat.adapter.ReviewAdapter;
import com.appinnovates.campeat.listeners.ItemListener;
import com.appinnovates.campeat.model.HomeBranchDeals.HomeBranchDealsModel;
import com.appinnovates.campeat.model.MenuSection;
import com.appinnovates.campeat.model.ReviewModel;
import com.appinnovates.campeat.model.getAllDeal.Deal;
import com.appinnovates.campeat.model.getAllDealsModel.RestaurantIdModel;
import com.appinnovates.campeat.services.DealService.AllDealsService;
import com.appinnovates.campeat.services.DealService.DealStore;
import com.appinnovates.campeat.services.DealService.RestaurantDealsInterface;
import com.appinnovates.campeat.services.FavoriteRestaurantService.FavouriteRestaurantService;
import com.appinnovates.campeat.services.FavoriteRestaurantService.FavouriteRestaurantServiceDelegate;
import com.appinnovates.campeat.services.ImageService.ImageServiceViewInterface;
import com.appinnovates.campeat.services.ReviewService.ReviewService;
import com.appinnovates.campeat.services.ReviewService.ReviewServiceInterface;
import com.appinnovates.campeat.services.SubscriptionDetailService.SubscriptionDetailService;
import com.appinnovates.campeat.services.SubscriptionDetailService.SubscriptionDetailServiceInterface;
import com.appinnovates.campeat.utils.CommonUtils;
import com.appinnovates.campeat.utils.Constant;
import com.appinnovates.campeat.utils.LocaleManager;
import com.appinnovates.campeat.utils.PermissionsUtil;
import com.appinnovates.campeat.utils.ShareUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SubscibeDetailActivity extends AppCompatActivity implements View.OnClickListener, ImageServiceViewInterface
        , FavouriteRestaurantServiceDelegate, SubscriptionDetailServiceInterface, RestaurantDealsInterface, ItemListener, ReviewServiceInterface {

    private TextView txtReview;
    private ImageView restaurantImageView;
    private RecyclerView recyclerViewReview;
    private RecyclerView recyclerViewDeal;
    private RecyclerView recyclerViewBranchDetails;
    private ImageView imageViewItemMap;
    private List<ReviewModel> reviewModelList;
    private RestaurantIdModel restaurantModel;
    private TextView textViewTitle;
    private ImageView imageViewHeart;
    private FavouriteRestaurantService favouriteRestaurantService;
    private List<HomeBranchDealsModel> branchDealModelList;
    private List<HomeBranchDealsModel> branchDealsFilterList;
    private HomeAdapter activeDealsAdapter;
    private ReviewAdapter reviewAdapter;
    private ImageView shareLayout;
    private TextView ratingBar;
    private List<MenuSection> menuSections;
    private String restaurantId = null;
    private AllDealsService allDealsService;
    private ArrayList<String> restaurantIdList;
    private SubscriptionDetailService subscriptionDetailService;
    private String isActive = "active";
    private ReviewService reviewService;
    private Boolean isSubscribed = false;
    private ConstraintLayout locationLayout;
    private TextView rating;
    private TextView address;
    private TextView description;
    private TextView subscribers;
    private BranchInfoAdapter infoAdapter;
    private boolean isBoolean = true;
    private ContentLoadingProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscibe_detail);
        locationLayout = findViewById(R.id.constraintLayout4);
        recyclerViewBranchDetails = findViewById(R.id.recyclerView_branch_details);
        locationLayout.setOnClickListener(this);
        restaurantId = getIntent().getStringExtra(Constant.RestaurantID);
        initView();
        initField();
        setBranchRecyclerview();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        address = findViewById(R.id.text_address);
        rating = findViewById(R.id.rating);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow_back_black));
        if (restaurantModel != null) {
            setRestaurantData();
        }

        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

        AppBarLayout appbar = findViewById(R.id.appbar);

        appbar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if ((collapsingToolbar.getHeight() + verticalOffset) < (2 * ViewCompat.getMinimumHeight(collapsingToolbar))) {
                imageViewHeart.setColorFilter(ContextCompat.getColor(SubscibeDetailActivity.this, R.color.colorAccent), PorterDuff.Mode.SRC_IN);
                Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                shareLayout.setColorFilter(ContextCompat.getColor(SubscibeDetailActivity.this, R.color.colorAccent), PorterDuff.Mode.SRC_IN);

            } else {
                imageViewHeart.setColorFilter(ContextCompat.getColor(SubscibeDetailActivity.this, R.color.white_color), PorterDuff.Mode.SRC_IN);
                Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.white_color), PorterDuff.Mode.SRC_ATOP);
                shareLayout.setColorFilter(ContextCompat.getColor(SubscibeDetailActivity.this, R.color.white_color), PorterDuff.Mode.SRC_IN);

            }
        });

        if (restaurantId == null) {
            setData();
        } else {
            getSingleRestaurant();
        }
        setAdapter();
        /*etMenuAdapter();*/
        setListener();
        setUpServices();
        if (restaurantId == null) {
            getData();
        }

        /*loadBackdrop();
         */
    }

    void setBranchRecyclerview() {
        infoAdapter = new BranchInfoAdapter(this, branchDealsFilterList);
        recyclerViewBranchDetails.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBranchDetails.setHasFixedSize(true);
        recyclerViewBranchDetails.setNestedScrollingEnabled(false);
        recyclerViewBranchDetails.setAdapter(infoAdapter);
        ((SimpleItemAnimator) Objects.requireNonNull(recyclerViewBranchDetails.getItemAnimator())).setSupportsChangeAnimations(false);
    }

    void setRestaurantData() {
        restaurantId = restaurantModel.getObjectId();
        getSubscribers();
        rating.setText(String.format("%d", restaurantModel.getAverageRating()));
        address.setText(restaurantModel.getStreetAddress());
        description.setText(restaurantModel.getDescription() != null ? restaurantModel.getDescription().trim() : "Not Specified");
        textViewTitle.setText(restaurantModel.getRestaurantName());
        if (restaurantModel.getLogo() != null) {
            Glide.with(this)
                    .load(restaurantModel.getLogo().getUrl())
                    .override(500, 500)
                    .error(R.drawable.default_rest)
                    .placeholder(R.drawable.default_rest)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            // log exception
                            Log.e("TAG", "Error loading image", e);
                            return false; // important to return false so the error placeholder can be placed
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(restaurantImageView);
        }
    }

/*
    private void loadBackdrop() {
        final ImageView imageView = findViewById(R.id.backdrop);
        Glide.with(this).load(getResources().getDrawable(R.drawable.a5)).apply(RequestOptions.centerCropTransform()).into(imageView);
    }
*/

    private void getSingleRestaurant() {

        if (!PermissionsUtil.isNetworkAvailable(this)) {
            CommonUtils.showToast(this, getString(R.string.no_internet_available));
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Restaurant");
        query.whereEqualTo("objectId", restaurantId);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    progressBar.setVisibility(View.GONE);
                    if (objects.size() == 1) {
                        DealStore dealStore = new DealStore();
                        restaurantModel = dealStore.createBranchRestaurantModel(objects.get(0));
                        setData();
                        getData();
                        setRestaurantData();
                    }

                } else {
                    progressBar.setVisibility(View.GONE);
                    CommonUtils.showToast(SubscibeDetailActivity.this, e.getMessage() + "");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        restaurantModel = intent.getParcelableExtra(Constant.RestaurantModel);
    }

    private void setData() {
        if (restaurantModel == null)
            return;
        textViewTitle.setText(restaurantModel.getRestaurantName());
        ratingBar.setText("" + restaurantModel.getAverageRating());

    }

    private void initField() {
        menuSections = new ArrayList<>();
        restaurantIdList = new ArrayList<>();
        reviewModelList = new ArrayList<>();
        branchDealModelList = new ArrayList<>();
        branchDealsFilterList = new ArrayList<>();


        restaurantModel = getIntent().getParcelableExtra(Constant.RestaurantModel);

    }


    private void setListener() {
        imageViewHeart.setOnClickListener(this);
        imageViewItemMap.setOnClickListener(this);
        shareLayout.setOnClickListener(this);
    }

    private void initView() {
        subscribers = findViewById(R.id.txtsubscribers);
        ratingBar = findViewById(R.id.rating);
        shareLayout = findViewById(R.id.share_layout);
        restaurantImageView = findViewById(R.id.img);
        textViewTitle = findViewById(R.id.text_view_title);
        description = findViewById(R.id.description);
        imageViewHeart = findViewById(R.id.image_view_heart);
        txtReview = findViewById(R.id.txtReview_count);
        recyclerViewReview = findViewById(R.id.recycler_view_reviews);
        recyclerViewDeal = findViewById(R.id.recycler_view_deals);
        recyclerViewDeal.setNestedScrollingEnabled(false);
        imageViewItemMap = findViewById(R.id.item_map);
        progressBar=findViewById(R.id.progressBar2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.constraintLayout4:
                startActivity(new Intent(getApplicationContext(), MapsActivity.class)
                        .putExtra(Constant.FROM_SCREEN, Constant.SUBSCRIPTION_DETAIL)
                        .putParcelableArrayListExtra(Constant.SUBSCRIPTION, (ArrayList<? extends Parcelable>) branchDealsFilterList));
                break;

            case R.id.image_view_heart:
                if (ParseUser.getCurrentUser() == null) {
                    CommonUtils.showToast(this, getString(R.string.login_first));
                    return;
                }
                if (!PermissionsUtil.isNetworkAvailable(this)) {
                    CommonUtils.showToast(this, getString(R.string.no_internet_available));
                    return;
                }
                if (isSubscribed) {
                    favouriteRestaurantService.requestUnSubscribe(restaurantModel.getPointer());
                    Toast.makeText(this, "Restaurant Unsubscribed", Toast.LENGTH_SHORT).show();
                    isSubscribed = false;
                } else {
                    favouriteRestaurantService.requestSubscribe(restaurantModel.getPointer());
                    Toast.makeText(this, "Restaurant Subscribed", Toast.LENGTH_SHORT).show();
                    isSubscribed = true;
                }
                break;
            case R.id.image_view_location:
                Intent intent = new Intent(SubscibeDetailActivity.this, HomePage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.share_layout:
                ShareUtil.shareRestaurantDetail(this, restaurantModel.getRestaurantName(), restaurantModel.getObjectId());
                break;
        }
    }


    private void setUpServices() {
        favouriteRestaurantService = FavouriteRestaurantService.getInstance();
        favouriteRestaurantService.setDelegate(this);

        subscriptionDetailService = SubscriptionDetailService.getInstance();
        subscriptionDetailService.setDelegateAndContext(this, getApplicationContext());

        allDealsService = AllDealsService.getInstance();
        allDealsService.setDelegateAndContext(this, getApplicationContext());

        reviewService = ReviewService.getInstance();
        reviewService.setDelegate(this, this);

    }

    //Service Requests
    private void getData() {
        if (!PermissionsUtil.isNetworkAvailable(this)) {
            CommonUtils.showToast(this, getString(R.string.no_internet_available));
            return;
        }
//        CommonUtils.showProgress(this);
        //if (restaurantModel.getLogo() != null)
        fetchRestaurantDeal();


        reviewService.getReviewsByRestaurant(this, restaurantModel.getObjectId());
    }

    private void fetchRestaurantDeal() {
        restaurantIdList.clear();
        if (restaurantModel != null && restaurantModel.getObjectId() != null) {
            restaurantIdList.add(restaurantModel.getObjectId());
            favouriteRestaurantService.isRestaurantSubscribed(restaurantModel.getPointer());
            subscriptionDetailService.requestRestaurantData(restaurantModel.getPointer());
        }
        if (isBoolean) {
            if (restaurantIdList.size() > 0) {
                isBoolean = false;
                allDealsService.getAllDeals(false, "", false, "", 5000
                        , false, "", new ArrayList<>(), "map", restaurantIdList, isActive);
            }
        }
    }


    public void setAdapter() {
        reviewAdapter = new ReviewAdapter(R.layout.adapter_review, reviewModelList, this);
        recyclerViewReview.setAdapter(reviewAdapter);
        recyclerViewReview.setHasFixedSize(true);
        recyclerViewReview.setNestedScrollingEnabled(false);
        recyclerViewReview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        activeDealsAdapter = new HomeAdapter(this, branchDealsFilterList, this, this, Constant.SUBSCRIPTION_DETAIL);
        recyclerViewDeal.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDeal.setHasFixedSize(true);
        recyclerViewDeal.setAdapter(activeDealsAdapter);
    }

    private void setSubscribed() {
        imageViewHeart.setImageDrawable(getResources().getDrawable(R.drawable.subscribe_icon));
    }

    private void setUnSubscribed() {

        imageViewHeart.setImageDrawable(getResources().getDrawable(R.drawable.heart_line));
    }

    //FavoriteRestaurantService Response
    @Override
    public void restaurantIsSubscribed(ParseObject object) {
        setSubscribed();
        isSubscribed = true;
    }

    @Override
    public void restaurantIsNotSubscribed() {
        setUnSubscribed();
        isSubscribed = false;
    }

    @Override
    public void onIsSubscribedFailure(String message) {
        CommonUtils.showToast(this, message);
    }

    @Override
    public void onSubscribeSuccess() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constant.SUBSCRIBE));
        setSubscribed();
    }

    @Override
    public void onSubscribeFailure(String message) {
        CommonUtils.showToast(this, message);
    }

    @Override
    public void onUnSubscribeSuccess() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constant.SUBSCRIBE));
        setUnSubscribed();
    }

    @Override
    public void onUnSubscribeFailure(String message) {
        CommonUtils.showToast(this, message);
    }

    //ImageService Response
    @Override
    public void onImageServiceSuccess(Bitmap bitmap) {
        if (bitmap != null) {
//            restaurantImageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onImageServiceFailure(String message) {

    }

    @Override
    public void onImageServiceSuccessForRow(Bitmap bitmap, int position, ImageView imageView) {

    }

    @Override
    public void onImageServiceFailureForRow(String message, int position) {

    }

    //Menus,Reviews,Deals Response
    @Override
    public void onMenusSuccess(List<MenuSection> menuSections) {
        this.menuSections.clear();
        this.menuSections.addAll(menuSections);
        /*setMenuAdapter();*/
    }

    @Override
    public void onNoMenus() {

    }

    @Override
    public void onMenusFailure(String message) {
    }

    @Override
    public void onReviewsSuccess(List<ReviewModel> reviewModelList) {
        this.reviewModelList.clear();
        this.reviewModelList.addAll(reviewModelList);
        setReviewText(reviewModelList.size());
        reviewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNoReviews() {
        setReviewText(0);
    }

    @Override
    public void onReviewsFailure(String message) {

    }


    @Override
    public void onNoDeals() {

    }

    @Override
    public void onDealsFailure(String message) {

    }

    @Override
    public void onNoBranchesAvailable(String message) {

    }

    @Override
    public void onBranchesFailure(String message) {

    }

    private void setReviewText(int size) {
        txtReview.setText(getApplicationContext().getResources().getString(R.string.review_count, size));
        TextView review = findViewById(R.id.txtReview);
        review.setText("" + size);
    }

    @Override
    public void onNoDealsAvailable(String message) {
        progressBar.setVisibility(View.GONE);
        branchDealsFilterList.clear();
        branchDealModelList.clear();
        activeDealsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNoInternetAvailable() {
        progressBar.setVisibility(View.GONE);
        CommonUtils.showToast(this, getString(R.string.no_internet_available));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this, LocaleManager.getLanguage(this));
    }

    @Override
    public void onDealsSuccess(List<Deal> dealsModelList) {

    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(this, ItemDetailActivity.class);
        HomeBranchDealsModel restaurant = branchDealsFilterList.get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable("restaurants", restaurant);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onRestaurantDealsSuccess(List<HomeBranchDealsModel> restaurantDeals) {
        progressBar.setVisibility(View.GONE);
        branchDealsFilterList.clear();
        branchDealModelList.clear();
        branchDealModelList.addAll(restaurantDeals);
        branchDealsFilterList.addAll(branchDealModelList);
        activeDealsAdapter.notifyDataSetChanged();
        if (restaurantModel == null)
            restaurantModel = branchDealsFilterList.get(0).getmRestaurantIdModel();
        setRestaurantData();
        setBranchRecyclerview();
        getSubscribers();
    }

    @Override
    public void onRestaurantDealsFailure(String message) {

    }

    @SuppressLint("DefaultLocale")
    void getSubscribers() {
        Map<String, String> map = new HashMap<>();
        map.put("restaurant_id", restaurantModel.getObjectId());
        ParseCloud.callFunctionInBackground("restaurantSubscriptionData", map, (FunctionCallback<Map>) (object, e) -> {
            if (object != null)
                subscribers.setText(String.format("%d", Integer.parseInt(object.get("subscribers").toString())));
        });
    }

}
