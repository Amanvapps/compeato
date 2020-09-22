package com.appinnovates.campeat.views.activities;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.adapter.MenuCategoryAdapter;
import com.appinnovates.campeat.adapter.MenuItemsAdapter;
import com.appinnovates.campeat.adapter.ReviewAdapter;
import com.appinnovates.campeat.bottomSheets.BottomSheetScanQr;
import com.appinnovates.campeat.model.MenuItem;
import com.appinnovates.campeat.model.MenuResult;
import com.appinnovates.campeat.model.MenuSection;
import com.appinnovates.campeat.model.RedeemCoupon.Picture;
import com.appinnovates.campeat.model.RedeemCoupon.Result;
import com.appinnovates.campeat.model.ReviewModel;
import com.appinnovates.campeat.services.CloudNetwork.MenuService;
import com.appinnovates.campeat.services.CloudNetwork.MenuServiceViewInterface;
import com.appinnovates.campeat.services.FavoriteRestaurantService.FavouriteRestaurantService;
import com.appinnovates.campeat.services.FavoriteRestaurantService.FavouriteRestaurantServiceDelegate;
import com.appinnovates.campeat.services.ReviewService.ReviewService;
import com.appinnovates.campeat.services.ReviewService.ReviewServiceInterface;
import com.appinnovates.campeat.utils.CommonUtils;
import com.appinnovates.campeat.utils.Constant;
import com.appinnovates.campeat.utils.CustomMenuDialog;
import com.appinnovates.campeat.utils.GetBranchTiming;
import com.appinnovates.campeat.utils.LocaleManager;
import com.appinnovates.campeat.utils.PermissionsUtil;
import com.appinnovates.campeat.utils.PrefManager;
import com.appinnovates.campeat.utils.ShareUtil;
import com.appinnovates.campeat.views.fragments.ScanQrFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.card.MaterialCardView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CouponMenu extends AppCompatActivity implements MenuItemsAdapter.OnMenuItemClickInterFace, BottomSheetScanQr.BottomSheetListener, ScanQrFragment.OnFragmentInteractionListener
        , FavouriteRestaurantServiceDelegate, ReviewServiceInterface, MenuServiceViewInterface {
    private TextView textViewName;
    private RecyclerView recyclerViewReview;
    private ReviewAdapter reviewAdapter;
    private List<ReviewModel> reviewModelList;
    private TextView textViewReviewCount;
    private ImageView imageViewHeart;
    private ImageView dealImage;
    private FavouriteRestaurantService favouriteRestaurantService;
    private ReviewService reviewService;
    private ImageView share;
    private List<MenuSection> menuSections;
    private String restaurantName;
    private String branchDealId = null;
    private TextView distance;
    private RecyclerView recyclerView_menu;
    private TextView nameOfDeal;
    ArrayList<String> restaurantId;
    private ConstraintLayout main_layout;
    private MenuService menuService;
    private ContentLoadingProgressBar progressBar;
    private MaterialCardView scanButton;
    private TextView discountPercent;
    private TextView fill;
    private Boolean isSubscribed = false;
    private CollapsingToolbarLayout collapsingToolbar;
    private Toolbar toolbar;
    private ScanQrFragment scanQrCodeFragment;
    private ConstraintLayout infoClick;
    private View informationDetails;
    private AppCompatImageView infoIcon;
    Animation open, close, slide_up, slide_down;
    private TextView description;
    private TextView phone;
    private boolean isScanQR = false;
    private TextView openingHours;
    String koreanAddress = "";
    private ConstraintLayout mapView;
    public static Long couponValue;
    public static Result couponModelResult = new Result();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coupon_deals_menu);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        branchDealId = getIntent().getStringExtra(Constant.DealId);
        couponModelResult = getIntent().getParcelableExtra("coupon_object");
        Picture picture = getIntent().getParcelableExtra("picture");
        initField();
        initView();
        setAdapters();
        setListener();
        setUpServices();
        setdata();
        getRestaurantPointer();
        getBranchPointer();
        getDataFromParse();
        couponValue = couponModelResult.getValue();
        description.setText(couponModelResult.getDescription());

        nameOfDeal.setText(couponModelResult.getCouponName() != null ? couponModelResult.getCouponName() : getString(R.string.not_specified));
        String address = couponModelResult.getBranch().getStreetAddress();
        address = address.replaceAll("[a-zA-Z]+", "");
        address = address.replaceAll("[,()]", "");
        String[] streetAddress = address.split(" ");
        for (String place : streetAddress) {
            if (place.length() > 0) {
                if (!Character.isDigit(place.charAt(0)) || !Character.isDigit(place.charAt(place.length() - 1))) {
                    place = place.replaceAll("[-]", "");
                }
                koreanAddress = koreanAddress.concat(place + " ");
            }
        }
        String dist = couponModelResult.getBranch().getDistance() == null ? getString(R.string.not_specified) : String.format("%.2f", couponModelResult.getBranch().getDistance()) + getString(R.string.kms);

        @SuppressLint("DefaultLocale") String streetAdress = dist + " , " + koreanAddress;
        distance.setText(streetAdress);
        String branchName = couponModelResult.getBranch().getBranchName();
        String restaurantName = couponModelResult.getBranch().getRestaurantId().getRestaurantName();
        branchName = branchName.trim();
        restaurantName = restaurantName.trim();
        String restName = restaurantName.replaceAll("\\s+$", "");
        String branName = branchName.replaceAll("\\s+$", "");
        textViewName.setText(String.format("%s-%s", restName, branName));
        if (couponModelResult.getType() == 1) {
            discountPercent.setText(String.format("%s %%", couponModelResult.getValue().toString()));
        } else if (couponModelResult.getType() == 2) {
            discountPercent.setText(String.format("%s %s", getString(R.string.won_symbol), couponModelResult.getValue().toString()));
        }

        /*        Picasso.get().load(branchDealModel.getDeal().getDealUrl().getUrl()).centerCrop().resize(400, 400).into(dealImage);*/
        if (picture != null) {
            Glide.with(this)
                    .load(picture.getUrl())
                    .override(700, 500)
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
                    .into(dealImage);
        }
//            getSingleDeal();

        collapseHandling();
        scanQrCodeFragment = new ScanQrFragment();
        getCouponPointer();
    }

    private void setdata() {
        GetBranchTiming getBranchTiming = new GetBranchTiming(this);
        openingHours.setText(getBranchTiming.getBranchTime(couponModelResult.getBranch().getOperational()));
        phone.setText(couponModelResult.getBranch().getPhone());
    }

    private void initField() {
        menuSections = new ArrayList<>();
        restaurantId = new ArrayList<>();
    }

    private void setMenuAdapter() {
        progressBar.hide();
        textViewReviewCount.setVisibility(View.VISIBLE);
        recyclerView_menu = findViewById(R.id.recycler_menu);
        recyclerView_menu.setLayoutManager(new LinearLayoutManager(this));
        MenuCategoryAdapter adapter = new MenuCategoryAdapter(CouponMenu.this, this, couponModelResult.getType());
        recyclerView_menu.setAdapter(adapter);
        recyclerView_menu.setHasFixedSize(true);
        recyclerView_menu.setNestedScrollingEnabled(false);
        adapter.setItems(menuSections);
    }

    public void setAdapters() {
        reviewModelList = new ArrayList<>();
        recyclerViewReview.setHasFixedSize(true);
        reviewAdapter = new ReviewAdapter(R.layout.adapter_review, reviewModelList, this);
        recyclerViewReview.setAdapter(reviewAdapter);
        recyclerViewReview.setNestedScrollingEnabled(false);
        recyclerViewReview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void setListener() {

        infoClick.setOnClickListener(v -> {
            open = AnimationUtils.loadAnimation(this, R.anim.open_menu);
            close = AnimationUtils.loadAnimation(this, R.anim.close_menu);
            slide_up = AnimationUtils.loadAnimation(this, R.anim.slide_up);
            slide_down = AnimationUtils.loadAnimation(this, R.anim.slide_down);
            if (informationDetails.getVisibility() == View.VISIBLE) {
                informationDetails.setAnimation(slide_up);
                infoIcon.setAnimation(close);
                informationDetails.setVisibility(View.GONE);
            } else {
                infoIcon.setAnimation(open);
                informationDetails.setAnimation(slide_down);
                informationDetails.setVisibility(View.VISIBLE);
            }
        });

        scanButton.setOnClickListener(view -> {
            isScanQR = true;
            getSupportFragmentManager().beginTransaction().add(R.id.coordinatorLayout, scanQrCodeFragment).commit();
        });

        mapView.setOnClickListener(view -> {
/*            Intent intent = new Intent(CouponMenu.this, MapsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Constant.FROM_SCREEN, Constant.DEAL_DETAIL);
            bundle.putParcelable(Constant.DEAL, restaurant);
            intent.putExtras(bundle);
            startActivity(intenret);*/
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("address", koreanAddress);
            if (clipboard != null) {
                clipboard.setPrimaryClip(clip);
            }
            Toast.makeText(this, R.string.copied, Toast.LENGTH_SHORT).show();
        });

        imageViewHeart.setOnClickListener(v -> {
            if (ParseUser.getCurrentUser() == null) {
                CommonUtils.showToast(CouponMenu.this, getString(R.string.login_first));
                return;
            }
            if (!PermissionsUtil.isNetworkAvailable(CouponMenu.this)) {
                CommonUtils.showToast(CouponMenu.this, getString(R.string.no_internet_available));
                return;
            }
            if (isSubscribed) {
                favouriteRestaurantService.requestUnSubscribe(couponModelResult.getBranch().getRestaurantId().getPointer());
                Toast.makeText(this, "Restaurant Unsubscribed", Toast.LENGTH_SHORT).show();
                isSubscribed = false;
            } else {
                favouriteRestaurantService.requestSubscribe(couponModelResult.getBranch().getRestaurantId().getPointer());
                Toast.makeText(this, "Restaurant Subscribed", Toast.LENGTH_SHORT).show();
                isSubscribed = true;
            }
        });
        share.setOnClickListener(v -> ShareUtil.shareApplication(this));
    }

    void collapseHandling() {
        AppBarLayout appbar = findViewById(R.id.appbar);

        appbar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if ((collapsingToolbar.getHeight() + verticalOffset) < (2 * ViewCompat.getMinimumHeight(collapsingToolbar))) {
                imageViewHeart.setColorFilter(ContextCompat.getColor(CouponMenu.this, R.color.colorAccent), PorterDuff.Mode.SRC_IN);
                Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                share.setColorFilter(ContextCompat.getColor(CouponMenu.this, R.color.colorAccent), PorterDuff.Mode.SRC_IN);
                discountPercent.setTextColor(getResources().getColor(R.color.colorAccent));
            } else {
                imageViewHeart.setColorFilter(ContextCompat.getColor(CouponMenu.this, R.color.white_color), PorterDuff.Mode.SRC_IN);
                Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.white_color), PorterDuff.Mode.SRC_ATOP);
                share.setColorFilter(ContextCompat.getColor(CouponMenu.this, R.color.white_color), PorterDuff.Mode.SRC_IN);
                discountPercent.setTextColor(getResources().getColor(R.color.white_color));
            }
        });
    }

    private void initView() {
        description = findViewById(R.id.description);
        phone = findViewById(R.id.phone_number);
        openingHours = findViewById(R.id.opening_hours_timing);
        share = findViewById(R.id.share_layout);
        toolbar = findViewById(R.id.toolbar);
        scanButton = findViewById(R.id.scan);
        fill = findViewById(R.id.fill);
        distance = findViewById(R.id.splash_txt_distance);
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
        infoClick = findViewById(R.id.info_click);
        infoIcon = findViewById(R.id.item_image);
        informationDetails = findViewById(R.id.information_details);
        dealImage = findViewById(R.id.img);
        nameOfDeal = findViewById(R.id.resturant_names);
        textViewReviewCount = findViewById(R.id.text_view_review_count);
        textViewName = findViewById(R.id.text_view_name);
        imageViewHeart = findViewById(R.id.image_view_heart);
        recyclerViewReview = findViewById(R.id.recycler_view_review);
        main_layout = findViewById(R.id.layout);
        discountPercent = findViewById(R.id.discount_percent);
        mapView = findViewById(R.id.constraintLayout4);
    }


    private void setUpServices() {
        favouriteRestaurantService = FavouriteRestaurantService.getInstance();
        favouriteRestaurantService.setDelegate(this);
        reviewService = ReviewService.getInstance();
        reviewService.setDelegate(this, this);
        menuService = MenuService.getInstance();
        menuService.setDelegateAndContext(this, getApplicationContext());
    }

    private void getDataFromParse() {
        if (!PermissionsUtil.isNetworkAvailable(this)) {
            CommonUtils.showToast(this, getString(R.string.no_internet_available));
            return;
        }
        progressBar.show();
        menuService.requestMenusofCoupons(couponModelResult.getObjectId());
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
        setSubscribed();
    }

    @Override
    public void onSubscribeFailure(String message) {
        CommonUtils.showToast(this, message);
    }

    @Override
    public void onUnSubscribeSuccess() {
        setUnSubscribed();
    }

    @Override
    public void onUnSubscribeFailure(String message) {
        CommonUtils.showToast(this, message);
    }

    //Reviews Response
    @Override
    public void onReviewsSuccess(List<ReviewModel> reviewList) {
        progressBar.hide();
        main_layout.setVisibility(View.VISIBLE);
        reviewModelList.clear();
        recyclerViewReview.setVisibility(View.VISIBLE);
        reviewModelList.addAll(reviewList);
        textViewReviewCount.setVisibility(View.VISIBLE);
        textViewReviewCount.setText(getString(R.string.review_count, reviewModelList.size()));
        reviewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (isScanQR) {
            isScanQR = false;
            getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.coordinatorLayout))).commit();
        } else
            super.onBackPressed();
    }

    @Override
    public void onNoReviews() {
        progressBar.hide();
        main_layout.setVisibility(View.VISIBLE);
        recyclerViewReview.setVisibility(View.GONE);
        textViewReviewCount.setVisibility(View.VISIBLE);
        textViewReviewCount.setText(getString(R.string.review_count, 0));
    }

    @Override
    public void onReviewsFailure(String message) {
        main_layout.setVisibility(View.VISIBLE);
        progressBar.hide();
    }

    @Override
    public void onMenusSuccess(MenuResult menuResult) {
        progressBar.hide();
        main_layout.setVisibility(View.VISIBLE);
        this.menuSections.clear();
        this.menuSections.addAll(menuResult.getResult());
        setMenuAdapter();
    }

    @Override
    public void onMenusFailure(String message) {
        progressBar.hide();
        main_layout.setVisibility(View.VISIBLE);
        main_layout.setVisibility(View.VISIBLE);
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this, LocaleManager.getLanguage(this));
    }


    @Override
    public void onQrCodeClicked() {
        openScannerActivity();
    }

    private void openScannerActivity() {
        Intent intent = new Intent(this, ScannerActivity.class);
        PrefManager.setScan(true);
        startActivity(intent);

    }


    @Override
    public void onMenuItemClick(MenuItem menuItem, ArrayList<MenuItem> menuItemList, int position) {
        progressBar.setVisibility(View.GONE);
        CustomMenuDialog menuDialog = CustomMenuDialog.getInstance(menuItem, menuItemList, position);
        menuDialog.show(getSupportFragmentManager(), "");
    }

    private void getRestaurantPointer() {
        ParseQuery<ParseObject> parseQuery = new ParseQuery<>("Restaurant");
        parseQuery.whereEqualTo("objectId", couponModelResult.getBranch().getRestaurantId().getObjectId());
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                couponModelResult.getBranch().getRestaurantId().setPointer(objects.get(0));
                favouriteRestaurantService.isRestaurantSubscribed(objects.get(0));
            }
        });
    }

    private void getBranchPointer() {
        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Branch");
        parseQuery.whereEqualTo("objectId", couponModelResult.getBranch().getObjectId());
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                couponModelResult.getBranch().setBranchPointer(objects.get(0));
                reviewService.getReviewsByBranch(CouponMenu.this, objects.get(0));
            }
        });
    }

    @Override
    public void onFragmentInteraction() {
        isScanQR = false;
    }

    void getCouponPointer() {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Coupons");
        query.whereEqualTo("objectId",couponModelResult.getObjectId());
        query.findInBackground((objects, e) -> {
            if (e==null){
                couponModelResult.setCouponPointer(objects.get(0));
            }
        });
    }

}
