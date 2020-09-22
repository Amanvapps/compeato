package com.appinnovates.campeat.views.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.adapter.MessageAdapter;
import com.appinnovates.campeat.model.getAllDealsModel.BranchDealModel;
import com.appinnovates.campeat.model.MessageModel;
import com.appinnovates.campeat.services.MessageService.MessageService;
import com.appinnovates.campeat.services.MessageService.MessageServiceInterface;
import com.appinnovates.campeat.utils.CommonUtils;
import com.appinnovates.campeat.utils.Constant;
import com.appinnovates.campeat.utils.LocaleManager;
import com.appinnovates.campeat.utils.PermissionsUtil;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener ,MessageServiceInterface{

    private ImageView imageViewBack;
    private RecyclerView recyclerView;
    private EditText editTextMessage;
    private Button buttonSend;
    private List<MessageModel> messagesModelList;
    private MessageAdapter messageListAdapter;
    private LinearLayout linearLayoutSuggestion;
    private TextView textViewFirstSuggestion;
    private TextView textViewSecondSuggestion;
    private TextView textViewThirdSuggestion,txtName,txtDiscount;
    private BranchDealModel branchDealModel;
    private MessageService messageService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();
        initRecyclerView();
        initListener();


    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getBundleExtra(Constant.BUNDLE);
        if (bundle!=null) {
            branchDealModel = bundle.getParcelable(Constant.DEAL);
            if (branchDealModel != null) {
                /*String discount = branchDealModel.getDealModel().getDiscount_rate() + "% "
                        + getApplicationContext().getResources().getString(R.string.discount)
                        + " from" + branchDealModel.getDealModel().getStart_time() + "-"
                        + branchDealModel.getDealModel().getEnd_time();
                txtDiscount.setText(discount);
                txtName.setText(branchDealModel.getBranchDealModels().getRestaurantModel().getRestaurant_name());*/
            }
        }
        messageService = MessageService.getInstance();
        messageService.setDelegate(this);
        if (!PermissionsUtil.isNetworkAvailable(this)) {
            CommonUtils.showToast(this, getString(R.string.no_internet_available));
            return;
        }
        //messageService.getMessages(branchDealModel.getDealModel().getDealPointer(), branchDealModel.getBranchDealModels().getRestaurantModel().getRestaurantPointer());



    }


    private void initView() {
        messagesModelList = new ArrayList<>();
        imageViewBack = findViewById(R.id.back);
        recyclerView = findViewById(R.id.recycler_view_messages);
        buttonSend = findViewById(R.id.button_send);
        editTextMessage = findViewById(R.id.edit_text_message_input);
        linearLayoutSuggestion = findViewById(R.id.linear_layout_suggestions);
        textViewFirstSuggestion = findViewById(R.id.text_view_first);
        textViewSecondSuggestion = findViewById(R.id.text_view_second);
        textViewThirdSuggestion = findViewById(R.id.text_view_third);
        txtName = findViewById(R.id.text_view_name);
        txtDiscount = findViewById(R.id.text_view_discount);

    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        messageListAdapter = new MessageAdapter(this, messagesModelList);
        recyclerView.setAdapter(messageListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initListener() {
        imageViewBack.setOnClickListener(this);
        buttonSend.setOnClickListener(this);
        textViewFirstSuggestion.setOnClickListener(this);
        textViewSecondSuggestion.setOnClickListener(this);
        textViewThirdSuggestion.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.button_send:
                sendMessage(editTextMessage.getText().toString());
                break;
            case R.id.text_view_first:
                sendMessage(textViewFirstSuggestion.getText().toString());
                break;
            case R.id.text_view_second:
                sendMessage(textViewSecondSuggestion.getText().toString());
                break;
            case R.id.text_view_third:
                sendMessage(textViewThirdSuggestion.getText().toString());
                break;
        }
    }

    private void sendMessage(String msg) {
        if (!PermissionsUtil.isNetworkAvailable(this)) {
            CommonUtils.showToast(this, getString(R.string.no_internet_available));
            return;
        }
       /* if (branchDealModel != null) {
            messageService.sendMessage(msg, branchDealModel.getDealModel().getDealPointer()
                    , branchDealModel.getBranchDealModels().getRestaurantModel().getRestaurantPointer());
            messagesModelList.add(new MessageModel("", msg
                    , DateFormatUtil.getDateInString(DateFormatUtil.getCurrentDate()), true
                    , branchDealModel.getDealModel().getId()));
        }*/
        messageListAdapter.notifyDataSetChanged();
        editTextMessage.setText("");
        recyclerView.smoothScrollToPosition(messageListAdapter.getItemCount() - 1);
    }

    @Override
    public void onMessagesSuccess(List<MessageModel> messageModelList) {
        this.messagesModelList.clear();
        this.messagesModelList.addAll(messageModelList);
        messageListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNoMessageAvailable(String message) {
        this.messagesModelList.clear();
        this.messageListAdapter.notifyDataSetChanged();
        CommonUtils.showToast(getApplicationContext(),message);
    }

    @Override
    public void onMessagesFailure(String message) {
        CommonUtils.showToast(getApplicationContext(),message);
    }

    @Override
    public void onMessageSentSuccess() {
    }

    @Override
    public void onMessageSentFailure(String message) {
        CommonUtils.showToast(getApplicationContext(),message);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this, LocaleManager.getLanguage(this));
    }
}
