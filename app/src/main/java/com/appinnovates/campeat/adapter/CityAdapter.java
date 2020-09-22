package com.appinnovates.campeat.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appinnovates.campeat.R;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    ActionListener actionListener;
    private List<String> mItemList;

    public CityAdapter(List<String> itemList) {
        mItemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_city_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        viewHolder.mTextViewName.setText(mItemList.get(position));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionListener != null)
                    actionListener.onActionListener(mItemList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public void setActionListener(ActionListener listener) {
        actionListener = listener;
    }

    public interface ActionListener {
        void onActionListener(String value);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTextViewName;

        public ViewHolder(View itemView) {
            super(itemView);

            mTextViewName = (TextView) itemView.findViewById(R.id.text_view_name);
        }

    }
}