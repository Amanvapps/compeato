package com.appinnovates.campeat.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.adapter.BranchDealsAdapter;
import com.appinnovates.campeat.model.HomeBranchDeals.BranchDealModel;
import com.appinnovates.campeat.model.HomeBranchDeals.HomeBranchDealsModel;
import com.appinnovates.campeat.utils.Constant;

import java.util.List;
import java.util.Objects;

public class ItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = findViewById(R.id.toolbar_item_details);
        RecyclerView recyclerView_deals = findViewById(R.id.recycler_view_deals);
        TextView txtdistance = findViewById(R.id.splash_txt_distance);
        TextView txtaddress = findViewById(R.id.splash_txt_location);
        TextView ratings = findViewById(R.id.rest_rating);
        TextView restaurantName = findViewById(R.id.filter_textview);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow_back_black));
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        Bundle bundle = getIntent().getExtras();
        HomeBranchDealsModel homeBranchDealsModel= Objects.requireNonNull(bundle).getParcelable("restaurants");
        List<BranchDealModel> dealList= Objects.requireNonNull(homeBranchDealsModel).getDeals();
        restaurantName.setText(homeBranchDealsModel.getBranchName());
        String distance = homeBranchDealsModel.getDistance().equals("Not Specified")?"Not Specified":String.format ("%.2f", homeBranchDealsModel.getDistance())+"Kms";
        txtdistance.setText(distance);

        txtaddress.setText(homeBranchDealsModel.getCity()+" , "+homeBranchDealsModel.getCountry());
        ratings.setText(String.valueOf(homeBranchDealsModel.getmRestaurantIdModel().getAverageRating()));

        recyclerView_deals.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_deals.setHasFixedSize(true);
        BranchDealsAdapter adapter = new BranchDealsAdapter(dealList, this);
        recyclerView_deals.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> {
            String dealImage = homeBranchDealsModel.getDeals().get(position).getDeal().getDealUrl().getUrl();
            Intent intent=new Intent(ItemDetailActivity.this, DealsMenu.class);
            Bundle bundle1 = new Bundle();
            BranchDealModel deal=dealList.get(position);
            bundle1.putParcelable("restaurants",homeBranchDealsModel);
            intent.putExtras(bundle1);
            String id=dealList.get(position).getObjectId();
            intent.putExtra(Constant.DealId,id);
            intent.putExtra("deal_object",deal);
            if (dealImage != null)
                intent.putExtra("img", dealImage);
            bundle1.putInt("position",position);
            startActivity(intent);
        });
    }
}
