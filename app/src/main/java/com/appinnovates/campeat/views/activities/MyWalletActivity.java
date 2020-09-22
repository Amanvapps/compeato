package com.appinnovates.campeat.views.activities;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.bottomSheets.BottomSheetReceiveTad;
import com.appinnovates.campeat.bottomSheets.BottomSheetSendTad;
import com.appinnovates.campeat.model.WalletModel;
import com.appinnovates.campeat.views.fragments.ConvertFragment;
import com.appinnovates.campeat.views.fragments.TADFragment;
import com.appinnovates.campeat.views.fragments.TADPFragment;

import java.util.ArrayList;

public class MyWalletActivity extends AppCompatActivity implements BottomSheetReceiveTad.BottomSheetListener, BottomSheetSendTad.BottomSheetListener {

    TextView txtTad,txtTadp,txtConvert;
    CardView tadCV,tadpCV,convertCV;
    FrameLayout container;
    ArrayList<WalletModel> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);

        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back_black);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        initViews();
        setClickListeners();
        setUpData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setFragment(0);
    }

    private void setUpData() {
        list.add(new WalletModel<>(tadpCV, txtTadp, new TADPFragment()));
        list.add(new WalletModel<>(tadCV, txtTad, new TADFragment()));
        list.add(new WalletModel<>(convertCV, txtConvert, new ConvertFragment()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setClickListeners() {

        tadpCV.setOnClickListener(v -> setFragment(0));

        tadCV.setOnClickListener(v -> setFragment(1));



        convertCV.setOnClickListener(v -> setFragment(2));
    }

    private void setFragment(int index) {
        WalletModel<Fragment> item = list.get(index);
        if (item.isAdded)
            return;

        for (int i = 0;i<list.size();i++){
            WalletModel model = list.get(i);
            model.cardView.setBackground(i == index ? getResources().getDrawable(R.drawable.border_unfill_orange):getResources().getDrawable(R.drawable.border_unfill_white));
//            model.textView.setTextColor(i == index ? getResources().getColor(R.color.white_color):getResources().getColor(R.color.darkGreyColor));
            model.isAdded = i == index;
        }

        loadFragment(item.fragment);
    }

    private void initViews() {
        container = findViewById(R.id.container);
        txtTad = findViewById(R.id.txt_tad);
        txtTadp = findViewById(R.id.txt_tadp);
        txtConvert = findViewById(R.id.txt_convert);
        tadCV = findViewById(R.id.tad_cv);
        tadpCV = findViewById(R.id.tadp_cv);
        convertCV = findViewById(R.id.convert_cv);

    }

    private void loadFragment(Fragment fragment){
        try{
            FragmentManager manager = getSupportFragmentManager();
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment, fragment.getTag())
                    .addToBackStack(fragment.getClass().getName());
            fragmentTransaction.commit();
        }
        catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void onButtonReceived(String text) {

    }

    @Override
    public void onButtonSend(String text) {

    }
}
