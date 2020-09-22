package com.appinnovates.campeat.bottomSheets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.appinnovates.campeat.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;
import com.parse.ParseUser;

import java.util.HashMap;
import java.util.Map;


public class BottomSheetSendTad extends BottomSheetDialogFragment {

    private Context context;

    private BottomSheetListener mListener;
    SendtadInterface sendtadInterface;
    private TextView txtAddress;
    private TextView txtAmount;
    private TextView txtFee;
    String address,amount,fee;

    public BottomSheetSendTad(Context context,SendtadInterface sendtadInterface) {
        this.context = context;
        this.sendtadInterface=sendtadInterface;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_send, container, false);

        txtAddress=v.findViewById(R.id.recepient_address);
        txtAmount=v.findViewById(R.id.amount);
        txtFee =v.findViewById(R.id.fee);
        address=txtAddress.getText().toString();
        amount=txtAmount.getText().toString();
        fee=txtFee.getText().toString();
        Map<String,Object> map=new HashMap<>();
        map.put("userId", ParseUser.getCurrentUser().getObjectId());
        map.put("receiverAddress",address);
        map.put("amount",amount);
        map.put("transactionFee",1);
        map.put("currency","TAD");
        map.put("description","transaction 1" );
        Button send = v.findViewById(R.id.btn_send);
        MaterialCardView cardView = v.findViewById(R.id.materialCardView);
        cardView.setOnClickListener(view -> dismiss());

        send.setOnClickListener(view -> {
            sendtadInterface.sendTadListener(map);
            dismiss();
        });

 
        return v;
    }
 
    public interface BottomSheetListener {
        void onButtonSend(String text);
    }
 
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
 
        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }

    public interface SendtadInterface{
        void sendTadListener(Map map);
    }
}