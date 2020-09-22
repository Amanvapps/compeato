package com.appinnovates.campeat.views.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.utils.LocaleManager;
import com.appinnovates.campeat.utils.PrefManager;

import java.util.Objects;

public class TutorialScreen extends AppCompatActivity {
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private int[] layouts;
    private Button btnNext;
    private PrefManager prefManager;
    private TextView skipAll;
    private ImageView[] imageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefManager = new PrefManager(this);
        boolean isHowToUse = getIntent().getBooleanExtra("isHowToUse", false);
        if (!isHowToUse)
            if (!prefManager.isFirstTimeLaunch()) {
                launchHomeScreen();
                finish();
            }

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setContentView(R.layout.activity_tutorial_screen);

        viewPager = findViewById(R.id.view_pager);
        btnNext = findViewById(R.id.btn_next);
        skipAll = findViewById(R.id.skip_all);
        ImageView circle1 = findViewById(R.id.circle1);
        ImageView circle2 = findViewById(R.id.circle2);
        ImageView circle3 = findViewById(R.id.circle3);
        ImageView circle4 = findViewById(R.id.circle4);
        ImageView circle5 = findViewById(R.id.circle5);

        imageViews = new ImageView[]{circle1, circle2, circle3, circle4, circle5};
        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4,
                R.layout.welcome_slide5};
        addBottomDots(0);
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        skipAll.setOnClickListener(v -> launchHomeScreen());
        btnNext.setOnClickListener(v -> {
            int current = getItem(+1);
            if (current < layouts.length) {
                viewPager.setCurrentItem(current);
            } else {
                launchHomeScreen();
            }
        });
    }

    private void addBottomDots(int currentPage) {
        int colorsActive = getResources().getColor(R.color.colorAccent);
        int colorsInactive = getResources().getColor(R.color.dull_black_color);

        for (int i = 0; i < imageViews.length; i++) {
            if (i == currentPage)
                imageViews[i].setColorFilter(colorsActive, android.graphics.PorterDuff.Mode.SRC_IN);
            else
                imageViews[i].setColorFilter(colorsInactive, android.graphics.PorterDuff.Mode.SRC_IN);
        }
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        Intent intent = new Intent(this, HomePage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    //	viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if (position == 0) {
                btnNext.setText(getString(R.string.start));
                skipAll.setVisibility(View.VISIBLE);
            } else if (position == layouts.length - 1) {
                btnNext.setText(getString(R.string.got_it));
                skipAll.setVisibility(View.GONE);
            } else {
                btnNext.setText(getString(R.string.next));
                skipAll.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public class MyViewPagerAdapter extends PagerAdapter {
        TextView textView, textView2;

        MyViewPagerAdapter() {
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = Objects.requireNonNull(layoutInflater).inflate(layouts[position], container, false);
            textView = view.findViewById(R.id.textView11);
            textView2 = view.findViewById(R.id.textView35);
            Typeface face = Typeface.createFromAsset(getAssets(), "fonts/noto_medium.ttf");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                String[] langCodes = getResources().getConfiguration().getLocales().toLanguageTags().split("-");
                String currentLang = langCodes[0];
                if (currentLang.equalsIgnoreCase("ko")) {
                    textView.setTypeface(face);
                    textView2.setTypeface(face);
                }
            }
            if (layouts[position] == R.layout.welcome_slide1) {
                textView.setText(Html.fromHtml(getString(R.string.find_discounted_and_1_love_deals)));

            } else if (layouts[position] == R.layout.welcome_slide2) {
                textView.setText(Html.fromHtml(getString(R.string.hurry_up_before_the_deal_expires_or_sold_out)));
            } else if (layouts[position] == R.layout.welcome_slide3) {
                textView.setText(Html.fromHtml(getString(R.string.now_visit_a_restaurant_after_getting_a_deal)));
            } else if (layouts[position] == R.layout.welcome_slide4) {
                textView.setText(Html.fromHtml(getString(R.string.after_arrival_scan_qr_to_use_discount_and_donate)));
            } else if (layouts[position] == R.layout.welcome_slide5) {
                textView.setText(Html.fromHtml(getString(R.string.select_a_charity_of_your_choice_you_eat_we_donate)));
            }
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this, LocaleManager.getLanguage(this));
    }
}
