package com.appinnovates.campeat.adapter;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.MessageModel;
import com.appinnovates.campeat.utils.DateFormatUtil;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private final Context context;
    private List<MessageModel> mMessages;

    public MessageAdapter(Context context, List<MessageModel> messages) {
        mMessages = messages;
        this.context = context;
    }


    @Override
    public int getItemViewType(int position) {
        if (mMessages.get(position).isFromCustomer()) {
            return 0;
        } else {
            return 1;
        }
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = -1;
        switch (viewType) {
            case 0:
                layout = R.layout.chat_bubble_right;
                break;
            case 1:
                layout = R.layout.chat_bubble_left;
                break;
        }
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        MessageModel message = mMessages.get(position);
        viewHolder.mMessageView.setText(message.getMessage());
        viewHolder.mTimeView.setText(DateFormatUtil.getTime(message.getCreatedAt()));
    }

    @Override
    public int getItemCount() {
        if (mMessages.size() >0){
        return mMessages.size();
        }
        return  0;
    }

    public void add(MessageModel message) {
        mMessages.add(message);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTimeView;
        private TextView mMessageView;

        public ViewHolder(View itemView) {
            super(itemView);

            mMessageView = (TextView) itemView.findViewById(R.id.txt_msg);
            mTimeView = (TextView) itemView.findViewById(R.id.text_view_time);
        }

    }
}