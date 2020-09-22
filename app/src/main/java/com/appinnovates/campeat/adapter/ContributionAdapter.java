package com.appinnovates.campeat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appinnovates.campeat.R;
import com.appinnovates.campeat.model.charity.getCharities.Result;

import java.util.ArrayList;
import java.util.List;

public class ContributionAdapter extends RecyclerView.Adapter<ContributionAdapter.ViewHolder> {

    private boolean isSelectedAll = false;

    public ContributionAdapter(List<Result> data, Context context, OnCategoryItemInterface onCategoryItemInterface) {
        this.onCategoryItemInterface = onCategoryItemInterface;
        this.data = data;
        this.context = context;
    }

    private List<Result> data;
    private ArrayList<String> charityIds;
    private ArrayList<String> allCharityIds;
    private String charityId;
    Context context;
    private ArrayList<String> objectId = new ArrayList<>();
    private int index = -1;
    private OnCategoryItemInterface onCategoryItemInterface;

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView name;
        ImageView more;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            allCharityIds = new ArrayList<>();
            charityIds = new ArrayList<>();
            checkBox = itemView.findViewById(R.id.checkbox);
            name = itemView.findViewById(R.id.text_name);
            more = itemView.findViewById(R.id.imageView19);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contribution_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result filterData = data.get(position);
        holder.name.setText(filterData.getCharityName());
        if (isSelectedAll)
            holder.checkBox.setChecked(true);
        else
            holder.checkBox.setChecked(false);

        if (position != 0) {
            for (int i = 1; i < data.size(); i++) {
                for (String id : objectId) {
                    if (data.get(i).getObjectId().equalsIgnoreCase(id)) {
                        holder.checkBox.setChecked(true);
                    }
                }
            }
        }
        if (position == data.size() - 1)
            objectId.clear();
        if (position == index) {
            holder.checkBox.setChecked(true);
        }
        if (position != 0) {
            if (holder.checkBox.isChecked()) {
                allCharityIds.add(filterData.getObjectId());
            }
        } else
            holder.more.setVisibility(View.GONE);
        setListeners(holder, position, filterData);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnCategoryItemInterface {
        void onCategoryItemClick(int position, Result submitResult, String charityId);

        void onItemClicked(ArrayList<String> charityIds);

        void getAllIds(ArrayList<String> allCharityIds);
    }

    public void setSelected(int index) {
        this.index = index;
        notifyDataSetChanged();
    }

    public void setObjectId(ArrayList<String> objectId) {
        this.objectId = objectId;
        notifyDataSetChanged();
    }

    private void setListeners(ViewHolder holder, int position, Result filterData) {
        holder.more.setOnClickListener(v -> {
            charityId = filterData.getObjectId();
            onCategoryItemInterface.onCategoryItemClick(position, filterData, charityId);
        });
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (position == 0) {
                if (isChecked) {
                    isSelectedAll = true;
                    notifyDataSetChanged();
                    onCategoryItemInterface.getAllIds(allCharityIds);
                } else {
                    isSelectedAll = false;
                    notifyDataSetChanged();
                }
            } else {
                if (isChecked) {
                    charityIds.add(filterData.getObjectId());
                    onCategoryItemInterface.onItemClicked(charityIds);
                } else {
                    charityIds.remove(filterData.getObjectId());
                    onCategoryItemInterface.onItemClicked(charityIds);
                }
            }
        });
    }
}

