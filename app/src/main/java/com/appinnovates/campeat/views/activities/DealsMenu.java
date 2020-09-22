package com.appinnovates.campeat.views.activities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.adapter.MenuCategoryAdapter;
import com.appinnovates.campeat.adapter.MenuItemsAdapter;
import com.appinnovates.campeat.adapter.ReviewAdapter;
import com.appinnovates.campeat.adapter.SimpleSectionedRecyclerViewAdapter;
import com.appinnovates.campeat.model.BookingDealModel;
import com.appinnovates.campeat.bottomSheets.BottomSheetCancelDeals;
import com.appinnovates.campeat.bottomSheets.BottomSheetDealType;
import com.appinnovates.campeat.bottomSheets.BottomSheetEarnPoints;
import com.appinnovates.campeat.bottomSheets.BottomSheetNoDealLeft;
import com.appinnovates.campeat.bottomSheets.BottomSheetScanQr;
import com.appinnovates.campeat.bottomSheets.BottomSheetStandardType;
import com.appinnovates.campeat.model.HomeBranchDeals.BranchDealModel;
import com.appinnovates.campeat.model.HomeBranchDeals.HomeBranchDealsModel;
import com.appinnovates.campeat.model.MenuItem;
import com.appinnovates.campeat.model.MenuResult;
import com.appinnovates.campeat.model.MenuSection;
import com.appinnovates.campeat.model.ReviewModel;
import com.appinnovates.campeat.services.AdService.AdService;
import com.appinnovates.campeat.services.AdService.AddPointsDelegate;
import com.appinnovates.campeat.services.AdService.PointService;
import com.appinnovates.campeat.services.AdService.PointsDelegate;
import com.appinnovates.campeat.services.AdService.SettingType;
import com.appinnovates.campeat.services.AdService.SettingsDelegate;
import com.appinnovates.campeat.services.AdService.TADPEntry;
import com.appinnovates.campeat.services.BookingService.CreateBookingService;
import com.appinnovates.campeat.services.BookingService.CreateBookingViewInterface;
import com.appinnovates.campeat.services.CancelDealService.CancelDealInterface;
import com.appinnovates.campeat.services.CancelDealService.CancelDealService;
import com.appinnovates.campeat.services.CloudNetwork.MenuService;
import com.appinnovates.campeat.services.CloudNetwork.MenuServiceViewInterface;
import com.appinnovates.campeat.services.DealService.AllDealsService;
import com.appinnovates.campeat.services.DealService.AllDealsServiceInterface;
import com.appinnovates.campeat.services.FavoriteRestaurantService.FavouriteRestaurantService;
import com.appinnovates.campeat.services.FavoriteRestaurantService.FavouriteRestaurantServiceDelegate;
import com.appinnovates.campeat.services.ReviewService.ReviewService;
import com.appinnovates.campeat.services.ReviewService.ReviewServiceInterface;
import com.appinnovates.campeat.utils.AnalyticUtil;
import com.appinnovates.campeat.utils.CommonUtils;
import com.appinnovates.campeat.utils.Constant;
import com.appinnovates.campeat.utils.CustomMenuDialog;
import com.appinnovates.campeat.utils.DateFormatUtil;
import com.appinnovates.campeat.utils.GetBranchTiming;
import com.appinnovates.campeat.utils.LocaleManager;
import com.appinnovates.campeat.utils.PermissionsUtil;
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
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class DealsMenu extends AppCompatActivity implements MenuItemsAdapter.OnMenuItemClickInterFace, CancelDealInterface, BottomSheetCancelDeals.OnCancelDealInterface, BottomSheetScanQr.BottomSheetListener, BottomSheetStandardType.BottomSheetListener, ScanQrFragment.OnFragmentInteractionListener
        , BottomSheetNoDealLeft.BottomSheetListener, BottomSheetNoDealLeft.OnNoDealLeftInterface, BottomSheetDealType.BottomSheetListener, CreateBookingViewInterface, FavouriteRestaurantServiceDelegate, ReviewServiceInterface, MenuServiceViewInterface, AllDealsServiceInterface, BottomSheetCancelDeals.BottomSheetListener, BottomSheetEarnPoints.OnEarnPointInterface, BottomSheetEarnPoints.BottomSheetListener {


    private BookingDealModel bookingDealModel;
    //    private CardView cardViewCircle;
    private MaterialCardView textViewDeal;
    private TextView getdealtext;
    private BranchDealModel branchDealModel;
    private LinearLayout linearLayoutCircle;
    private TextView textViewName;
    private RecyclerView recyclerViewReview;
    private ReviewAdapter reviewAdapter;
    private List<ReviewModel> reviewModelList;
    private TextView textViewReviewCount;
    private TextView textViewDiscount;
    private ImageView imageViewHeart;
    private ImageView dealImage;
    private CreateBookingService createBookingService;
    private FavouriteRestaurantService favouriteRestaurantService;
    private ReviewService reviewService;
    private ImageView share;
    private MenuService menuService;
    private String minL_limit;
    private List<SimpleSectionedRecyclerViewAdapter.Section> sections;
    private List<MenuSection> menuSections;
    private List<MenuItem> menuItems;
    private String restaurantName;
    private int noOfPeople;
    private String branchDealId = null;
    private TextView textViewType;
    private Boolean isActiveDeal = true;
    private TextView distance;
    private HomeBranchDealsModel restaurant;
    private RecyclerView recyclerView_menu;
    private TextView nameOfDeal;
    ArrayList<String> restaurantId;
    private ConstraintLayout main_layout;
    private ImageView dealleft_image;
    private BookingDealModel bookingModel;
    private TextView dealDesc;

    private BroadcastReceiver refreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
/*            String status = intent.getStringExtra(Constant.STATUS);

            //chang
            branchDealModel.getDeal().setGetDeal(status);
            textViewDeal.setText(status);*/
        }
    };
    private int myTotalPoints;
    private boolean getDealAgain = false, isTadp = false;
    private Long points;
    private String TADP_PER_DISCOUNT, IS_TADP_PER_DISCOUNT;
    private ContentLoadingProgressBar progressBar;

    private MaterialCardView scanButton;
    private TextView groupDiscount;
    private TextView dealTime;
    private ImageView tag;
    private TextView textViewDiscountBannerCoupons;
    private TextView dealLeft;
    private TextView discountPercent;
    private String dealStatus = "";
    private CancelDealService cancelDealService;
    private TextView minNumOfPeople;
    private CardView timer;
    public static int discountedPrice;
    private TextView fill;
    private Boolean isSubscribed = false;
    private TextView bookingDate;
    private TextView noOfGuests;
    private CollapsingToolbarLayout collapsingToolbar;
    private Toolbar toolbar;
    private ScanQrFragment scanQrCodeFragment;
    private boolean isScanQR = false;
    private boolean isBooked = false;
    private boolean isCancelled = false;
    private int dealIndex = -1;
    private int branchIndex = -1;
    private ConstraintLayout infoClick;
    private View informationDetails;
    private AppCompatImageView infoIcon;
    Animation open, close, slide_up, slide_down;
    private TextView description;
    private TextView phone;
    private TextView openingHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals_menu);
        initField();
        initView();

        progressBar.show();
        dealStatus = getString(R.string.get_deal);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        branchDealModel = new BranchDealModel();
        branchDealId = getIntent().getStringExtra(Constant.DealId);
        isActiveDeal = getIntent().getBooleanExtra(Constant.isActive, true);
        getDealAgain = getIntent().getBooleanExtra(Constant.GetDealAgain, false);

        Bundle bundle = getIntent().getExtras();
        restaurant = new HomeBranchDealsModel();
        restaurant = bundle.getParcelable("restaurants");
        branchIndex = bundle.getInt("branch_index");
        dealIndex = bundle.getInt("deal_index");
        branchDealModel = getIntent().getParcelableExtra("deal_object");
        timer.setVisibility(View.VISIBLE);

        setDelegate();


        minL_limit = branchDealModel.getDeal().getMinPersonRequired();

        setAdapters();
        setListener();
        setUpServices();
        dealStatus = branchDealModel.getDeal().getGetDeal() != null ? branchDealModel.getDeal().getGetDeal() : getString(R.string.get_deal);
        if (dealStatus != null && dealStatus.equalsIgnoreCase(Constant.CANCELDEAL)) {
            getdealtext.setText(getString(R.string.cancel_deal));
        } else {
            getdealtext.setText(getString(R.string.get_deal));
        }
        phone.setText(branchDealModel.getBranch().getPhone());
        if (branchDealModel.getDeal().getDealDesc() != null)
            dealDesc.setText(branchDealModel.getDeal().getDealDesc());
        description.setText(branchDealModel.getBranch().getRestaurantId().getDescription());
        GetBranchTiming getBranchTiming = new GetBranchTiming(this);
        openingHours.setText(getBranchTiming.getBranchTime(branchDealModel.getBranch().getOperational()));
        nameOfDeal.setText(branchDealModel.getDeal().getDealName());
        String streetAdress = restaurant.getDistance().equals(getString(R.string.not_specified)) ? getString(R.string.not_specified) : String.format("%.2f", restaurant.getDistance()) + getString(R.string.kms) + " , " + branchDealModel.getBranch().getStreetAddress();
        streetAdress = streetAdress.replaceAll("[a-zA-Z]+", "");
        distance.setText(streetAdress);
        /*        Picasso.get().load(branchDealModel.getDeal().getDealUrl().getUrl()).centerCrop().resize(400, 400).into(dealImage);*/
        String dealImg = getIntent().getStringExtra("img");
        if (dealImg == null) {
            dealImg = branchDealModel.getDeal().getDealUrl().getUrl();
        }
        Glide.with(this)
                .load(dealImg)
                .override(650, 550)
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
        Long deal_left = branchDealModel.getDeal().getDealsLeft();
        if (deal_left > 0) {
            dealLeft.setText(getResources().getString(R.string.deals_left) + " : " + deal_left);
        }
        if (branchDealId == null) {
            setData();
            getDataFromParse();
        } else {
            getSingleDeal();
        }

        new CountDownTimer(branchDealModel.getDeal().getLeftTime() - System.currentTimeMillis(), 1000) { // adjust the milli seconds here
            @SuppressLint("DefaultLocale")
            public void onTick(long millisUntilFinished) {
                dealTime.setText(String.format("%02d : %02d : %02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                timer.setVisibility(View.GONE);
            }
        }.start();

        new PointService().points(new PointsDelegate() {
            @Override
            public void onSuccess(ArrayList<TADPEntry> tadpEntries, ArrayList<TADPEntry> tadEntries, int totalTADPPoints, int totalTADPoints) {
                myTotalPoints = totalTADPPoints;
            }

            @Override
            public void onFailure(String message) {

            }
        });

        new AdService().getSettings(settings -> {
            TADP_PER_DISCOUNT = settings.get(SettingType.TADP_PER_DISCOUNT);
            IS_TADP_PER_DISCOUNT = settings.get(SettingType.IS_TADP_PER_DISCOUNT);
        });

        if (dealStatus != null && dealStatus.equalsIgnoreCase(getResources().getString(R.string.cancel_deal))) {
            scanButton.setVisibility(View.VISIBLE);
            fill.setVisibility(View.VISIBLE);
        }
        collapseHandling();
        checkDeal();
        scanQrCodeFragment = new ScanQrFragment();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }


    private void getSingleDeal() {
        AllDealsService allDealsService = AllDealsService.getInstance();
        //CommonUtils.showProgress(this);
        allDealsService.getSingleDeal(branchDealId);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(refreshReceiver);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        branchDealModel = getIntent().getParcelableExtra("deal_object");
        branchDealId = intent.getStringExtra(Constant.DealId);
        setAdapters();
        setListener();
        setUpServices();
        if (branchDealId == null) {
            Log.e("aa" , "1");
            setData();
            getDataFromParse();
        } else {
            Log.e("aa" , "2");
            getSingleDeal();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initField() {
        bookingDealModel = getIntent().getParcelableExtra(Constant.BookingDealModel);
        branchDealModel = getIntent().getParcelableExtra("deal_object");
        menuItems = new ArrayList<>();
        menuSections = new ArrayList<>();
        reviewModelList = new ArrayList<>();
        sections = new ArrayList<>();
        restaurantId = new ArrayList<>();
        //branchDealModel=new BranchDealModel();
    }

    private void setMenuAdapter() {
        progressBar.hide();
        textViewReviewCount.setVisibility(View.VISIBLE);
        int index = 0;
        //Sections
        sections.clear();
        for (MenuSection category : menuSections) {
            if (category.getMenuItem() != null && category.getMenuItem().size() > 0) {
                sections.add(new SimpleSectionedRecyclerViewAdapter.Section(index, category.getCatName()));
                index = index + category.getMenuItem().size() + 1;
            }

        }
        menuItems.clear();
        for (int cat = 0; cat < menuSections.size(); cat++) { // cat = 2
            if (menuSections.get(cat).getMenuItem() != null) {
                menuItems.addAll(menuSections.get(cat).getMenuItem());
            }
        }


        recyclerView_menu = findViewById(R.id.recycler_menu);
        recyclerView_menu.setLayoutManager(new LinearLayoutManager(this));
        ((SimpleItemAnimator) Objects.requireNonNull(recyclerView_menu.getItemAnimator())).setSupportsChangeAnimations(false);
        MenuCategoryAdapter adapter = new MenuCategoryAdapter(DealsMenu.this, this, 100L);
        recyclerView_menu.setAdapter(adapter);
        recyclerView_menu.setHasFixedSize(true);
        recyclerView_menu.setNestedScrollingEnabled(false);
        adapter.setItems(menuSections);
    }

    public void setAdapters() {
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
//            assert getFragmentManager() != null;
//            bottomSheetScanQr.show(getSupportFragmentManager(), "BottomSheetscanQr");
//            isScanQR = true;
//            Bundle bundle = new Bundle();
//            bundle.putParcelable(Constant.DEAL_DETAIL, branchDealModel);
//            scanQrCodeFragment.setArguments(bundle);
//            getSupportFragmentManager().beginTransaction().add(R.id.coordinatorLayout, scanQrCodeFragment).commit();
//

            goToReviewScreen();


        });

        findViewById(R.id.share_layout).setOnClickListener(v -> AnalyticUtil.setSharesEvent(getApplicationContext(), branchDealModel.getBranch().getRestaurantId().getObjectId()));

        findViewById(R.id.get_deal).setOnClickListener(view -> {
            if (dealStatus.equals(Constant.CANCELDEAL) &&
                    branchDealModel.getDeal().getBookingDealModel() != null) {
                linearLayoutCircle.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                BottomSheetCancelDeals bottomSheet = new BottomSheetCancelDeals(this, this);
                bottomSheet.show(getSupportFragmentManager(), "BottomSheetCancelDeal");
            } else {
                if (ParseUser.getCurrentUser() == null) {
                    CommonUtils.showToast(DealsMenu.this, getString(R.string.login_first));
                    return;
                }
                if (!PermissionsUtil.isNetworkAvailable(DealsMenu.this)) {
                    CommonUtils.showToast(DealsMenu.this, getString(R.string.no_internet_available));
                    return;
                }
                if (!isActiveDeal) {
                    CommonUtils.showToast(DealsMenu.this, getString(R.string.deal_expired));
                    return;
                }
                progressBar.show();
                progressBar.setVisibility(View.VISIBLE);
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("BranchBooking");
                parseQuery.whereEqualTo("deal_branch", branchDealModel.getBranchDealPointer());
                parseQuery.whereEqualTo("customer_id", ParseUser.getCurrentUser());
                parseQuery.whereEqualTo("has_fulfilled_yn", "N");
                parseQuery.findInBackground((objects, e) -> {
                    if (e == null) {
                        if (objects != null && objects.size() > 0) {
                            CommonUtils.showToast(DealsMenu.this, getString(R.string.not_book_same_deal));
                        } else {
                            if (branchDealModel.getDeal().getType().equalsIgnoreCase(Constant.GROUP)) {
                                groupSheet();
                            } else {
                                if (branchDealModel.getDeal().getFreeCouponDiscount() > 0) {
                                    standardSheet();
                                } else {
                                    noOfPeople = 1;
                                    requestTAdpPointsAndSettings(noOfPeople);
                                }
                            }

                        }
                    } else {
                        if (branchDealModel.getDeal().getType().equalsIgnoreCase(Constant.GROUP)) {
                            groupSheet();
                        } else {
                            if (branchDealModel.getDeal().getFreeCouponDiscount() > 0) {
                                standardSheet();
                            } else {
                                noOfPeople = 1;
                                requestTAdpPointsAndSettings(noOfPeople);
                            }
                        }

                    }
                });
            }
        });

        findViewById(R.id.constraintLayout4).setOnClickListener(view -> {
            Intent intent = new Intent(DealsMenu.this, MapsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Constant.FROM_SCREEN, Constant.DEAL_DETAIL);
            bundle.putParcelable(Constant.DEAL, restaurant);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        linearLayoutCircle.setOnClickListener(view -> {
            startActivity(new Intent(DealsMenu.this, HomePage.class).putExtra(Constant.DEAL, Constant.DEAL));
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            finish();
        });

        imageViewHeart.setOnClickListener(v -> {
            if (ParseUser.getCurrentUser() == null) {
                CommonUtils.showToast(DealsMenu.this, getString(R.string.login_first));
                return;
            }
            if (!PermissionsUtil.isNetworkAvailable(DealsMenu.this)) {
                CommonUtils.showToast(DealsMenu.this, getString(R.string.no_internet_available));
                return;
            }
            if (isSubscribed) {
                favouriteRestaurantService.requestUnSubscribe(branchDealModel.getBranch().getRestaurantId().getPointer());
                Toast.makeText(this, "Restaurant Unsubscribed", Toast.LENGTH_SHORT).show();
                isSubscribed = false;
            } else {
                favouriteRestaurantService.requestSubscribe(branchDealModel.getBranch().getRestaurantId().getPointer());
                Toast.makeText(this, "Restaurant Subscribed", Toast.LENGTH_SHORT).show();
                isSubscribed = true;
            }
        });
        share.setOnClickListener(v -> ShareUtil.shareDeal(DealsMenu.this, restaurantName
                , String.valueOf(branchDealModel.getDeal().getDiscountRate()), branchDealModel.getDeal().getObjectId()
                , branchDealModel.getBranch().getRestaurantId().getObjectId()));
    }

    void collapseHandling() {
        AppBarLayout appbar = findViewById(R.id.appbar);

        appbar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if ((collapsingToolbar.getHeight() + verticalOffset) < (2 * ViewCompat.getMinimumHeight(collapsingToolbar))) {
                timer.setVisibility(View.GONE);
                imageViewHeart.setColorFilter(ContextCompat.getColor(DealsMenu.this, R.color.colorAccent), PorterDuff.Mode.SRC_IN);
                Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                share.setColorFilter(ContextCompat.getColor(DealsMenu.this, R.color.colorAccent), PorterDuff.Mode.SRC_IN);
                discountPercent.setTextColor(getResources().getColor(R.color.colorAccent));
            } else {
                if (dealStatus.equalsIgnoreCase(Constant.CANCELDEAL)) {
                    timer.setVisibility(View.GONE);
                } else {
                    timer.setVisibility(View.VISIBLE);
                }
                imageViewHeart.setColorFilter(ContextCompat.getColor(DealsMenu.this, R.color.white_color), PorterDuff.Mode.SRC_IN);
                Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.white_color), PorterDuff.Mode.SRC_ATOP);
                share.setColorFilter(ContextCompat.getColor(DealsMenu.this, R.color.white_color), PorterDuff.Mode.SRC_IN);
                discountPercent.setTextColor(getResources().getColor(R.color.white_color));
            }
        });
    }




    private void goToReviewScreen() {
        BookingDealModel model;
        if (branchDealModel != null) {
            model = branchDealModel.getDeal().getBookingDealModel();
        } else {
            model = bookingDealModel;
        }
        Intent intent = new Intent(this, SubmitDealActivity.class);
        intent.putExtra(Constant.BookingDealModel, model);
        startActivity(intent);
    }



    private void initView() {
        dealDesc = findViewById(R.id.deal_detail);
        description = findViewById(R.id.description);
        phone = findViewById(R.id.phone_number);
        openingHours = findViewById(R.id.opening_hours_timing);
        share = findViewById(R.id.share_layout);
        toolbar = findViewById(R.id.toolbar);
        scanButton = findViewById(R.id.scan);
        fill = findViewById(R.id.fill);
        distance = findViewById(R.id.splash_txt_distance);
        progressBar = findViewById(R.id.progressBar2);
        timer = findViewById(R.id.cardview_deal_timer);
        getdealtext = findViewById(R.id.get_deal_text);
        minNumOfPeople = findViewById(R.id.min_num_of_people);
        progressBar.setVisibility(View.VISIBLE);
        infoClick = findViewById(R.id.info_click);
        infoIcon = findViewById(R.id.item_image);
        informationDetails = findViewById(R.id.information_details);
        dealImage = findViewById(R.id.img);
        nameOfDeal = findViewById(R.id.resturant_names);
        textViewReviewCount = findViewById(R.id.text_view_review_count);
        textViewName = findViewById(R.id.text_view_name);
        textViewType = findViewById(R.id.text_view_type);
        textViewDiscount = findViewById(R.id.text_view_discount);
        imageViewHeart = findViewById(R.id.image_view_heart);
//        cardViewCircle = findViewById(R.id.card_view_circle);
        linearLayoutCircle = findViewById(R.id.linear_layout_circle);
        textViewDeal = findViewById(R.id.get_deal);
        recyclerViewReview = findViewById(R.id.recycler_view_review);
        main_layout = findViewById(R.id.layout);
        dealleft_image = findViewById(R.id.imageView15);
        tag = findViewById(R.id.imageView9);


        groupDiscount = findViewById(R.id.groupdiscount);
        dealTime = findViewById(R.id.time_of_deal);
        textViewDiscountBannerCoupons = findViewById(R.id.coupons);
        dealLeft = findViewById(R.id.dealleft);

        discountPercent = findViewById(R.id.discount_percent);
        bookingDate = findViewById(R.id.bookedtime);
        noOfGuests = findViewById(R.id.no_of_guests);

    }

/*    private void setRestaurantInfo(BranchModel branchModel) {
        if (branchModel != null) {
            adddress.setText(branchModel.getStreetAddress());
            if (branchModel.getRestaurantId() != null)
                textViewDesc.setText(branchModel.getRestaurantId().getDescription());
        }
    }*/

    private void setData() {
        if (branchDealModel != null && branchDealModel.getDeal() != null && branchDealModel.getBranch() != null) {
            if (branchDealModel.getDeal().getMinPersonRequired() != null) {
                if (!branchDealModel.getDeal().getMinPersonRequired().equals("null")) {
                }
            }
            String timeLeft = branchDealModel.getDeal().getTimeLeft();
            if (!isActiveDeal) {
                timeLeft = "0 min";
            }
            restaurantName = branchDealModel.getBranch().getRestaurantId().getRestaurantName();
            textViewName.setText(restaurantName + " , " + branchDealModel.getBranch().getBranchName());
/*            if (getDealAgain) {
                getdealtext.setText(getResources().getString(R.string.get_deal));
            } else {
                getdealtext.setText(branchDealModel.getDeal().getGetDeal());
            }*/
            String dealsLeft = getApplicationContext().getResources().getString(R.string.deals_left) + branchDealModel.getDeal().getDealsLeft();


/*
            setRestaurantInfo(branchDealModel.getBranch());
            textViewOperationHours.setText(DateFormatUtil.getOpeningHours(branchDealModel.getBranch().getOperational()));
*/
        }
    }


    private void groupSheet() {
        int minLimit = Integer.parseInt(branchDealModel.getDeal().getMinPersonRequired());

        BottomSheetDealType bottomSheet = new BottomSheetDealType(this, minLimit);
        bottomSheet.show(getSupportFragmentManager(), "BottomSheetdealtype");
    }

    private void standardSheet() {
        BottomSheetStandardType bottomSheet = new BottomSheetStandardType(this, branchDealModel.getDeal());
        bottomSheet.show(getSupportFragmentManager(), "BottomSheetstandard");
    }

    @Override
    public void onBackPressed() {
        if (isScanQR) {
            isScanQR = false;
            getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.coordinatorLayout))).commit();
        } else {
            Intent intent = new Intent();
            intent.putExtra("branch_index", branchIndex);
            intent.putExtra("deal_index", dealIndex);
            if (isBooked) {
                intent.putExtra("isBooked", isBooked);
                intent.putExtra("booking_model", bookingModel);
            } else if (isCancelled)
                intent.putExtra("isCancelled", isCancelled);
            setResult(123, intent);
            finish();
        }
    }


    private void requestTAdpPointsAndSettings(int count) {
        //CommonUtils.showProgress(this);
        new AdService().getSettings(new SettingsDelegate() {
            @Override
            public void success(Map<String, String> settings) {
                TADP_PER_DISCOUNT = settings.get(SettingType.TADP_PER_DISCOUNT);
                IS_TADP_PER_DISCOUNT = settings.get(SettingType.IS_TADP_PER_DISCOUNT);
                long discount = branchDealModel.getDeal().getDiscountRate();
                points = discount * Integer.parseInt(TADP_PER_DISCOUNT);
                isTadp = IS_TADP_PER_DISCOUNT.equalsIgnoreCase("TRUE");
                if (isTadp)
                    if (count < Integer.parseInt(branchDealModel.getDeal().getMinPersonRequired())) {


                    } else {
                        if (points <= myTotalPoints) {

                            createBookingService.requestCreateBooking(branchDealModel.getBranchDealPointer()
                                    , "N", noOfPeople);
                        } else {
                            CommonUtils.hideProgress();

                            earnPoints();

                        }
                    }
                else {
                    createBookingService.requestCreateBooking(branchDealModel.getBranchDealPointer()
                            , "N", noOfPeople);
                }
            }
        });
    }

    private void earnPoints() {
        BottomSheetEarnPoints bottomSheet = new BottomSheetEarnPoints(this, this, "null", 111, "abc");
        bottomSheet.show(getSupportFragmentManager(), "bottomSheetFilter");
    }


    private void setUpServices() {
        createBookingService = CreateBookingService.getInsance();
        createBookingService.setDelegateAndContext(this, getApplicationContext());

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
        favouriteRestaurantService.isRestaurantSubscribed(branchDealModel.getBranch().getRestaurantId().getPointer());
//        reviewService.getDealReviews(this, branchDealModel.getDeal().getDealPointer(), branchDealModel.getBranch().getBranchPointer());

        String dealId = branchDealModel.getDeal().getObjectId();
        String branchId = branchDealModel.getBranch().getObjectId();
        reviewService.getReviewsByBranch(this, branchDealModel.getBranch().getBranchPointer());
        menuService.requestMenus(dealId, branchId, "single");
    }

    private void setSubscribed() {
        imageViewHeart.setImageDrawable(getResources().getDrawable(R.drawable.subscribe_icon));
    }

    private void setUnSubscribed() {
        imageViewHeart.setImageDrawable(getResources().getDrawable(R.drawable.heart_line));
    }

    //Create Booking Response
    @Override
    public void onCreateBookingSuccess(BookingDealModel bookingDealModel, String getDeal) {
        String discountRate;
        progressBar.hide();
        isBooked = true;
        this.bookingModel = bookingDealModel;
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        textViewDeal.setVisibility(View.GONE);
        branchDealModel.getDeal().setGetDeal(getDeal);
        branchDealModel.getDeal().setBookingDealModel(bookingDealModel);
        dealStatus = Constant.CANCELDEAL;
        timer.setVisibility(View.GONE);
        if (branchDealModel.getDeal().getType().equalsIgnoreCase(Constant.GROUP)) {
            discountRate = branchDealModel.getDeal().getGroupDiscountRate() + getString(R.string.off);
            linearLayoutCircle.setBackground(getResources().getDrawable(R.drawable.rounded_border_primary));
            textViewType.setText(getString(R.string.group_discount_));

        } else {
            discountRate = branchDealModel.getDeal().getDiscountRate() + getString(R.string.off);
            linearLayoutCircle.setBackground(getResources().getDrawable(R.drawable.rounded_border_green));
            textViewType.setText(getString(R.string.standard));
        }
        textViewDiscount.setText(discountRate);
        if (bookingDealModel != null && bookingDealModel.getBooking_date() != null)
//            textViewType.setText(DateFormatUtil.getTime(branchDealModel.getDeal().getBookingDealModel().getBooking_date()));
            linearLayoutCircle.setVisibility(View.VISIBLE);
//        scanButton.setVisibility(View.VISIBLE);
//        textViewDeal.setBackgroundColor(getResources().getColor(R.color.white_color));
//        textViewDeal.setBackground(getResources().getDrawable(R.drawable.border_unfill_orange));
//        getdealtext.setTextColor(getResources().getColor(R.color.textcolordark));
//        getdealtext.setText(getDeal);

        if (isTadp) {
            new PointService().addPoints(-points, null, branchDealModel.getBranch().getBranchPointer(), null, false, new AddPointsDelegate() {
                @Override
                public void success() {

                }

                @Override
                public void failure(String message) {

                }
            });
        }

//        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constant.RefreshHomeActivity));
    }


    @Override
    public void onCreateBookingFailure(String message) {
        CommonUtils.showToast(this, message);
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
        Log.i("review", reviewModelList.size() + "aa");

        recyclerViewReview.setVisibility(View.VISIBLE);
        reviewModelList.addAll(reviewList);
        textViewReviewCount.setText(getString(R.string.review_count, reviewModelList.size()));
        reviewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNoReviews() {
        progressBar.hide();
        main_layout.setVisibility(View.VISIBLE);
        recyclerViewReview.setVisibility(View.GONE);
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
    public void onAllDealsSuccess(List<BranchDealModel> dealsModels, boolean isDiscounted) {
        progressBar.hide();
        main_layout.setVisibility(View.VISIBLE);
        if (dealsModels != null && dealsModels.size() == 1) {
            branchDealModel = dealsModels.get(0);
            getDataFromParse();
            setData();
        }

    }

    @Override
    public void onAllDealsFailure(String message) {
        progressBar.hide();
        main_layout.setVisibility(View.VISIBLE);
        CommonUtils.showToast(this, message);
    }

    @Override
    public void onNoDealsAvailable(String message) {
        progressBar.hide();
        main_layout.setVisibility(View.VISIBLE);
        CommonUtils.showToast(this, message);
    }

    @Override
    public void onNoInternetAvailable() {
        progressBar.hide();
        CommonUtils.showToast(this, getString(R.string.no_internet_available));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this, LocaleManager.getLanguage(this));
    }

    @Override
    public void onButtonDeal(String count) {
        noOfPeople = Integer.parseInt(count);
        noOfPeople = noOfPeople + 1;    // include user
        requestTAdpPointsAndSettings(noOfPeople);
    }

    @Override
    public void onButtonStandard() {
        noOfPeople = 1;
        requestTAdpPointsAndSettings(noOfPeople);
    }

    @Override
    public void onQrCodeClicked() {
        openScannerActivity();
    }

    private void openScannerActivity() {
        BookingDealModel model;
        if (branchDealModel != null) {
            model = branchDealModel.getDeal().getBookingDealModel();
        } else {
            model = bookingDealModel;
        }
        Intent intent = new Intent(this, ScannerActivity.class);
        intent.putExtra(Constant.BookingDealModel, model);
        startActivity(intent);

    }

    void checkDeal() {
        if (branchDealModel.getDeal().getType().equalsIgnoreCase(Constant.GROUP)) {
            discountedPrice = Integer.parseInt(branchDealModel.getDeal().getGroupDiscountRate().toString());
            textViewDiscountBannerCoupons.setVisibility(View.GONE);
            minNumOfPeople.setVisibility(View.VISIBLE);
            minNumOfPeople.setText(getResources().getString(R.string.min_people) + minL_limit);
            groupDiscount.setText(R.string.group_disc);
            dealLeft.setVisibility(View.VISIBLE);
            dealleft_image.setVisibility(View.VISIBLE);
            groupDiscount.setTextColor(getResources().getColor(R.color.groupColor));
            tag.setImageDrawable(getResources().getDrawable(R.drawable.free_coupon));
            discountPercent.setText(branchDealModel.getDeal().getGroupDiscountRate().toString() + getString(R.string.off));
            if (dealStatus.equalsIgnoreCase(Constant.CANCELDEAL)) {
                scanButton.setVisibility(View.VISIBLE);
                textViewDeal.setBackground(getResources().getDrawable(R.drawable.border_unfill_orange_white));
                getdealtext.setTextColor(getResources().getColor(R.color.textcolordark));
                textViewType.setText(getString(R.string.group_discount_));
                timer.setVisibility(View.GONE);
                minNumOfPeople.setVisibility(View.GONE);
                textViewDiscount.setText(branchDealModel.getDeal().getGroupDiscountRate().toString() + getString(R.string.off));
                if (branchDealModel.getDeal().getBookingDealModel() != null) {
                    String booking = branchDealModel.getDeal().getBookingDealModel().getBooking_date();
                    String[] time = booking.split("GMT");
                    String bookingTime = time[0];
                    bookingDate.setText(bookingTime);
                    bookingDate.setVisibility(View.VISIBLE);

                }
                noOfGuests.setVisibility(View.VISIBLE);
                noOfGuests.setText(getString(R.string.no_of_guest) + " " + minL_limit);
            } else {
                if (branchDealModel.getDeal().getDealsLeft() == 0) {
                    BottomSheetNoDealLeft bottomSheet = new BottomSheetNoDealLeft(this, this);
                    bottomSheet.setCancelable(false);
                    bottomSheet.show(getSupportFragmentManager(), "BottomSheetNoDealleft");
                }
                textViewDeal.setBackground(getResources().getDrawable(R.drawable.border_grey_dark));
                getdealtext.setTextColor(getResources().getColor(R.color.white_color));
            }
        } else {
            discountedPrice = Integer.parseInt(branchDealModel.getDeal().getDiscountRate().toString());
            if (branchDealModel.getDeal().getFreeCouponDiscount() > 0) {
                textViewDiscountBannerCoupons.setVisibility(View.VISIBLE);
                dealLeft.setVisibility(View.VISIBLE);
                dealleft_image.setVisibility(View.VISIBLE);
            } else {
                minNumOfPeople.setVisibility(View.VISIBLE);
                minNumOfPeople.setText(getString(R.string.deals_left) + branchDealModel.getDeal().getDealsLeft());
            }
            groupDiscount.setText(R.string.standard_discount);
            groupDiscount.setTextColor(getResources().getColor(R.color.green_light));
            tag.setImageDrawable(getResources().getDrawable(R.drawable.standard_coupon));
            discountPercent.setText(branchDealModel.getDeal().getDiscountRate().toString() + getString(R.string.off));
            if (dealStatus.equalsIgnoreCase(Constant.CANCELDEAL)) {
                scanButton.setVisibility(View.VISIBLE);
                timer.setVisibility(View.GONE);
                textViewDeal.setBackground(getResources().getDrawable(R.drawable.border_unfill_orange_white));
                getdealtext.setTextColor(getResources().getColor(R.color.textcolordark));
                textViewType.setText(getString(R.string.standard));
                textViewDiscount.setText(branchDealModel.getDeal().getDiscountRate().toString() + getString(R.string.off));
                if (branchDealModel.getDeal().getBookingDealModel() != null) {
                    String booking = branchDealModel.getDeal().getBookingDealModel().getBooking_date();
                    String[] time = booking.split("GMT");
                    String bookingTime = time[0];
                    bookingDate.setText(bookingTime);
                    bookingDate.setVisibility(View.VISIBLE);
                }

            } else {
                if (branchDealModel.getDeal().getDealsLeft() == 0) {
                    BottomSheetNoDealLeft bottomSheet = new BottomSheetNoDealLeft(this, this);
                    bottomSheet.setCancelable(false);
                    bottomSheet.show(getSupportFragmentManager(), "BottomSheetNoDealleft");
                }
                textViewDeal.setBackground(getResources().getDrawable(R.drawable.border_grey_dark));
                getdealtext.setTextColor(getResources().getColor(R.color.white_color));
            }
        }
    }


    @Override
    public void onButtonCancelDeal(String text) {

    }

    @Override
    public void onCancelDealClicked() {
        if (!PermissionsUtil.isNetworkAvailable(this)) {
            CommonUtils.showToast(this, getString(R.string.no_internet_available));
            return;
        }
        progressBar.show();
        if (branchDealModel != null) {
            cancelDealService.cancelDeal(branchDealModel.getDeal().getBookingDealModel().getBookingPointer());
        } else {
            cancelDealService.cancelDeal(bookingDealModel.getBookingPointer());
        }
    }

    void setDelegate() {
        cancelDealService = CancelDealService.getInstance();
        cancelDealService.setDelegate(this);
    }

    @Override
    public void onCancelDealSuccess(String message) {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBar.setVisibility(View.GONE);
        progressBar.hide();
        isCancelled = true;
        CommonUtils.showToast(this, message);
        dealStatus = getApplicationContext().getResources().getString(R.string.get_deal);
        onBackPressed();
    }

    @Override
    public void onCancelDealFailure(String message) {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBar.setVisibility(View.GONE);
        progressBar.hide();
        CommonUtils.showToast(this, message);
    }

    @Override
    public void onPointsClicked() {
        startActivity(new Intent(DealsMenu.this, EarnPointsActivity.class));
    }


    @Override
    public void onMenuItemClick(MenuItem menuItem, ArrayList<MenuItem> menuItemList, int position) {
        progressBar.setVisibility(View.GONE);
        CustomMenuDialog menuDialog = CustomMenuDialog.getInstance(menuItem, menuItemList, position);
        menuDialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public void onNoDealLeftClicked() {
        Intent intent = new Intent(this, HomePage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finishAffinity();
    }

    @Override
    public void onFragmentInteraction() {
        isScanQR = false;
    }
}
